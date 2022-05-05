package com.nsu.stu.meet.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

public enum RelationEnums {

    BLOCK (-1, "拉黑"),

    NORMAL (0, "取关"),

    FOLLOW (1, "关注"),

    LIKE (2, "特别关心");


    @EnumValue
    @JsonValue
    private final int value;
    private final String desc;

    RelationEnums(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public int value() {
        return this.value;
    }

    public String desc() {
        return this.desc;
    }
}
