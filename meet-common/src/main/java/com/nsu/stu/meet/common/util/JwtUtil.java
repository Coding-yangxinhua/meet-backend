package com.nsu.stu.meet.common.util;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.nsu.stu.meet.common.base.IllegalParamException;
import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.common.constant.SystemConstants;
import com.nsu.stu.meet.common.enums.ResultStatus;
import lombok.experimental.UtilityClass;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@UtilityClass
public class JwtUtil {
    private final byte[] key ="zxcv123321AZ".getBytes();

    public String createToken(Long userId) {
        DateTime date = DateUtil.date();
        return JWT.create()
                .setPayload("userId", userId)
                .setKey(key)
                .setExpiresAt(DateUtil.offsetDay(date, 7))
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
        boolean verify = false;
        if (StringUtils.hasText(token)) {
            verify = JWTUtil.verify(token, key);
        }
        return verify;
    }
    public String getTokenFromCookies(Cookie[] cookies) {
        String token = null;
        for (Cookie cookie:
                cookies) {
            if (SystemConstants.TOKEN_NAME.equals(cookie.getName())) {
                token = cookie.getValue();
            }
        }
        return token;
    }

    public Long getTokenUserId(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String token = JwtUtil.getTokenFromCookies(cookies);
        return JwtUtil.getTokenUserId(token);
    }

    public Boolean renewToken(String token) {
        Object expireTime = getTokenPayload(token, "expire_time");
        return false;
    }
}
