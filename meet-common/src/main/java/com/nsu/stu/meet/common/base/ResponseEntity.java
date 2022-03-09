package com.nsu.stu.meet.common.base;

import com.nsu.stu.meet.common.enums.ResultStatus;

import java.io.Serializable;

public class ResponseEntity<R> implements Serializable {
    private static final long serialVersionUID = -2774548248088883523L;
    private R result;
    private String message;
    private ResultStatus resultStatus;
    private int code;

    public ResponseEntity() {
        this.message = ResultStatus.OK.message();
        this.resultStatus = ResultStatus.OK;
        this.code = ResultStatus.OK.value();
    }

    public ResponseEntity(ResultStatus resultStatus) {
        this.message = ResultStatus.OK.message();
        this.resultStatus = ResultStatus.OK;
        this.code = ResultStatus.OK.value();
        this.resultStatus = resultStatus;
        this.code = resultStatus.value();
        this.message = resultStatus.message();
    }

    public ResponseEntity(ResultStatus resultStatus, R result) {
        this.message = ResultStatus.OK.message();
        this.resultStatus = ResultStatus.OK;
        this.code = ResultStatus.OK.value();
        this.result = result;
        this.resultStatus = resultStatus;
        this.code = resultStatus.value();
        this.message = resultStatus.message();
    }

    public ResponseEntity(ResultStatus resultStatus, String message, R result) {
        this.message = ResultStatus.OK.message();
        this.resultStatus = ResultStatus.OK;
        this.code = ResultStatus.OK.value();
        this.result = result;
        this.message = message;
        this.resultStatus = resultStatus;
        this.code = resultStatus.value();
    }

    public R getResult() {
        return this.result;
    }

    public void setResult(R result) {
        this.result = result;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResultStatus getStatus() {
        return this.resultStatus;
    }

    public void setStatus(ResultStatus resultStatus) {
        this.resultStatus = resultStatus;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static <R> ResponseEntity<R> ok() {
        return (new ResponseEntity.Builder(ResultStatus.OK, null, null)).build();
    }

    public static <R> ResponseEntity<R> ok(R result) {
        return (new ResponseEntity.Builder(ResultStatus.OK, null, result)).build();
    }
    public static <R> ResponseEntity<R> ok(String msg, R result) {
        return (new ResponseEntity.Builder(ResultStatus.OK, msg, result)).build();
    }
    public static <R> ResponseEntity<R> ok(String msg) {
        return (new ResponseEntity.Builder(ResultStatus.OK, msg, null)).build();
    }

    public static <R> ResponseEntity<R> checkError(String msg) {
        return (new ResponseEntity.Builder(ResultStatus.CHECK_ERROR, msg, null)).build();
    }

    public static <R> ResponseEntity.Builder<R> builder() {
        return new ResponseEntity.Builder();
    }

    public static class Builder<R> {
        private R result;
        private ResultStatus resultStatus;
        private String message;
        private int code;

        public Builder() {
            this.resultStatus = ResultStatus.OK;
            this.code = ResultStatus.OK.value();
        }
        public Builder(ResultStatus status, String msg, R result) {
            this.resultStatus = status;
            this.code = status.value();
            this.message = msg;
            this.result = result;
        }

        public <R> ResponseEntity<R> build() {
            return new ResponseEntity(this.resultStatus, this.message == null ? this.resultStatus.message() : this.message, this.result);
        }

        public ResponseEntity.Builder<R> status(ResultStatus resultStatus) {
            this.resultStatus = resultStatus;
            this.code = resultStatus.value();
            return this;
        }

        public ResponseEntity.Builder<R> result(R result) {
            this.result = result;
            return this;
        }

        public ResponseEntity.Builder<R> message(String message) {
            this.message = message;
            return this;
        }
    }
}
