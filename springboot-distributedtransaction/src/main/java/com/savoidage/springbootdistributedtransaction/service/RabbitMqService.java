package com.savoidage.springbootdistributedtransaction.service;

import com.alibaba.fastjson.JSON;
import com.savoidage.springbootdistributedtransaction.config.RabbitConfig;
import com.savoidage.springbootdistributedtransaction.dao.LocalMqMessageDao;
import com.savoidage.springbootdistributedtransaction.entity.LocalMqMessage;
import com.savoidage.springbootdistributedtransaction.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * Author: created by savoidage
 * CreateTime: 2020-10-13 09:38
 * Description: rabbitMqService
 */
@Slf4j
@Service("rabbitMqService")
public class RabbitMqService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Resource
    private LocalMqMessageDao localMqMessageDao;

    /**
     * 初始化Rabbit
     */
    @PostConstruct
    private void initRabbitTemplate() {
        // 设置消息发送确认回调  发送消息成功后更新本地消息表状态
        rabbitTemplate.setConfirmCallback((CorrelationData correlationData, boolean ack, String cause) -> {
            log.info("进入消息发送确认回调...");
            if (ack) {
                Integer updateCount = localMqMessageDao.updateLocalMqMessageById(Long.parseLong(correlationData.getId()));
                if (updateCount > 0) {
                    log.info("消息状态更新成功... 更新消息的订单id：" + correlationData.getId());
                }
            } else {
                // 走补偿手段
                LocalMqMessage message = localMqMessageDao.findById(Long.parseLong(correlationData.getId()));
                if(null != message){
                    retrySendMessage(message);
                }
            }
        });
    }

    /**
     * 发送mq消息
     *
     * @param mqMessage 本地消息
     */
    public void sendMessage(LocalMqMessage mqMessage) {
        log.info("发送本地消息...");
        rabbitTemplate.convertAndSend(RabbitConfig.ORDER_EXCHANGE, RabbitConfig.ORDER_ROUTE_KEY, JSON.toJSONString(mqMessage),
                next -> {
                    next.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                    return next;
                }, new CorrelationData(String.valueOf(mqMessage.getId()))
        );
    }

    /**
     * 消息重试
     * @param message 本地消息
     */
    @Transactional
    public void retrySendMessage(LocalMqMessage message){
        log.info("进入消息重试...");
        if (message.getRetryCount() >= 5) {
            // 修改消息状态为失败 需人工处理
            localMqMessageDao.updateMessageToFailed(message.getId());
        } else {
            // 修改重试次数并重新发送消息
            localMqMessageDao.updateMessageRetryCount(message.getId());
            // 重发消息
            sendMessage(message);
        }
    }
}
