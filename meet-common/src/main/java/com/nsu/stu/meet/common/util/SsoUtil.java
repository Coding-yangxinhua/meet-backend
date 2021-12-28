package com.nsu.stu.meet.common.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SsoUtil {

    public boolean isLogin(String token) {
        return JwtUtil.isValidToken(token);
    }
    public String login(Long id) {
        return JwtUtil.createToken(id);
    }

    public Long getUserId(String token) {
        return JwtUtil.getTokenUserId(token);
    }


}
