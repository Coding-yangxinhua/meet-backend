package com.nsu.stu.meet.common.base;

import lombok.experimental.UtilityClass;


/**
 * @author Xinhua X Yang
 */
@UtilityClass
public class JwtStorage {

    public static final ThreadLocal<JwtInfo> jwtInfo = new InheritableThreadLocal<>();


    public void set(Long userId, String userName) {
        JwtInfo jwtInfoParam = new JwtInfo(userId, userName);
        set(jwtInfoParam);
    }

    public void set(boolean isSelf) {
        JwtStorage.jwtInfo.get().setSelf(isSelf);
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

    public boolean isSelf () {
        if (JwtStorage.jwtInfo.get() != null) {
            return JwtStorage.jwtInfo.get().isSelf();
        }
        return false;
    }

    public void remove() {
        jwtInfo.remove();
    }
}
