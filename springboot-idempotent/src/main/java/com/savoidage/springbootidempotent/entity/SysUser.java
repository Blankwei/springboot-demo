package com.savoidage.springbootidempotent.entity;


import lombok.Data;

import java.io.Serializable;

/**
 * Author: created by savoidage
 * CreateTime: 2020-08-21 16:52
 * Description: SysUser
 */
@Data
public class SysUser implements Serializable {

    private Integer id;
    private String userName;
    private String passWord;
    private String realName;

}
