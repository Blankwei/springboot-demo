/**
    创建本地消息表
 */
-- DROP TABLE IF EXISTS `local_mq_message`;
CREATE TABLE `local_mq_message`(
    `id` BIGINT (15) NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `message_type` VARCHAR (50) NOT NULL DEFAULT '' COMMENT '消息类型',
    `message_content` VARCHAR (50) NOT NULL DEFAULT '' COMMENT '消息内容',
    `retry_count` INT (11) NOT NULL DEFAULT -1 COMMENT '重试次数（上限5次）',
    `status` INT (11) NOT NULL DEFAULT -1 COMMENT '消息投递状态（0.投递中 1.投递成功 2.投递失败）',
    `del_flag` TINYINT (3) NOT NULL DEFAULT 0 COMMENT '删除状态（true 已删除 false 未删除）',
    `create_date` DATETIME NOT NULL DEFAULT '1000-01-01 00:00:00' COMMENT '创建时间',
    `update_date` DATETIME NOT NULL DEFAULT '1000-01-01 00:00:00' COMMENT '更新时间',
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '`本地mq消息记录表`';

/**
    创建订单表
 */
-- DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`(
    `order_id` BIGINT (15) NOT NULL AUTO_INCREMENT COMMENT '订单id',
    `buyer_user_id` INT (11) NOT NULL DEFAULT -1 COMMENT '买家id',
    `buyer_user_name` VARCHAR (50) NOT NULL DEFAULT '' COMMENT '买家名称',
    `order_amount` DECIMAL (13,4) NOT NULL DEFAULT -1 COMMENT '订单金额',
    `order_status` INT (11) NOT NULL DEFAULT -1 COMMENT '订单状态',
    `pay_status` INT (11) NOT NULL DEFAULT -1 COMMENT '支付状态',
    `create_time` DATETIME NOT NULL DEFAULT '1000-01-01 00:00:00' COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT '1000-01-01 00:00:00' COMMENT '更新时间',
    PRIMARY KEY (`order_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '`订单表`';
