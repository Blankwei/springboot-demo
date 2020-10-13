package com.savoidage.springbootdistributedtransaction;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@MapperScan(value = "com.savoidage.springbootdistributedtransaction.dao")
public class SpringbootDistributedtransactionApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootDistributedtransactionApplication.class, args);
        System.out.println("springboot-distributed transaction 项目启动成功...");
    }

}
