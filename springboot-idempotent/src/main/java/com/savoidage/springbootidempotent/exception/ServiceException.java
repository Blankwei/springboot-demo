package com.savoidage.springbootidempotent.exception;

/**
 * Author: created by savoidage
 * CreateTime: 2020-08-24 12:32
 * Description: service 异常
 */
public class ServiceException extends RuntimeException {

    private Integer code;

    public ServiceException() {
        super();
    }

    public ServiceException(String msg) {
        super(msg);
    }

    public ServiceException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return getMessage();
    }
}
