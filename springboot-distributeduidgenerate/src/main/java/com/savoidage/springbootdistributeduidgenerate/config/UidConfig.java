package com.savoidage.springbootdistributeduidgenerate.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * Author: created by savoidage
 * CreateTime: 2020-10-13 12:07
 * Description: uid config
 */
@Configuration
@ImportResource(locations = { "classpath:uid/cached-uid-spring.xml" })
public class UidConfig {
}
