package com.nsu.stu.meet.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

public enum LimitEnums {

    PRIVATE (-1, "仅自己可见"),

    PUBLIC (0, "公开"),

    FOLLOW (1, "关注可见");


    @EnumValue
    @JsonValue
    public int value;
    public String desc;

    LimitEnums(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public Integer value() {
        return this.value;
    }
}
