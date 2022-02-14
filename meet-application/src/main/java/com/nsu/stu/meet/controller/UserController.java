package com.nsu.stu.meet.controller;

import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.common.enums.ResultStatus;
import com.nsu.stu.meet.common.enums.SmsEnums;
import com.nsu.stu.meet.common.util.AssertUtil;
import com.nsu.stu.meet.common.util.JwtUtil;
import com.nsu.stu.meet.common.util.MD5Util;
import com.nsu.stu.meet.model.User;
import com.nsu.stu.meet.model.UserDto;
import com.nsu.stu.meet.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @RequestMapping(value = "get", method = RequestMethod.GET, params = {"userId"})
    public ResponseEntity<User> getUserById(Long userId) {
        User user = userService.getById(userId);
        return ResponseEntity.ok(user);
    }

    @RequestMapping(value = "sendSms", method = RequestMethod.GET, params = {"mobile"})
    public ResponseEntity<ResultStatus> sendSms(String mobile) {
        return ResponseEntity.ok();
    }

    @RequestMapping(value = "register", method = RequestMethod.POST, params = {"code"})
    public ResponseEntity<String> register(@RequestBody UserDto userDto, Integer code) {
        String mobile = userDto.getMobile();
        redisTemplate.opsForValue().get(mobile);
        AssertUtil.hasText(mobile, "手机号不能为空");
        return userService.registerUser(userDto);
    }



}
