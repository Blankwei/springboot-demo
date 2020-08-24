package com.savoidage.springbootidempotent.demo.base;

/**
 * Author: created by savoidage
 * CreateTime: 2020-08-24 14:23
 * Description: 返回结果码
 */
public enum ResultCode {

    // 成功
    SUCCESS(200, "操作成功"),

    // 失败
    FAILED(500, "操作失败");

    // 状态码
    private int code;
    // 状态信息
    private String message;

    private ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
