package com.nsu.stu.meet.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

public enum GenderEnums  {

    MALE (0, "男"),

    FEMALE (1, "女"),

    SECRET (2, "保密");


    @EnumValue
    @JsonValue
    public int value;
    public String desc;

    GenderEnums(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public Integer value() {
        return this.value;
    }
}
