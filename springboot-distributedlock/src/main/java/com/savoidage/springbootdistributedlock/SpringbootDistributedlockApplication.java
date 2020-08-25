package com.savoidage.springbootdistributedlock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootDistributedlockApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootDistributedlockApplication.class, args);
        System.out.println("springboot-distributedlock 项目启动成功...");
    }

}
