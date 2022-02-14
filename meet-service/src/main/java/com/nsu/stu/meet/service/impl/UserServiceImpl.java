package com.nsu.stu.meet.service.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.common.enums.ResultStatus;
import com.nsu.stu.meet.common.util.JwtUtil;
import com.nsu.stu.meet.common.util.MD5Util;
import com.nsu.stu.meet.dao.UserMapper;
import com.nsu.stu.meet.model.User;
import com.nsu.stu.meet.model.UserDto;
import com.nsu.stu.meet.service.UserService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    @Cacheable(value = "user", key = "#userId")
    public User getById(Long userId) {
        return userMapper.selectById(userId);
    }

    @Override
    @CacheEvict(value = "user", key = "#userId")
    public int deleteById(Long userId) {
        return userMapper.deleteById(userId);
    }


    @Override
    public List<User> getByIds(Collection<Long> ids) {
        return userMapper.selectBatchIds(ids);
    }

    @Override
    public boolean saveUpdate(User user) {
        return super.saveOrUpdate(user);
    }

    @Override
    public boolean saveUpdateBatch(List<User> users) {
        return super.saveOrUpdateBatch(users);
    }

    @Override
    public boolean removeBatch(Collection<Long> ids) {
        return super.removeByIds(ids);
    }

    @Override
    public IPage<UserDto> findPageDto(UserDto condition, int currentPage, int pageSize) {
        return userMapper.findPageDto(condition, currentPage, pageSize);
    }

    @Override
    public List<UserDto> findByConditionDto(UserDto condition) {
        return userMapper.findByConditionDto(condition);
    }

    @Override
    public ResponseEntity<String> registerUser(UserDto userDto) {
        // 提取出密码
        String password = userDto.getPassword();
        // 根据手机号查询账号是否存在
        List<UserDto> userDtoList = this.findByConditionDto(userDto);
        // 构造响应体
        ResponseEntity<String> build = ResponseEntity.builder().build();
        // 账号已存在
        if (userDtoList.size() != 0) {
            build.setStatus(ResultStatus.CHECK_ERROR);
            build.setMessage("账号已存在");
            return build;
        }
        // 根据md5加密密码
        if (StringUtils.hasText(password)) {
            String md5Password = MD5Util.md5Password(password);
            userDto.setPassword(md5Password);
        }
        // 存入数据库
        boolean b = this.saveUpdate(userDto);
        // 失败提示
        if (!b) {
            build.setStatus(ResultStatus.SYS_ERROR);
            return build;
        }
        // 注册成功
        // 生成token
        String token = JwtUtil.createToken(userDto.getUserId());
        build.setMessage("注册成功");
        build.setStatus(ResultStatus.OK);
        build.setResult(token);
        return build;
    }


}
