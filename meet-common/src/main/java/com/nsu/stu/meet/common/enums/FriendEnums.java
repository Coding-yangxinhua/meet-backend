package com.nsu.stu.meet.common.enums;

public enum FriendEnums {
    FOLLOW(0, "关注"),
    FAN(1, "粉丝");


    private final int value;
    private final String desc;

    FriendEnums(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }
    public static FriendEnums lookUp (int value) {
        for (FriendEnums s:
                FriendEnums.values()) {
            if (s.value == value) {
                return s;
            }
        }
        return FriendEnums.FOLLOW;
    }

    public int value() {
        return this.value;
    }

    public String desc() {
        return this.desc;
    }
}
