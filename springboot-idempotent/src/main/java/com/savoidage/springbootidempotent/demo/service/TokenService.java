package com.savoidage.springbootidempotent.demo.service;

import javax.servlet.http.HttpServletRequest;

/**
 * Author: created by savoidage
 * CreateTime: 2020-08-24 11:24
 * Description: token service
 */
public interface TokenService {

    String generateToken(String businessId);

    boolean checkToken(HttpServletRequest request);
}
