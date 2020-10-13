package com.savoidage.springbootdistributedtransaction.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: created by savoidage
 * CreateTime: 2020-10-12 14:54
 * Description: rabbit mq 配置
 */
@Configuration
public class RabbitConfig {

    // 订单交换机
    public final static String ORDER_EXCHANGE = "orderExchange";

    // 订单路由key
    public final static String ORDER_ROUTE_KEY = "orderRoutingKey";

    // 订单队列
    public final static String ORDER_QUEUE = "orderQueue";

    // 死信队列
    public final static String DL_QUEUE = "dlQueue";

    // 死信交换机
    public final static String DL_REXCHANGE = "dlExchange";

    // 死信路由key
    public final static String DL_ROUTIONG_KEY = "dlRoutingKey";

    /**
     * 死信队列
     * @return
     */
    @Bean
    public Queue dlQueue(){
        return QueueBuilder.durable(DL_QUEUE)
                .build();
    }

    @Bean
    public DirectExchange dlExchange(){
        return (DirectExchange) ExchangeBuilder.directExchange(DL_REXCHANGE).build();
    }

    @Bean
    public Binding dlMessageBinding(){
        return BindingBuilder.bind(dlQueue()).to(dlExchange()).with(DL_ROUTIONG_KEY);
    }

    @Bean
    public DirectExchange messageDirectExchange() {
        return (DirectExchange) ExchangeBuilder.directExchange(ORDER_EXCHANGE)
                .durable(true)
                .build();
    }

    @Bean
    public Queue messageQueue() {
        return QueueBuilder.durable(ORDER_QUEUE)
                //配置死信
                .withArgument("x-dead-letter-exchange",ORDER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key",DL_ROUTIONG_KEY)
                .build();
    }

    @Bean
    public Binding messageBinding() {
        return BindingBuilder.bind(messageQueue())
                .to(messageDirectExchange())
                .with(ORDER_ROUTE_KEY);
    }
}
