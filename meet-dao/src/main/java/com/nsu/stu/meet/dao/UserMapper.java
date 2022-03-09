package com.nsu.stu.meet.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nsu.stu.meet.model.User;
import org.apache.ibatis.annotations.Param;

import javax.validation.constraints.NotNull;


public interface UserMapper extends BaseMapper<User> {

    /**
     * 通过手机号获取用户信息
     * @param mobile
     * @return
     */
    User getUserByMobile (@Param("mobile") String mobile);

    /**
     * 通过手机号修改账号密码
      * @param userId
     * @param password
     * @return
     */
    User updateUserPassword (@Param("userId") Long userId, @Param("password") String password);

}
