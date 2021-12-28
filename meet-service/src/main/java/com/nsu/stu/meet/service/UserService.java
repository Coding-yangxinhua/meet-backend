package com.nsu.stu.meet.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nsu.stu.meet.model.User;
import com.nsu.stu.meet.model.UserDto;

import java.util.Collection;
import java.util.List;

public interface UserService {
    /**
     * 通过id获取
     * @param userId
     * @return
     */
    User getById(Long userId);

    /**
     * 通过id删除
     * @return
     */
    int deleteById(Long userId);

    /**
     * 批量查询
     * @param ids
     * @return
     */
    List<User> getByIds(Collection<Long> ids);
    /**
     * 主键字段为空则插入,否则更新
     * @param user
     * @return
     */
    boolean saveUpdate(User user);

    /**
     * 批量更新,主键字段为空则插入,否则更新
     * @param users
     * @return
     */
    boolean saveUpdateBatch(List<User> users);

    /**
     * 批量删除,可通过配置logic-delete-field等来实现逻辑删除,见application.yml配置.
     * @param ids
     * @return
     */
    boolean removeBatch(Collection<Long> ids);

    /**
     * 分页查询
     * @param condition 传入的参数
     * @param currentPage 当前页
     * @param pageSize 每页条数
     * @return IPage<UserDto>
     */

    IPage<UserDto> findPageDto(UserDto condition, int currentPage, int pageSize);

    /**
     * 分页查询
     * @param condition 传入的参数
     * @return  List<UserDto>
     */
    List<UserDto> findByConditionDto(UserDto condition);

    /**
     * 查询最新注册用户
     * @return  ApplicationDto
     */
    User findNewestUser();

}
