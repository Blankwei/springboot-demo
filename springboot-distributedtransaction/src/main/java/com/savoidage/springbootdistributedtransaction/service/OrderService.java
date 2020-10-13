package com.savoidage.springbootdistributedtransaction.service;

import com.savoidage.springbootdistributedtransaction.dao.LocalMqMessageDao;
import com.savoidage.springbootdistributedtransaction.dao.OrderDao;
import com.savoidage.springbootdistributedtransaction.entity.LocalMqMessage;
import com.savoidage.springbootdistributedtransaction.entity.Order;
import com.savoidage.springbootdistributedtransaction.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Author: created by savoidage
 * CreateTime: 2020-10-13 10:22
 * Description: orderService
 */
@Slf4j
@Service("orderService")
public class OrderService {

    @Resource
    private OrderDao orderDao;

    @Resource
    private LocalMqMessageDao localMqMessageDao;

    @Autowired
    private RabbitMqService rabbitMqService;

    @Value("${distributed.uid.application.name}")
    private String uidGenerateServiceUrl;

    @Transactional
    public void saveOrder(Order order){
        // 处理订单相关逻辑
        orderDao.insert(order);

        LocalMqMessage localMqMessage = new LocalMqMessage();
        // 远程调用获取分布式id
        Long uid = null;
        try {
            String uidStr = HttpUtils.get(uidGenerateServiceUrl+"/system/getUid");
            uid = Long.parseLong(uidStr);
        } catch (Exception e) {
            log.error("远程调用获取分布式id失败..");
            e.printStackTrace();
        }
        localMqMessage.setId(uid);
        localMqMessage.setMessageType("order");
        localMqMessage.setMessageContent(order.getOrderId()+"");
        localMqMessage.setRetryCount(0);
        localMqMessage.setStatus(0);
        localMqMessage.setDelFlag(false);
        localMqMessage.setCreateDate(new Date());
        localMqMessage.setUpdateDate(new Date());
        localMqMessageDao.insert(localMqMessage);

        // 发送mq消息
        rabbitMqService.sendMessage(localMqMessage);
    }
}
