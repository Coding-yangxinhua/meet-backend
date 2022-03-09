package com.nsu.stu.meet.service;

import com.nsu.stu.meet.common.base.ResponseEntity;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;

import javax.validation.constraints.NotNull;


public interface SmsService {
    ResponseEntity<SendSmsResponse> sendSms(String token, String mobile, int type);

    Boolean checkCode(@NotNull String mobile, @NotNull String code, @NotNull int type);
}
