package com.savoidage.springbootidempotent.demo.controller;

import com.savoidage.springbootidempotent.demo.entity.SysUser;
import com.savoidage.springbootidempotent.demo.service.SysUserService;
import com.savoidage.springbootidempotent.demo.utils.JsonUtils;
import com.savoidage.springbootidempotent.demo.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Author: created by savoidage
 * CreateTime: 2020-08-21 16:59
 * Description: SysUserController
 */
@RestController
@RequestMapping(value = "/api")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * redis连接测试
     * @param redisValue
     * @return
     */
    @GetMapping(value = "/test")
    public String testRedis(@RequestParam("redisValue") String redisValue) {
        if (!StringUtils.isEmpty(redisValue)) {
            redisUtils.set("test", redisValue, 60L);
        }
        Object test = redisUtils.get("test");
        return StringUtils.isEmpty(test) ? "nothing" : JsonUtils.jsonToObject(test.toString(), String.class);
    }

    /**
     * 查询用户信息 restful get
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/sysUser/{id}")
    public SysUser findOne(@PathVariable("id") Integer id) {
        return sysUserService.findOne(id);
    }

    /**
     * 查询用户列表 restful get
     *
     * @return
     */
    @GetMapping(value = "/sysUser")
    public List<SysUser> findList() {
        return sysUserService.findList();
    }

    /**
     * 新增用户 restful post
     *
     * @param sysUser
     * @return
     */
    @PostMapping(value = "/sysUser")
    public String saveSysUser(@RequestBody SysUser sysUser) {
        int insert = sysUserService.insert(sysUser);
        return insert > 0 ? "success" : "failed";
    }

    /**
     * 更新用户 restful put
     *
     * @param sysUser
     * @return
     */
    @PutMapping(value = "/sysUser")
    public String updateSysUser(@RequestBody SysUser sysUser) {
        int update = sysUserService.update(sysUser);
        return update > 0 ? "success" : "failed";
    }

    /**
     * 删除用户 restful delete
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/sysUser/{id}")
    public String deleteSysUser(@PathVariable("id") Integer id) {
        int delete = sysUserService.delete(id);
        return delete > 0 ? "success" : "failed";
    }

}
