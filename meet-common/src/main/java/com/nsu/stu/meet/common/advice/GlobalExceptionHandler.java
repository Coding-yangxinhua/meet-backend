package com.nsu.stu.meet.common.advice;

import com.nsu.stu.meet.common.base.BusinessException;
import com.nsu.stu.meet.common.base.IllegalParamException;
import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.common.enums.ResultStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {BusinessException.class})
    @ResponseBody
    public ResponseEntity<ResultStatus> handleBusinessException(BusinessException e){
        return ResponseEntity.builder().status(ResultStatus.BIZ_ERROR).message(e.getMessage()).build();
    }
    @ExceptionHandler(value = {IllegalParamException.class})
    @ResponseBody
    public ResponseEntity<ResultStatus> handleIllegalParamException(IllegalParamException e){
        return ResponseEntity.builder().status(ResultStatus.CHECK_ERROR).message(e.getMessage()).build();
    }
    @ExceptionHandler(value = { IllegalArgumentException.class})
    @ResponseBody
    public ResponseEntity<ResultStatus> handleIllegalArgumentException(IllegalArgumentException e){
        return ResponseEntity.builder().status(ResultStatus.CHECK_ERROR).message(ResultStatus.CHECK_ERROR.message()).build();
    }

    // 当异常为Exception或Error，返回状态为 系统错误 的响应类
    @ExceptionHandler(value = { Exception.class, Error.class })
    @ResponseBody
    ResponseEntity<ResultStatus> handleOtherException(Throwable e) {
        return ResponseEntity.builder().status(ResultStatus.SYS_ERROR)
                .message(e.getMessage()).build();
    }

}
