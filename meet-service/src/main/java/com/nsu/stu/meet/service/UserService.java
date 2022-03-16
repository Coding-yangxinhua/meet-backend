package com.nsu.stu.meet.service;


import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.common.enums.ResultStatus;
import com.nsu.stu.meet.model.dto.UserDto;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

public interface UserService {

    /**
     * 通过短信注册
     * @param userDto
     * @param code
     * @param type
     * @return
     */
    ResponseEntity<String> registerByCode (UserDto userDto, String code, int type, HttpServletResponse response);

    /**
     * 通过密码注册
     * @param userDto
     * @return
     */
    ResponseEntity<String> registerByPassword (UserDto userDto, HttpServletResponse response);

    /**
     * 通过短信登录
     * @param userDto
     * @param code
     * @param type
     * @return
     */
    ResponseEntity<String> loginByCode (UserDto userDto, String code, int type, HttpServletResponse response);

    /**
     * 通过密码登录
     * @param userDto
     * @return
     */
    ResponseEntity<String> loginByPassword (UserDto userDto, HttpServletResponse response);

    /**
     * 通过短信修改密码
     * @param userId
     * @param password
     * @param code
     * @return
     */
    ResponseEntity<String> updatePasswordByCode(Long userId, String password, String code);

    /**
     * 修改用户简介
     * @param userDto
     * @param userId
     * @return
     */
    ResponseEntity<String> updateUserProfile(Long userId, UserDto userDto);

    ResponseEntity<String> updateUserAvatar(Long userId, MultipartFile file);

}
