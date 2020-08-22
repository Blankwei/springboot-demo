-- auto Generated on 2020-08-21 16:54:11 
-- DROP TABLE IF EXISTS `sys_user`; 
CREATE TABLE `sys_user`(
    `id` INT (11) NOT NULL AUTO_INCREMENT COMMENT 'id',
    `user_name` VARCHAR (300) NOT NULL DEFAULT '' COMMENT 'userName',
    `pass_word` VARCHAR (50) NOT NULL DEFAULT '' COMMENT 'passWord',
    `real_name` VARCHAR (50) NOT NULL DEFAULT '' COMMENT 'realName',
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '`sys_user`';

alter table sys_user add unique unique_realName(real_name);