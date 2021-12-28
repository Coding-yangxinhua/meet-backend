package com.nsu.stu.meet.controller;

import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.common.enums.ResultStatus;
import com.nsu.stu.meet.model.User;
import com.nsu.stu.meet.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "get", method = RequestMethod.GET, params = {"userId"})
    public ResponseEntity<User> getUserById(Long userId) {
        User user = userService.getById(userId);
        return ResponseEntity.ok(user);
    }

    @RequestMapping(value = "delete", method = RequestMethod.GET, params = {"userId"})
    public ResponseEntity<ResultStatus> deleteUserById(Long userId) {
        int i = userService.deleteById(userId);
        if (i == 1) {
            return ResponseEntity.ok();
        }
        return ResponseEntity.builder().status(ResultStatus.CHECK_ERROR).message("查无此用户").build();
    }



}
