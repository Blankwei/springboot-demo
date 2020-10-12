package com.savoidage.springbootdistributedtransaction.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Author: created by savoidage
 * CreateTime: 2020-10-12 11:21
 * Description: todo
 */
@Data
public class Product implements Serializable {

    private Integer productId; // 商品id
    private String productName; // 商品名称
    private String productDescription; // 描述
    private Integer productStock; // 库存
    private BigDecimal productPrice; // 价格
    private Date createTime; // 新增时间
    private Date updateTime; // 更新时间
}
