package com.nsu.stu.meet.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.model.UserRelation;
import com.nsu.stu.meet.model.dto.UserRelationDto;
import com.nsu.stu.meet.model.dto.user.FriendBaseDto;
import com.nsu.stu.meet.model.dto.user.UserDto;

import java.util.List;

public interface UserRelationService {

    /**
     * 用户与用户之间关系
     * @param srcId
     * @param destId
     * @return
     */
    UserRelation getUserRelation (Long srcId, Long destId);

    /**
     * 用户关注的用户id
     * @param userId
     * @return
     */
    List<Long> getUserFollowIds (Long userId);


    /**
     * 关注用户的用户Id
     * @param userId
     * @return
     */
    List<Long> getFollowedUserIds (Long userId);

    /**
     * 互关id
     * @param userId
     * @return
     */
    List<Long> getFollowedEach(Long userId);


    /**
     * 用户拉黑的用户id
     * @param userId
     * @return
     */
    List<Long> getUserBlock (Long userId);

    /**
     * 拉黑用户的用户
     * @param userId
     * @return
     */
    List<Long> getBlockedUser (Long userId);

    /**
     * 获得所有双向拉黑用户
     * @param userId
     * @return
     */
    List<Long> getBlockedEach(Long userId);

    /**
     * 是否双向拉黑
     * @param userId
     * @param queryId
     * @return
     */
    boolean isBlockedEach(Long userId, Long queryId);

    /**
     * 改变用户关系
     */
    ResponseEntity<String> changeStatus(UserRelationDto userRelationDto);

}
