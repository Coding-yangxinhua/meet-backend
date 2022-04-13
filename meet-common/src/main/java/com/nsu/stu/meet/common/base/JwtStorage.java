package com.nsu.stu.meet.common.base;

import lombok.experimental.UtilityClass;


@UtilityClass
public class JwtStorage {

    public static ThreadLocal<JwtInfo> jwtInfo = new InheritableThreadLocal<>();

    public void set(Long userId, String userName) {
        JwtInfo jwtInfo = new JwtInfo(userId, userName);
        set(jwtInfo);
    }
    public void set(JwtInfo jwtInfo) {
        JwtStorage.jwtInfo.set(jwtInfo);
    }

    public JwtInfo info() {
        return JwtStorage.jwtInfo.get();
    }

    public Long userId () {
        if (JwtStorage.jwtInfo.get() != null) {
            return JwtStorage.jwtInfo.get().getUserId();
        }
        return null;
    }

    public void remove() {
        jwtInfo.remove();
    }
}
