package com.nsu.stu.meet.common.enums;

public enum ChatEnums {
    NORMAL(0, "正常接受"),
    NOT_ATTENTION(1, "接收但不提醒"),
    BLOCK(2, "屏蔽");


    private final int value;
    private final String desc;

    ChatEnums(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }
    public static ChatEnums lookUp (int value) {
        for (ChatEnums s:
                ChatEnums.values()) {
            if (s.value == value) {
                return s;
            }
        }
        return ChatEnums.NORMAL;
    }

    public int value() {
        return this.value;
    }

    public String desc() {
        return this.desc;
    }
}
