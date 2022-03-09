package com.nsu.stu.meet.common.enums;

public enum SmsEnums {
    REGISTER(0,"REGISTER_CODE", "1326868"),
    LOGIN(1,"LOGIN_CODE", "1325316"),
    UPDATE(2, "UPDATE_CODE", "1325318");

    private final int type;
    private final String desc;
    private final String templateId;

    SmsEnums(int type, String desc, String templateId) {
        this.type = type;
        this.desc = desc;
        this.templateId = templateId;
    }

    public static SmsEnums lookUp (int type) {
        for (SmsEnums s:
             SmsEnums.values()) {
            if (s.type == type) {
                return s;
            }
        }
        return null;
    }


    public Integer type() {
        return this.type;
    }

    public String desc() {
        return this.desc;
    }

    public String templateId() {
        return this.templateId;
    }
}
