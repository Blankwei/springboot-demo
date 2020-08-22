package com.savoidage.springbootidempotent.demo.service.impl;

import com.savoidage.springbootidempotent.demo.dao.SysUserDao;
import com.savoidage.springbootidempotent.demo.entity.SysUser;
import com.savoidage.springbootidempotent.demo.service.SysUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.beans.Transient;
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

    @Transactional
    @Override
    public int insert(SysUser pojo) {
        return sysUserDao.insert(pojo);
    }

    @Transactional
    @Override
    public int insertSelective(SysUser pojo) {
        return sysUserDao.insertSelective(pojo);
    }

    @Transactional
    @Override
    public int insertList(List<SysUser> pojos) {
        return sysUserDao.insertList(pojos);
    }

    @Transactional
    @Override
    public int update(SysUser pojo) {
        return sysUserDao.update(pojo);
    }

    @Override
    public SysUser findOne(Integer id) {
        return sysUserDao.findOne(id);
    }

    @Override
    public List<SysUser> findList() {
        return sysUserDao.findList();
    }

    @Transactional
    @Override
    public int delete(Integer id) {
        return sysUserDao.delete(id);
    }
}
