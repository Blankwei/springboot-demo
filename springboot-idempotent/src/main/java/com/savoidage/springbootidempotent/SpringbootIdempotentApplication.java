package com.savoidage.springbootidempotent;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "com.savoidage.springbootidempotent.demo.dao")
public class SpringbootIdempotentApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootIdempotentApplication.class, args);
        System.out.println("springboot-idempotent 项目启动成功...");
    }

}
