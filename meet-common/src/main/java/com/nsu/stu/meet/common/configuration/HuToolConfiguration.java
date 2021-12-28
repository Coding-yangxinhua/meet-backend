package com.nsu.stu.meet.common.configuration;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class HuToolConfiguration {
    @Bean
    public Snowflake snowflake() {
        return IdUtil.createSnowflake(1,1);
    }

}
