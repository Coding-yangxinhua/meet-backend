package com.nsu.stu.meet.common.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@ConfigurationProperties(prefix = "sso")
@Getter
@Setter
public class SsoConfig {
    private String excludePath;

    private Long defaultUserId = 1L;

    private String defaultNickName = "SYSTEM";

    private Boolean enable;

    private Cookie cookie;


    // 根据逗号分割
    public String[] getExcludePathArray() {
        if (!StringUtils.hasText(this.excludePath)) {
            return new String[]{};
        }
        return excludePath.split(",");
    }

    @Getter
    @Setter
    public static class Cookie {
        private String host;
        private String path;
    }
}
