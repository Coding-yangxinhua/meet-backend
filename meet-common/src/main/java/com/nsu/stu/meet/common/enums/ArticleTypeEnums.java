package com.nsu.stu.meet.common.enums;

public enum ArticleTypeEnums {
    NEW(0, "最新"),
    HOT(1, "热门"),
    FOLLOW(2, "关注"),
    POINT (3, "指定用户");


    private final int value;
    private final String desc;

    ArticleTypeEnums(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }
    public static ArticleTypeEnums lookUp (int value) {
        for (ArticleTypeEnums s:
                ArticleTypeEnums.values()) {
            if (s.value == value) {
                return s;
            }
        }
        return ArticleTypeEnums.NEW;
    }

    public int value() {
        return this.value;
    }

    public String desc() {
        return this.desc;
    }
}
