package com.nsu.stu.meet.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nsu.stu.meet.annotation.Limit;
import com.nsu.stu.meet.common.base.JwtStorage;
import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.common.enums.ArticleTypeEnums;
import com.nsu.stu.meet.common.enums.FriendEnums;
import com.nsu.stu.meet.common.enums.ResultStatus;
import com.nsu.stu.meet.common.enums.SmsEnums;
import com.nsu.stu.meet.model.User;
import com.nsu.stu.meet.model.dto.ArticleDto;
import com.nsu.stu.meet.model.dto.user.FriendBaseDto;
import com.nsu.stu.meet.model.dto.user.UserBaseDto;
import com.nsu.stu.meet.model.dto.user.UserDto;
import com.nsu.stu.meet.service.SmsService;
import com.nsu.stu.meet.service.UserService;
import com.nsu.stu.meet.service.impl.UserServiceImpl;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
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
            responseEntity = userService.registerByCode(userDto, code, SmsEnums.REGISTER.type(), response);
        }else {
            responseEntity = userService.registerByPassword(userDto, response);
        }
        if (responseEntity.getStatus().equals(ResultStatus.OK)) {

        }
        return responseEntity;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<UserBaseDto> login(@RequestBody UserDto userDto, @RequestParam(required = false) String code, HttpServletResponse response) {
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

    @RequestMapping(value = "/updateBackground", method = RequestMethod.POST)
    public ResponseEntity<String> updateBackground(@RequestPart("file") MultipartFile file) {
        return userService.updateUserBackground(file);
    }

    @RequestMapping(value = "/getInfo", method = RequestMethod.GET)
    public ResponseEntity<User> getInfo(@RequestParam(value = "userId", required = false) Long userId) {
        if (userId == null) {
            Long tokenUserId = JwtStorage.userId();
            return userService.getInfo(tokenUserId);
        } else {
            return userService.getInfo(userId);
        }
    }


    @RequestMapping(value = "/getUserBase", method = RequestMethod.GET)
    public ResponseEntity<UserBaseDto> getUserBase(@RequestParam(value = "userId", required = false) Long userId) {
        if (userId == null) {
            userId = JwtStorage.userId();
        }
        return userService.getUserBase(userId);
    }

    @Limit(clazz = UserServiceImpl.class, half = true)
    @RequestMapping(value = "/getFriend", method = RequestMethod.GET, params = {"type", "page", "size"})
    @Transactional
    public ResponseEntity<IPage<FriendBaseDto>> getFriend(@RequestParam(value = "queryId", required = false) Long queryId, Integer type, Integer page, Integer size) {
        FriendEnums friendEnums = FriendEnums.lookUp(type);
        Long userId = JwtStorage.userId();
        if (queryId == null) {
            queryId = userId;
        }
        switch (friendEnums) {
            case FOLLOW:
                return userService.getUserFollow(userId, queryId, page, size);
            case FAN:
                return userService.getFollowedUser(userId, queryId, page, size);
            default:
                return userService.getUserFollow(userId, queryId, page, size);
        }
    }







}
