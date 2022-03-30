package com.nsu.stu.meet.service;


import com.nsu.stu.meet.model.UserRelation;
import com.nsu.stu.meet.model.vo.LimitVo;

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
    List<Long> getUserFollow (Long userId);


    /**
     * 关注用户的用户
     * @param userId
     * @return
     */
    List<Long> getFollowedUser (Long userId);


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

    boolean isBlockedEach(Long userId, Long queryId);

}
