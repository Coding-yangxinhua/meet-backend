package com.nsu.stu.meet.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nsu.stu.meet.model.User;
import com.nsu.stu.meet.model.dto.user.FriendBaseDto;
import com.nsu.stu.meet.model.dto.user.UserBaseDto;
import com.nsu.stu.meet.model.dto.user.UserDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface UserMapper extends BaseMapper<User> {
    default User getUserByMobile (String mobile) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getMobile, mobile);
        return selectOne(queryWrapper);
    }

    UserBaseDto selectBaseFromUser(Long userId);

    List<FriendBaseDto> getFriendBaseByIds (@Param("queryIds") List<Long> queryIds, @Param("start") int start, @Param("end") int end);

}
