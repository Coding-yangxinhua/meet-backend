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

    public String createToken(Long userId, String userName) {
        DateTime date = DateUtil.date();
        return JWT.create()
                .setPayload("userId", userId)
                .setPayload("userName", userName)
                .setKey(key)
                .setExpiresAt(DateUtil.offsetDay(date, 7))
                .sign();
    }
    public String createToken(Long userId) {
        return createToken(userId, null);
    }
    public Object getTokenPayload(String token, String name) {
        boolean validToken = isValidToken(token);
        if (validToken) {
            JWT jwt = JWTUtil.parseToken(token);
            return jwt.getPayload(name);
        }
        return null;
    }

    public boolean isValidToken(String token) {
        boolean verify = false;
        if (StringUtils.hasText(token)) {

            try {
                verify = JWTUtil.verify(token, key);
            } catch (RuntimeException e) {
                throw new IllegalParamException(SystemConstants.TOKEN_ERROR);
            }
        }
        return verify;
    }

    public Boolean renewToken(String token) {
        Object expireTime = getTokenPayload(token, "expire_time");
        return false;
    }
}
