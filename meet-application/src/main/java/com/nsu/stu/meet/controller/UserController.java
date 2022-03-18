package com.nsu.stu.meet.controller;

import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.common.config.SsoConfig;
import com.nsu.stu.meet.common.constant.SystemConstants;
import com.nsu.stu.meet.common.enums.ResultStatus;
import com.nsu.stu.meet.common.enums.SmsEnums;
import com.nsu.stu.meet.common.util.JwtUtil;
import com.nsu.stu.meet.model.User;
import com.nsu.stu.meet.model.dto.UserDto;
import com.nsu.stu.meet.service.SmsService;
import com.nsu.stu.meet.service.UserService;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nullable;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private SmsService smsService;

    @Autowired
    private SsoConfig ssoConfig;


    @RequestMapping(value = "/sendSms", method = RequestMethod.GET, params = {"mobile", "type"})
    public ResponseEntity<SendSmsResponse> sendSms(String mobile, int type) {
        return smsService.sendSms(mobile, type);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST, params = {"code"})
    public ResponseEntity<String> register(@RequestBody UserDto userDto, @Nullable String code, HttpServletResponse response) {
        ResponseEntity<String> responseEntity = null;
        if (code != null) {
            responseEntity = userService.registerByCode(userDto, code, SmsEnums.REGISTER.type());
        }else {
            responseEntity = userService.registerByPassword(userDto);
        }
        if (responseEntity.getStatus().equals(ResultStatus.OK)) {
            setTokenToCookies (responseEntity.getResult(), response);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, params = {"code"})
    public ResponseEntity<String> login(@RequestBody UserDto userDto, @Nullable String code, HttpServletResponse response) {
        ResponseEntity<String> responseEntity = null;
        if (code != null) {
            responseEntity = userService.loginByCode(userDto, code, SmsEnums.LOGIN.type());
        }else {
            responseEntity = userService.loginByPassword(userDto);
        }
        if (responseEntity.getStatus().equals(ResultStatus.OK)) {
            setTokenToCookies (responseEntity.getResult(), response);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST, params = {"password", "code"})
    public ResponseEntity<String> updatePassword(String password, String code) {
        return userService.updatePasswordByCode(password, code);
    }

    @RequestMapping(value = "/updateProfile", method = RequestMethod.POST)
    public ResponseEntity<String> updateProfile(@RequestBody UserDto userDto) {
        return userService.updateUserProfile(userDto);
    }

    @RequestMapping(value = "/updateAvatar", method = RequestMethod.POST)
    public ResponseEntity<String> updateAvatar(@RequestPart("file") MultipartFile file) {
        return userService.updateUserAvatar(file);
    }

    @RequestMapping(value = "/getInfo", method = RequestMethod.GET, params = {"userId"})
    public ResponseEntity<User> getInfo(@Nullable Long userId) {
        if (userId == null) {
            return userService.getSelfInfo();
        } else {
            return userService.getInfo(userId);
        }
    }

    private void setTokenToCookies(@NotNull String token, @NotNull HttpServletResponse response) {
        Cookie cookie = new Cookie(SystemConstants.TOKEN_NAME, "utf-8");
        cookie.setDomain(ssoConfig.getCookie().getHost());
        cookie.setPath(ssoConfig.getCookie().getPath());
        cookie.setValue(token);
        response.addCookie(cookie);
    }




}
