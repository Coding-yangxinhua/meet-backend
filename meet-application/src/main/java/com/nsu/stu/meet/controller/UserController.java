package com.nsu.stu.meet.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.common.enums.ResultStatus;
import com.nsu.stu.meet.model.User;
import com.nsu.stu.meet.model.UserDto;
import com.nsu.stu.meet.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping(value = "getAllUser", method = RequestMethod.POST)
    public ResponseEntity<List<UserDto>> getAllUser(@RequestBody UserDto condition) {
        List<UserDto> byConditionDto = userService.findByConditionDto(condition);
        return ResponseEntity.ok(byConditionDto);
    }
    @RequestMapping(value = "getUserList", method = RequestMethod.POST, params = {"currentPage", "pageSize"})
    public ResponseEntity<IPage<UserDto>> getUserList(@RequestBody UserDto condition, Integer currentPage, Integer pageSize) {
        IPage<UserDto> pageDto = userService.findPageDto(condition, currentPage, pageSize);
        return ResponseEntity.ok(pageDto);
    }
    @RequestMapping(value = "saveUser", method = RequestMethod.POST)
    public ResponseEntity<List<ResultStatus>> saveUser(@RequestBody User user) {
        userService.saveUpdate(user);
        return ResponseEntity.ok();
    }

    @RequestMapping(value = "findUser", method = RequestMethod.GET, params = {"userId"})
    public ResponseEntity<User> findUser(Long userId) {
        return ResponseEntity.ok(userService.getById(userId));
    }

}
