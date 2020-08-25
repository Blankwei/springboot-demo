package com.savoidage.springbootidempotent.constant;

/**
 * Author: created by savoidage
 * CreateTime: 2020-08-24 11:30
 * Description: 模块常量
 */
public class SysConstant {

    // 幂等token固定值
    public static final String API_IDEMPOTENT_TOKEN_PREFIX = "api_idempotent_token";
    // 幂等token默认有效期
    public static final Long DEFAULT_EXPIRED_TIME = 60L;


    /***************** 以下是业务逻辑返回message *****************/
    public static final String PARAMETER_NOT_CONTAIN_TOKEN = "请求的参数中没有token";
    public static final String REPETITIVE_OPERATION = "请勿重复操作";
}
