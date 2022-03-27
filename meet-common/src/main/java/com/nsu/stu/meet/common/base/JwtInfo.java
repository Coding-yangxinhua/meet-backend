package com.nsu.stu.meet.common.base;

import com.nsu.stu.meet.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Getter
@Setter
public class JwtInfo {



    Long userId;

    String userName;

    public JwtInfo(Long userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

}
