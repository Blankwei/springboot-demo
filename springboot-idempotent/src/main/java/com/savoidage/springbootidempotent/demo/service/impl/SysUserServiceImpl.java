package com.savoidage.springbootidempotent.demo.service.impl;

import com.savoidage.springbootidempotent.demo.dao.SysUserDao;
import com.savoidage.springbootidempotent.demo.entity.SysUser;
import com.savoidage.springbootidempotent.demo.service.SysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Author: created by savoidage
 * CreateTime: 2020-08-21 16:54
 * Description: SysUserServiceImpl
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private SysUserDao sysUserDao;

    @Override
    public int insert(SysUser pojo) {
        return sysUserDao.insert(pojo);
    }

    @Override
    public int insertSelective(SysUser pojo) {
        return sysUserDao.insertSelective(pojo);
    }

    @Override
    public int insertList(List<SysUser> pojos) {
        return sysUserDao.insertList(pojos);
    }

    @Override
    public int update(SysUser pojo) {
        return sysUserDao.update(pojo);
    }
}
