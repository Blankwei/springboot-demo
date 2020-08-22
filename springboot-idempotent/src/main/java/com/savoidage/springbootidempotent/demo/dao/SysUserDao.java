package com.savoidage.springbootidempotent.demo.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import com.savoidage.springbootidempotent.demo.entity.SysUser;

@Mapper
public interface SysUserDao {

    int insert(@Param("pojo") SysUser pojo);

    int insertSelective(@Param("pojo") SysUser pojo);

    int insertList(@Param("pojos") List<SysUser> pojo);

    int update(@Param("pojo") SysUser pojo);

    SysUser findOne(Integer id);

    List<SysUser> findList();

    int delete(Integer id);
}
