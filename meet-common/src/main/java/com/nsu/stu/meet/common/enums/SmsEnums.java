package com.nsu.stu.meet.common.enums;

public enum SmsEnums {
    REGISTER(0,"register_code"),
    LOGIN(1,"login_code"),
    UPDATE(2, "update_code");

    private final Integer key;
    private final String value;

    SmsEnums(Integer key, String value) {
        this.key = key;
        this.value = value;
    }


    public Integer key() {
        return this.key;
    }

    public String value() {
        return this.value;
    }
}
