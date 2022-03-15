package com.nsu.stu.meet.common.enums;

public enum dataEnums {

    KB ("k", 1),

    MB ("m", 1024),

    GB("g", 1048576);


    public String label;
    public Integer value;

    private dataEnums(String label, Integer value) {
        this.label = label;
        this.value = value;
    }

    public Integer value() {
        return this.value;
    }
}
