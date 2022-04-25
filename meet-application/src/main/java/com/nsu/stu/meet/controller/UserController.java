package com.nsu.stu.meet.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nsu.stu.meet.common.base.JwtStorage;
import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.common.config.SsoConfig;
import com.nsu.stu.meet.common.constant.SystemConstants;
import com.nsu.stu.meet.common.enums.ResultStatus;
import com.nsu.stu.meet.common.enums.SmsEnums;
import com.nsu.stu.meet.model.User;
import com.nsu.stu.meet.model.dto.UserBaseDto;
import com.nsu.stu.meet.model.dto.UserDto;
import com.nsu.stu.meet.service.SmsService;
import com.nsu.stu.meet.service.UserService;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nullable;
import javax.servlet.http.Cookie;
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

        }
        return responseEntity;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, params = {"code"})
    public ResponseEntity<UserBaseDto> login(@RequestBody UserDto userDto, @Nullable String code, HttpServletResponse response) {
        ResponseEntity<UserBaseDto> responseEntity = null;
        if (code != null) {
            responseEntity = userService.loginByCode(userDto, code, SmsEnums.LOGIN.type(), response);
        }else {
            responseEntity = userService.loginByPassword(userDto, response);
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

    @RequestMapping(value = "/getUserFollow", method = RequestMethod.GET, params = {"page", "size"})
    public ResponseEntity<IPage<UserDto>> getUserFollow(int page, int size) {
        Long userId = JwtStorage.userId();
        return userService.getUserFollow(userId, page, size);
    }

    @RequestMapping(value = "/getFollowedUser", method = RequestMethod.GET, params = {"page", "size"})
    public ResponseEntity<IPage<UserDto>> getFollowedUser(int page, int size) {
        Long userId = JwtStorage.userId();
        return userService.getFollowedUser(userId, page, size);
    }








}
