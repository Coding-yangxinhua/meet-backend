package com.nsu.stu.meet.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nsu.stu.meet.model.User;
import org.apache.ibatis.annotations.Param;

import javax.validation.constraints.NotNull;


public interface UserMapper extends BaseMapper<User> {
    default User getUserByMobile (String mobile) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getMobile, mobile);
        return selectOne(queryWrapper);
    }


}
