package com.savoidage.springbootidempotent.dao;

import com.savoidage.springbootidempotent.entity.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserDao {

    int insert(@Param("pojo") SysUser pojo);

    int insertSelective(@Param("pojo") SysUser pojo);

    int insertList(@Param("pojos") List<SysUser> pojo);

    int update(@Param("pojo") SysUser pojo);

    SysUser findOne(Integer id);

    List<SysUser> findList();

    int delete(Integer id);
}
