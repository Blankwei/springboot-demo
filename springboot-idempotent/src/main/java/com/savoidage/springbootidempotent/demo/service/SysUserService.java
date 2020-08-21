package com.savoidage.springbootidempotent.demo.service;

import com.savoidage.springbootidempotent.demo.entity.SysUser;

import java.util.List;

public interface SysUserService{

    int insert(SysUser pojo);

    int insertSelective(SysUser pojo);

    int insertList(List<SysUser> pojos);

    int update(SysUser pojo);
}
