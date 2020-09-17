package com.nb.shiro.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * create by lihaoyang on 2020/9/10
 */
@Configuration
@MapperScan(value={"com.nb.shiro.mapper"})
public class MybatisPlusConfig {
}
