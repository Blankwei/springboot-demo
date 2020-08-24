package com.savoidage.springbootidempotent.demo.exception;

import com.savoidage.springbootidempotent.demo.base.CommonResult;
import com.savoidage.springbootidempotent.demo.base.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Author: created by savoidage
 * CreateTime: 2020-08-24 14:20
 * Description: 全局异常拦截
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 全局异常处理
     *
     * @param request  请求
     * @param response 响应
     * @param e        异常
     * @return
     */
    @ExceptionHandler(Exception.class)
    public CommonResult handleException(HttpServletRequest request, HttpServletResponse response, final Exception e) {
        e.printStackTrace();

        // 拦截serviceException
        if (e instanceof ServiceException) {
            if (((ServiceException) e).getCode() != null) {
                return CommonResult.failed(((ServiceException) e).getCode(), e.getMessage(), null);
            } else {
                return CommonResult.failed(ResultCode.FAILED.getCode(), e.getMessage() != null ? e.getMessage() : "", null);
            }
        }
        return CommonResult.failed(ResultCode.FAILED.getCode(), ResultCode.FAILED.getMessage());
    }
}
