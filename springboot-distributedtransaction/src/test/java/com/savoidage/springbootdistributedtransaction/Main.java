package com.savoidage.springbootdistributedtransaction;

import com.savoidage.springbootdistributedtransaction.entity.Order;
import com.savoidage.springbootdistributedtransaction.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Date;


@RunWith(SpringRunner.class)
@SpringBootTest
public class Main {

    @Autowired
    private OrderService orderService;

    @Test
    public void test(){
        Order order = new Order();
        order.setOrderId(1432238L);
        order.setBuyerUserId(3414324);
        order.setBuyerUserName("涨价或");
        order.setOrderAmount(new BigDecimal("250.88"));
        order.setOrderStatus(1);
        order.setPayStatus(1);
        order.setCreateTime(new Date());
        order.setUpdateTime(new Date());
        orderService.saveOrder(order);
    }
}
