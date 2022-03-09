package com.nsu.stu.meet.common.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "cloud")
@Getter
@Setter
public class CloudConfig {
    private String secretId;
    private String secretKey;
    private Sms sms;

    @Getter
    @Setter
    public static class Sms{
        private String sdkAppId;
        private String signName;
    }
}
