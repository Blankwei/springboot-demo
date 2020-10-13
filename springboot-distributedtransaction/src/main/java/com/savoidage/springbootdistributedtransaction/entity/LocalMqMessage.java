package com.savoidage.springbootdistributedtransaction.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Author: created by savoidage
 * CreateTime: 2020-10-13 09:39
 * Description: 本地消息表记录mq消息状态
 */
@Data
public class LocalMqMessage implements Serializable {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 消息类型
     */
    private String messageType;

    /**
     * 消息内容
     */
    private String messageContent;

    /**
     * 重试次数（上限5次）
     */
    private Integer retryCount;

    /**
     * 消息投递状态（0.投递中 1.投递成功 2.投递失败）
     */
    private Integer status;

    /**
     * 删除状态（true 已删除 false 未删除）
     */
    private Boolean delFlag;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 更新时间
     */
    private Date updateDate;

}
