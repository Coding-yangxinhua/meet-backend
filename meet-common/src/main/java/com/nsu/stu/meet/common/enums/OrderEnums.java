package com.nsu.stu.meet.common.enums;

public enum OrderEnums {
    TIME(0, "按时间排序"),
    HOT(1, "按热度排序");

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
