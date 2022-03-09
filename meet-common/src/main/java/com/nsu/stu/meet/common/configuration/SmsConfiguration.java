package com.nsu.stu.meet.common.configuration;

;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.sms.v20190711.SmsClient;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SmsConfiguration {
    // 1 初始化用户身份信息（secretId, secretKey）
    @Value(value = "${cloud.secretId}")
    private String secretId;
    @Value(value = "${cloud.secretKey}")
    private String secretKey;



    @Bean
    public SmsClient smsClient() {
        /* 实例化一个认证对象，入参需要传入腾讯云账户密钥对secretId，secretKey */
        Credential cred = new Credential(secretId, secretKey);
        /* 实例化要请求产品(以sms为例)的client对象
         * 第二个参数是地域信息*/
        return new SmsClient(cred, "ap-guangzhou",  new ClientProfile());
    }

}
