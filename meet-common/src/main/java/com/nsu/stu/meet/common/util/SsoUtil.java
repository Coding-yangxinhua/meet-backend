package com.nsu.stu.meet.common.util;

import com.nsu.stu.meet.common.base.JwtInfo;
import com.nsu.stu.meet.common.constant.SystemConstants;
import lombok.experimental.UtilityClass;

import javax.servlet.http.Cookie;

@UtilityClass
public class SsoUtil {

    public boolean isLogin(String token) {
        return JwtUtil.isValidToken(token);
    }
    public String login(Long userId, String userName) {
        return JwtUtil.createToken(userId, userName);
    }
    public String login(Long userId) {
        return JwtUtil.createToken(userId, null);
    }

    public JwtInfo getJwtInfo(String token) {
        Long userId = (Long) JwtUtil.getTokenPayload(token, "userId");
        String userName = (String) JwtUtil.getTokenPayload(token, "userName");
        return new JwtInfo(userId, userName);
    }


    public Object getCookieValue(String key, Cookie[] cookies) {
        Object value = null;
        for (Cookie cookie:
                cookies) {
            if (key.equals(cookie.getName())) {
                value = cookie.getValue();
            }
        }
        return value;
    }

    public String getToken(Cookie[] cookies) {
        return (String) getCookieValue(SystemConstants.TOKEN_NAME, cookies);
    }



}
