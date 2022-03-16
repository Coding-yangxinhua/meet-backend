package com.nsu.stu.meet.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nsu.stu.meet.model.User;
import org.apache.ibatis.annotations.Param;

import javax.validation.constraints.NotNull;


public interface UserMapper extends BaseMapper<User> {
    default User getUserByMobile (String mobile) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile", mobile);
        return selectOne(queryWrapper);
    }
}
