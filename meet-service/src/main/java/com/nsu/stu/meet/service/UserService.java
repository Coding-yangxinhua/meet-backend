package com.nsu.stu.meet.service;


import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.model.Article;
import com.nsu.stu.meet.model.User;
import com.nsu.stu.meet.model.dto.UserDto;
import org.springframework.web.multipart.MultipartFile;

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
    ResponseEntity<String> loginByCode (UserDto userDto, String code, int type);

    /**
     * 通过密码登录
     * @param userDto
     * @return
     */
    ResponseEntity<String> loginByPassword (UserDto userDto);

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

}
