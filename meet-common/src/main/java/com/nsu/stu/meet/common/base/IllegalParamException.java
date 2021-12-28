package com.nsu.stu.meet.common.base;

import com.nsu.stu.meet.common.enums.ResultStatus;

public class IllegalParamException extends RuntimeException{
    public IllegalParamException() {}
    public IllegalParamException(String message) {
        super(message);
    }
}
