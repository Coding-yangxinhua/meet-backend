package com.nsu.stu.meet.common.enums;

public enum MybatisEnums {
    DELETED(1),
    NOT_DELETED(0);


    private final int value;

    MybatisEnums(int value) {
        this.value = value;
    }
    public int value() {
        return this.value;
    }

}
