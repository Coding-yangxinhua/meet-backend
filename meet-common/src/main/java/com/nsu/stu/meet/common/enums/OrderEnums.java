package com.nsu.stu.meet.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

public enum OrderEnums {
    TIME(0, "按时间排序"),
    HOT(1, "按热度排序");

    @EnumValue
    @JsonValue
    private final int value;
    private final String desc;

    OrderEnums(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }
    public static OrderEnums lookUp (int value) {
        for (OrderEnums s:
                OrderEnums.values()) {
            if (s.value == value) {
                return s;
            }
        }
        return OrderEnums.TIME;
    }

    public int value() {
        return this.value;
    }

    public String desc() {
        return this.desc;
    }
}
