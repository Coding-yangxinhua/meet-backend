package com.nsu.stu.meet.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.model.User;
import com.nsu.stu.meet.model.dto.UserBaseDto;
import com.nsu.stu.meet.model.dto.UserDto;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

public interface UserService extends CheckService{

    /**
     * 通过短信注册
     * @param userDto
     * @param code
     * @param type
     * @return
     */
    ResponseEntity<String> registerByCode (UserDto userDto, String code, int type);

    /**
     * 通过密码注册
     * @param userDto
     * @return
     */
    ResponseEntity<String> registerByPassword (UserDto userDto);

    /**
     * 通过短信登录
     * @param userDto
     * @param code
     * @param type
     * @return
     */
    ResponseEntity<UserBaseDto> loginByCode (UserDto userDto, String code, int type, HttpServletResponse response);

    /**
     * 通过密码登录
     * @param userDto
     * @return
     */
    ResponseEntity<UserBaseDto> loginByPassword (UserDto userDto, HttpServletResponse response);

    /**
     * 通过短信修改密码
     * @param password
     * @param code
     * @return
     */
    ResponseEntity<String> updatePasswordByCode(String password, String code);

    /**
     * 修改用户简介
     * @param userDto
     * @return
     */
    ResponseEntity<String> updateUserProfile(UserDto userDto);

    ResponseEntity<String> updateUserAvatar(MultipartFile file);

    ResponseEntity<User> getInfo(Long queryUserId);

    ResponseEntity<User> getSelfInfo();

    /**
     * 关注用户的用户
     * @param userId
     * @return
     */
    ResponseEntity<IPage<UserDto>> getFollowedUser(Long userId, int page, int size);

    /**
     * 用户关注的用户
     * @param userId
     * @return
     */
    ResponseEntity<IPage<UserDto>> getUserFollow(Long userId, int page, int size);

    boolean checkExists(Long userId);

}
