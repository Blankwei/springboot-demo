package com.savoidage.springbootdistributeduidgenerate;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.baidu.fsg.uid.worker.dao")
public class SpringbootDistributeduidgenerateApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootDistributeduidgenerateApplication.class, args);
        System.out.println("springboot-distributed uid generate启动成功...");
    }

}
