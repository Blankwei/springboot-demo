package com.savoidage.springbootdistributeduidgenerate.service;

import com.baidu.fsg.uid.UidGenerator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Author: created by savoidage
 * CreateTime: 2020-10-13 14:01
 * Description: UidService
 */
@Service("uidService")
@Component
public class UidService {

    @Resource
    private UidGenerator uidGenerator;

    /**
     * 获取自动生成的分布式uid
     * @return
     */
    public long getUid() {
        return uidGenerator.getUID();
    }
}
