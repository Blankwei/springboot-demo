package com.savoidage.springbootidempotent.service;

import com.savoidage.springbootidempotent.entity.SysUser;

import java.util.List;

public interface SysUserService{

    int insert(SysUser pojo);

    int insertSelective(SysUser pojo);

    int insertList(List<SysUser> pojos);

    int update(SysUser pojo);

    SysUser findOne(Integer id);

    List<SysUser> findList();

    int delete(Integer id);
}
