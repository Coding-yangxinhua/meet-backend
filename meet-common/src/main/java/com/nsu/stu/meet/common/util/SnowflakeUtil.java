package com.nsu.stu.meet.common.util;

import cn.hutool.core.lang.Snowflake;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SnowflakeUtil {

    public long nextId() {
        Snowflake snowflake = com.nsu.stu.meet.util.SpringContextUtil.getBean("snowflake", Snowflake.class);
        return snowflake.nextId();
    }
}
