package com.savoidage.springbootdistributedtransaction.scheduled;

import com.savoidage.springbootdistributedtransaction.dao.LocalMqMessageDao;
import com.savoidage.springbootdistributedtransaction.entity.LocalMqMessage;
import com.savoidage.springbootdistributedtransaction.service.RabbitMqService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * Author: created by savoidage
 * CreateTime: 2020-10-13 10:44
 * Description: 定时器处理
 */
@Slf4j
@Service
public class ScheduledService {

    @Autowired
    private RabbitMqService rabbitMqService;

    @Resource
    private LocalMqMessageDao localMqMessageDao;

    /**
     * 定时器扫描本地未更新的订单消息
     * 每隔5分钟扫描一次
     */
    @Scheduled(cron = "0 0/5 * * * ?")
    public void scanFailedLocalMqMessage() {
        log.info("定时扫描本地消息表-开启...");
        List<LocalMqMessage> messages = localMqMessageDao.findMessageList();
        if (!CollectionUtils.isEmpty(messages)) {
            for (LocalMqMessage message : messages) {
                // 重发消息
                rabbitMqService.retrySendMessage(message);
            }
        }
    }
}
