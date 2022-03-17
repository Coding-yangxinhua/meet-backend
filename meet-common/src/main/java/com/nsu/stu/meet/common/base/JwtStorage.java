package com.nsu.stu.meet.common.base;

import lombok.experimental.UtilityClass;


@UtilityClass
public class JwtStorage {

    public static ThreadLocal<JwtInfo> jwtInfo = new InheritableThreadLocal<>();

    public void set(Long userId, String userName) {
        jwtInfo.set(new JwtInfo(userId, userName));
    }
    public void set(JwtInfo jwtInfo) {
        JwtStorage.jwtInfo.set(jwtInfo);
    }

    public JwtInfo info() {
        return JwtStorage.jwtInfo.get();
    }

    public void remove() {
        jwtInfo.remove();
    }
}
