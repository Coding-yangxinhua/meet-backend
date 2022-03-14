package com.nsu.stu.meet.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.nsu.stu.meet.common.base.BusinessException;
import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.common.config.CloudConfig;
import com.nsu.stu.meet.common.constant.SystemConstants;
import com.nsu.stu.meet.common.enums.SmsEnums;
import com.nsu.stu.meet.common.util.AssertUtil;
import com.nsu.stu.meet.common.util.JwtUtil;
import com.nsu.stu.meet.common.util.ValidateUtil;
import com.nsu.stu.meet.service.SmsService;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.concurrent.TimeUnit;

@Service
public class SmsServiceImpl implements SmsService {
    @Autowired
    private SmsClient smsClient;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private CloudConfig cloudConfig;
    public ResponseEntity<SendSmsResponse> sendSms(String token, String mobile, int type) {
        // 通过token获取信息
        Long userId = JwtUtil.getTokenUserId(token);
        // 获得短信类型的枚举
        SmsEnums smsEnums = SmsEnums.lookUp(type);
        // 模板类型
        if (smsEnums == null) {
            return ResponseEntity.checkError(SystemConstants.UNKNOWN_ERROR);
        }
        // 号码格式校验
        if (!ValidateUtil.isMobile(mobile)) {
            return ResponseEntity.checkError(SystemConstants.MOBILE_ERROR);
        }
        mobile = "+86" + mobile;
        String keyPhone = smsEnums.desc() + "_" + mobile;
        // 随机数验证码
        String code = RandomUtil.randomNumbers(4);
        // 查询间隔时间
        Boolean isNew = redisTemplate.opsForValue().setIfAbsent(keyPhone + "_interval", DateUtil.now(), 1, TimeUnit.MINUTES);
        // 存在与间隔时间内，则不处理
        if (Boolean.FALSE.equals(isNew)) {
            return null;
        }
        // 存入数据库
        redisTemplate.opsForValue().set(keyPhone, code, 5, TimeUnit.MINUTES);
        // 存入缓存
        SendSmsRequest req = new SendSmsRequest();
        // 存储用户id
        req.setSessionContext(String.valueOf(userId));
        // 设置模板id
        req.setTemplateID(smsEnums.templateId());
        // 设置发送的手机号码
        req.setPhoneNumberSet(new String[]{mobile});

        req.setSmsSdkAppid(this.cloudConfig.getSms().getSdkAppId());

        req.setSign(this.cloudConfig.getSms().getSignName());

        String[] templateParamSet = new String[] {code};
        req.setTemplateParamSet(templateParamSet);

        SendSmsResponse res = null;
        try {
            res = smsClient.SendSms(req);
        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
            return ResponseEntity.checkError(SystemConstants.SMS_ERROR);
        }
        return ResponseEntity.ok(res);
    }

    @Override
    public Boolean checkCode(@NotNull String mobile, @NotNull String code, @NotNull int type) {
        // 获得短信类型的美剧
        SmsEnums smsEnums = SmsEnums.lookUp(type);
        if (smsEnums != null) {
            String mobileCode = redisTemplate.opsForValue().get(smsEnums.desc() + "_" + "+86" + mobile);
            return mobileCode != null && mobileCode.equals(code);
        }
        return false;
    }

}
