package com.nsu.stu.meet.common.base;

import com.nsu.stu.meet.common.enums.ResultStatus;

public class BusinessException extends RuntimeException {

    public BusinessException() {}
    public BusinessException(String message) {
        super(message);
    }
}
