package com.joco.consumer.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by jocochen on 2020/5/19.
 */
@Configuration
@MapperScan({"com.joco.consumer.dao", "com.joco.mbg.mapper"})
public class MybatisConfig {
}
