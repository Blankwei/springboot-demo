package com.savoidage.springbootdistributedtransaction.listener;

import com.rabbitmq.client.Channel;
import com.savoidage.springbootdistributedtransaction.config.RabbitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Author: created by savoidage
 * CreateTime: 2020-10-13 15:20
 * Description: 死信监听
 */
@Slf4j
@Component
public class DlListener {

    @RabbitListener(queues = RabbitConfig.DL_QUEUE)
    public void handleMessage(Channel channel, Message message, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        log.info("死信消息：" + new String(message.getBody()));
        // 人工处理死信
        operateDlMessage(new String(message.getBody()));

        channel.basicAck(tag,false);
    }

    private void operateDlMessage(String dlMessage){
        // 处理死信消息
    }
}
