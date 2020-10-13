package com.savoidage.springbootdistributedtransaction.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Author: created by savoidage
 * CreateTime: 2020-10-12 11:21
 * Description: 订单实体
 */
@Data
public class Order implements Serializable {

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 买家id
     */
    private Integer buyerUserId;

    /**
     * 买家名称
     */
    private String buyerUserName;

    /**
     * 订单金额
     */
    private BigDecimal orderAmount;

    /**
     * 订单状态
     */
    private Integer orderStatus;

    /**
     * 支付状态
     */
    private Integer payStatus;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
