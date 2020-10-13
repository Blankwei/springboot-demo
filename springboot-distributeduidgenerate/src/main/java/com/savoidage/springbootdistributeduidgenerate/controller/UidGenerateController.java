package com.savoidage.springbootdistributeduidgenerate.controller;

import com.savoidage.springbootdistributeduidgenerate.service.UidService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Author: created by savoidage
 * CreateTime: 2020-10-13 14:02
 * Description: uid controller
 */
@RestController
@RequestMapping(value = "/system")
public class UidGenerateController {

    @Resource
    private UidService uidService;

    @GetMapping("/getUid")
    public Long getId() {
        return uidService.getUid();
    }
}
