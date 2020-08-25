package com.savoidage.springbootidempotent.service.impl;

import com.savoidage.springbootidempotent.constant.SysConstant;
import com.savoidage.springbootidempotent.exception.ServiceException;
import com.savoidage.springbootidempotent.service.TokenService;
import com.savoidage.springbootidempotent.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * Author: created by savoidage
 * CreateTime: 2020-08-24 11:28
 * Description: token service 实现类
 */
@Slf4j
@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 生成幂等token
     *
     * @param businessId 业务id
     * @return token
     */
    @Override
    public synchronized String generateToken(String businessId) {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        StringBuilder token = new StringBuilder();
        // 规则： 前缀 + 业务id + uuid
        token.append(SysConstant.API_IDEMPOTENT_TOKEN_PREFIX).append("_").append(businessId).append("_").append(uuid);
        if (!StringUtils.isEmpty(token)) {
            boolean set = redisUtils.set(token.toString(), token.toString(), SysConstant.DEFAULT_EXPIRED_TIME);
            if (!set) {
                throw new ServiceException("token存入redis失败：" + businessId);
            }
            return token.toString();
        }
        return "";
    }

    /**
     * 校验幂等token的合法性
     *
     * @param request 请求
     * @return 校验结果
     */
    @Override
    public synchronized boolean checkToken(HttpServletRequest request) {
        // 从请求头中获取token
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            throw new ServiceException(SysConstant.PARAMETER_NOT_CONTAIN_TOKEN);
        }
        // redis中是否存在token：
        // 由于第一次请求会先获取token 并存入redis 然后请求接口时  请求头带上该token  并通过拦截器进行校验
        // 如果token 存在 正常处理业务 然后删除token  如果是重复请求  由于token已被删除  校验时不会通过 会返回 请勿重复操作等提示
        // 如果token 不存在  说明参数不合法等
        if (!redisUtils.exists(token)) {
            throw new ServiceException(SysConstant.REPETITIVE_OPERATION);
        }
        boolean remove = redisUtils.remove(token);
        if (!remove) {
            throw new ServiceException(SysConstant.REPETITIVE_OPERATION);
        }
        return true;
    }
}
