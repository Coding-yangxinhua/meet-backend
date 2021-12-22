package com.nsu.stu.meet.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nsu.stu.meet.dao.mapper.UserBaseMapper;
import com.nsu.stu.meet.model.User;
import com.nsu.stu.meet.model.UserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends UserBaseMapper {

    default void customizeQuery(UserDto condition, QueryWrapper<User> query) {

    }
}
