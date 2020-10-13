package com.savoidage.springbootdistributedtransaction.listener;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.savoidage.springbootdistributedtransaction.config.RabbitConfig;
import com.savoidage.springbootdistributedtransaction.entity.LocalMqMessage;
import com.savoidage.springbootdistributedtransaction.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Author: created by savoidage
 * CreateTime: 2020-10-13 14:58
 * Description: 订单监听
 */
@Slf4j
@Component
public class OrderListener {

    @Autowired
    private RedisUtils redisUtils;

    @RabbitListener(queues = RabbitConfig.ORDER_QUEUE)
    public void handleMessage(Channel channel, @Payload String message, @Header(AmqpHeaders.DELIVERY_TAG) long tag,
                              @Header(AmqpHeaders.REDELIVERED) boolean reDelivered) throws IOException {
        log.info("订单监听到的消息：" + message);
        LocalMqMessage localMqMessage = JSON.parseObject(message, LocalMqMessage.class);
        try {
            // 保证幂等
            if (null == redisUtils.get(String.valueOf(localMqMessage.getId()))) {
                // 消息处理
                anotherOperation(localMqMessage);
                redisUtils.set(String.valueOf(localMqMessage.getId()), "1");
            }
            channel.basicAck(tag, false);
        } catch (IOException e) {
            if (reDelivered) {
                log.info("消息已重复处理失败：{}", message);
                channel.basicReject(tag, false);
            } else {
                log.error("消息处理失败", e);
                // 重新入队一次
                channel.basicNack(tag, false, true);
            }
        }
    }

    private void anotherOperation(LocalMqMessage mqMessage) {
        // 根据获取的消息处理其他业务逻辑
    }
}
