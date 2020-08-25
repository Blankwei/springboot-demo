package com.savoidage.springbootidempotent.controller;

import com.savoidage.springbootidempotent.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Author: created by savoidage
 * CreateTime: 2020-08-24 15:19
 * Description: token控制器
 */
@RestController
@RequestMapping(value = "/api")
public class TokenController {

    @Autowired
    private TokenService tokenService;

    /**
     * 获取token
     *
     * @param businessId 业务id
     * @return token
     */
    @GetMapping(value = "/token/{businessId}")
    public String getToken(@PathVariable("businessId") String businessId) {
        return tokenService.generateToken(businessId);
    }
}
