package com.nsu.stu.meet.common.enums;


public enum DeletedEnums {
    /**
     *
     */
    DELETED_NO(0),
    /**
     *
     */
    DELETED_YES(1);

    public Integer code;

    private DeletedEnums(Integer code) {
        this.code = code;
    }

}
