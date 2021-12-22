package com.nsu.stu.meet.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nsu.stu.meet.dao.UserMapper;
import com.nsu.stu.meet.model.User;
import com.nsu.stu.meet.model.UserDto;
import com.nsu.stu.meet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;
import java.util.Collection;
import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;


    @Override
    public User getById(Long userId) {
        return userMapper.selectById(userId);
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
}
