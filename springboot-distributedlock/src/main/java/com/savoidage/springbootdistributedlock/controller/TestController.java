package com.savoidage.springbootdistributedlock.controller;

import com.savoidage.springbootdistributedlock.annotation.RedisLock;
import com.savoidage.springbootdistributedlock.utils.RedisUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Author: created by savoidage
 * CreateTime: 2020-08-25 14:01
 * Description: TestController
 */
@RestController
@RequestMapping(value = "/api")
public class TestController {

    @Resource
    private RedisUtils redisUtils;

    @GetMapping(value = "/test")
    public String test(){
        redisUtils.set("test","this is a test value",60L);
        return (String) redisUtils.get("test");
    }

    @RedisLock("/api/create")
    @PostMapping(value = "create")
    public String create(@RequestParam("businessId") String businessId){

        return "success";
    }

}
