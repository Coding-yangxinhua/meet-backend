package com.nsu.stu.meet.common.util;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import lombok.experimental.UtilityClass;
import org.springframework.util.StringUtils;

import java.util.Date;

@UtilityClass
public class JwtUtil {
    private final byte[] key ="zxcv123321AZ".getBytes();

    public String createToken(Long userId) {
        DateTime date = DateUtil.date();
        return JWT.create()
                .setPayload("userId", userId)
                .setKey(key)
                .setExpiresAt(DateUtil.offsetDay(date, 15))
                .sign();
    }
    private Object getTokenPayload(String token, String name) {
        boolean validToken = isValidToken(token);
        if (validToken) {
            JWT jwt = JWTUtil.parseToken(token);
            return jwt.getPayload(name);
        }
        return null;
    }
    public Long getTokenUserId(String token) {
        return (Long) getTokenPayload(token, "userId");
    }
    public boolean isValidToken(String token) {
        if (StringUtils.hasText(token)) {
            return JWTUtil.verify(token, key);
        }
        return false;
    }
}
