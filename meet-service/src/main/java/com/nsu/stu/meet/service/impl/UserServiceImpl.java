package com.nsu.stu.meet.service.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nsu.stu.meet.dao.UserMapper;
import com.nsu.stu.meet.model.User;
import com.nsu.stu.meet.model.UserDto;
import com.nsu.stu.meet.service.UserService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

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
    public User findNewestUser() {
        return userMapper.getNewestUser();
    }
}
