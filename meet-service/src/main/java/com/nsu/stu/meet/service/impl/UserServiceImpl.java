package com.nsu.stu.meet.service.impl;


import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.common.constant.SystemConstants;
import com.nsu.stu.meet.common.enums.ResultStatus;
import com.nsu.stu.meet.common.enums.SmsEnums;
import com.nsu.stu.meet.common.util.*;
import com.nsu.stu.meet.dao.UserMapper;
import com.nsu.stu.meet.model.User;
import com.nsu.stu.meet.model.dto.UserDto;
import com.nsu.stu.meet.service.SmsService;
import com.nsu.stu.meet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nullable;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private SmsService smsService;
    @Autowired
    private CosUtil cosUtil;

    @Value("${system.cookie.host}")
    private String host;
    @Value("${system.cookie.path}")
    private String path;

    @Override
    public ResponseEntity<String> registerByPassword(UserDto userDto, HttpServletResponse response) {
        // 提取出密码
        String password = userDto.getPassword();
        String mobile = userDto.getMobile();
        // 检测手机号格式
        if (ValidateUtil.isMobile(mobile)) {
            return ResponseEntity.checkError(SystemConstants.MOBILE_ERROR);
        }
        // 密码校验
        if (ValidateUtil.isPassword(password)) {
            return ResponseEntity.checkError(SystemConstants.PASSWORD_LENGTH_ERROR);
        }
        // 根据手机号查询账号是否存在
        User user = baseMapper.getUserByMobile(userDto.getMobile());
        // 账号已存在
        if (user != null) {
            return ResponseEntity.checkError(SystemConstants.USER_EXIST);
        }
        // 根据md5加密密码
        if (StringUtils.hasText(password)) {
            String md5Password = MD5Util.md5Password(password);
            userDto.setPassword(md5Password);
        }
        // 存入数据库
        // 设置默认用户名
        userDto.setNickname("用户" + RandomUtil.randomNumbers(11));
        baseMapper.insert(userDto);
        setTokenToCookies(userDto.getUserId(), response);
        // 注册成功
        // 生成token
        return ResponseEntity.ok(SystemConstants.REGISTER_SUCCESS);
    }

    @Override
    public ResponseEntity<String> registerByCode(UserDto userDto, String code, int type, HttpServletResponse response) {
        // 获取手机号
        String mobile = userDto.getMobile();
        // 检测手机号格式
        if (!ValidateUtil.isMobile(mobile)) {
            return ResponseEntity.checkError(SystemConstants.MOBILE_ERROR);
        }
        // 根据手机号查询账号是否存在
        User user = baseMapper.getUserByMobile(userDto.getMobile());
        // 账号已存在
        if (user != null) {
            return ResponseEntity.checkError(SystemConstants.USER_EXIST);
        }
        // 存入数据库
        Boolean isCodeRight = smsService.checkCode(userDto.getMobile(), code, type);
        if (isCodeRight) {
            // 设置默认用户名
            userDto.setNickname("用户" + RandomUtil.randomNumbers(11));
            // 屏蔽基础信息
            this.setBaseNull(userDto);
            baseMapper.insert(userDto);
            setTokenToCookies(userDto.getUserId(), response);
            return ResponseEntity.ok(SystemConstants.REGISTER_SUCCESS);
        }
        return ResponseEntity.checkError(SystemConstants.CODE_ERROR);
    }

    @Override
    public ResponseEntity<String> loginByCode(UserDto userDto, String code, int type, HttpServletResponse response) {
        // 获取手机号
        String mobile = userDto.getMobile();
        // 检测手机号格式
        if (!ValidateUtil.isMobile(mobile)) {
            return ResponseEntity.checkError(SystemConstants.MOBILE_ERROR);
        }
        // 检测用户是否存在
        User user = baseMapper.getUserByMobile(userDto.getMobile());
        if (user == null) {
            return ResponseEntity.checkError(SystemConstants.USER_NOT_EXIST);
        }
        // 对比验证码是否正确
        Boolean isCodeRight = smsService.checkCode(userDto.getMobile(), code, type);
        if (isCodeRight) {
            setTokenToCookies(user.getUserId(), response);
            return ResponseEntity.ok(SystemConstants.LOGIN_SUCCESS);
        }
        return ResponseEntity.checkError(SystemConstants.CODE_ERROR);
    }

    @Override
    public ResponseEntity<String> loginByPassword(UserDto userDto, HttpServletResponse response) {
        // 获取手机号
        String mobile = userDto.getMobile();
        // 提取出密码
        String password = userDto.getPassword();
        // 检测手机号格式
        if (!ValidateUtil.isMobile(mobile)) {
            return ResponseEntity.checkError(SystemConstants.MOBILE_ERROR);
        }
        // 密码校验
        if (ValidateUtil.isPassword(password)) {
            return ResponseEntity.checkError(SystemConstants.PASSWORD_LENGTH_ERROR);
        }
        // 根据md5加密密码
        String md5Password = MD5Util.md5Password(password);
        // 根据手机号查询账号是否存在
        User user = baseMapper.getUserByMobile(userDto.getMobile());
        if (user == null) {
            return ResponseEntity.checkError(SystemConstants.USER_NOT_EXIST);
        }
        // 密码正确
        if (md5Password.equals(userDto.getPassword())) {
            setTokenToCookies(user.getUserId(), response);
            return ResponseEntity.ok(SystemConstants.REGISTER_SUCCESS);
        }
        return ResponseEntity.checkError(SystemConstants.PASSWORD_ERROR);


    }

    @Override
    public ResponseEntity<String> updatePasswordByCode(Long userId, String password, String code) {
        // 密码校验
        if (ValidateUtil.isPassword(password)) {
            return ResponseEntity.checkError(SystemConstants.PASSWORD_LENGTH_ERROR);
        }
        // 与原密码比对
        User user = baseMapper.selectById(userId);
        if (user == null || password.equals(user.getPassword())) {
            return ResponseEntity.checkError(SystemConstants.PASSWORD_SAME_ERROR);
        }
        // 查询验证码是否正确
        Boolean isCodeRight  = smsService.checkCode(String.valueOf(userId), code, SmsEnums.UPDATE.type());
        if (isCodeRight) {
            UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("user_id", userId);
            User newUser = new User();
            newUser.setPassword(password);
            baseMapper.update(newUser, updateWrapper);
            return ResponseEntity.ok(SystemConstants.UPDATE_PASSWORD_SUCCESS);
        }
        return ResponseEntity.checkError(SystemConstants.CODE_ERROR);
    }

    @Override
    public ResponseEntity<String> updateUserProfile(Long userId, UserDto userDto) {
        // 将重要信息屏蔽
        this.setBaseNull(userId, userDto);
        baseMapper.updateById(userDto);
        return ResponseEntity.ok(SystemConstants.UPDATE_PROFILE_SUCCESS);
    }

    @Override
    public ResponseEntity<String> updateUserAvatar(Long userId, MultipartFile file) {
        User user = new User();
        user.setUserId(userId);
        boolean isImage = OwnUtil.checkFileIsImage(file.getResource().getFilename());
        long size = file.getSize();
        if (!isImage) {
            return ResponseEntity.checkError(SystemConstants.FILE_TYPE_ERROR);
        }
        if (size > 5 * 1024 * 1000) {
            return ResponseEntity.checkError(SystemConstants.FILE_SIZE_ERROR);
        }
        String url = cosUtil.upload(file);
        user.setAvatar(url);
        baseMapper.updateById(user);
        return ResponseEntity.ok();
    }

    private void setBaseNull(UserDto userDto) {
        // 屏蔽基础信息
        setBaseNull(null, userDto);
    }
    private void setBaseNull(Long userId, UserDto userDto) {
        // 屏蔽基础信息
        userDto.setUserId(userId);
        userDto.setAvatar(null);
        userDto.setPassword(null);
        userDto.setMobile(null);
        userDto.setIsDeleted(null);
    }
    private void setTokenToCookies(@NotNull Long userId, @NotNull HttpServletResponse response) {
        String token = JwtUtil.createToken(userId);
        Cookie cookie = new Cookie(SystemConstants.TOKEN_NAME, "utf-8");
        cookie.setDomain(host);
        cookie.setPath(path);
        cookie.setValue(token);
        response.addCookie(cookie);
    }
}
