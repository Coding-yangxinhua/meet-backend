package com.nsu.stu.meet.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

public enum GenderEnums  {

    MALE (0, "男"),

    FEMALE (0, "女"),

    SECRET (0, "保密");


    @EnumValue
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
