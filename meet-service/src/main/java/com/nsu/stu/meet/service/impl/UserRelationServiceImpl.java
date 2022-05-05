package com.nsu.stu.meet.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.common.constant.SystemConstants;
import com.nsu.stu.meet.common.enums.MybatisEnums;
import com.nsu.stu.meet.common.util.OwnUtil;
import com.nsu.stu.meet.dao.UserRelationMapper;
import com.nsu.stu.meet.model.UserRelation;
import com.nsu.stu.meet.model.dto.UserRelationDto;
import com.nsu.stu.meet.model.dto.user.FriendBaseDto;
import com.nsu.stu.meet.model.dto.user.UserDto;
import com.nsu.stu.meet.model.enums.RelationEnums;
import com.nsu.stu.meet.service.UserRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class UserRelationServiceImpl extends ServiceImpl<UserRelationMapper, UserRelation> implements UserRelationService {
    @Autowired
    private StringRedisTemplate redisTemplate;

    private final String key = "user_relation";

    @Override
    public UserRelation getUserRelation(Long srcId, Long destId) {
        // 缓存
        String key = OwnUtil.getRedisKey(this.key, srcId, destId);
        String userRelationKey = redisTemplate.opsForValue().get(key);
        if (userRelationKey != null) {
            return JSON.parseObject(userRelationKey, UserRelation.class);
        }
        // 数据库
        LambdaQueryWrapper<UserRelation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRelation::getSrcId, srcId).eq(UserRelation::getDestId, destId);
        UserRelation userRelation = baseMapper.selectOne(queryWrapper);
        if (userRelation == null) {
            userRelation = new UserRelation();
            userRelation.setSrcId(srcId);
            userRelation.setDestId(destId);
            userRelation.setStatus(RelationEnums.NORMAL);
        }
        // 存入缓存
        redisTemplate.opsForValue().set(key, JSON.toJSONString(userRelation));
        return userRelation;
    }

    @Override
    public List<Long> getUserFollowIds(Long userId) {
        // 缓存
        String suffix = "follow";
        String userFollowKey = OwnUtil.getRedisKey(this.key, suffix, userId);
        return this.getIdsByFunction(userId, userFollowKey, RelationEnums.FOLLOW, UserRelation::getSrcId, UserRelation::getDestId);
    }

    @Override
    public List<Long> getFollowedUserIds(Long userId) {
        // 缓存
        String suffix = "followed";
        String userFollowedKey = OwnUtil.getRedisKey(this.key, suffix, userId);
        return this.getIdsByFunction(userId, userFollowedKey, RelationEnums.FOLLOW, UserRelation::getDestId, UserRelation::getSrcId);
    }

    @Override
    public List<Long> getFollowedEach(Long userId) {
        Set<Long> followSet = new HashSet<>();
        followSet.addAll(this.getFollowedUserIds(userId));
        followSet.retainAll(this.getUserFollowIds(userId));
        return new ArrayList<>(followSet);
    }

    @Override
    public List<Long> getUserBlock(Long userId) {
        // 缓存
        String suffix = "block";
        String userBlockKey = OwnUtil.getRedisKey(this.key, suffix, userId);
        return this.getIdsByFunction(userId, userBlockKey, RelationEnums.BLOCK, UserRelation::getSrcId, UserRelation::getDestId);
    }

    @Override
    public List<Long> getBlockedUser(Long userId) {
        String suffix = "blocked";
        String userBlockedKey = OwnUtil.getRedisKey(this.key, suffix, userId);
        return this.getIdsByFunction(userId, userBlockedKey, RelationEnums.BLOCK, UserRelation::getDestId, UserRelation::getSrcId);
    }

    @Override
    public List<Long> getBlockedEach(Long userId) {
        Set<Long> blockSet = new HashSet<>();
        blockSet.addAll(this.getBlockedUser(userId));
        blockSet.addAll(this.getUserBlock(userId));
        return new ArrayList<>(blockSet);
    }

    @Override
    public boolean isBlockedEach(Long userId, Long queryId) {
        if (userId.equals(queryId)) {
            return false;
        }
        List<Long> blockedEach = this.getBlockedEach(userId);
        return blockedEach.contains(queryId);
    }

    @Override
    public ResponseEntity<String> changeStatus(UserRelationDto userRelationDto) {
        // 基础值置空
        this.setBaseNull(userRelationDto);
        // 判断用户状态
        Long srcId = userRelationDto.getSrcId();
        Long destId = userRelationDto.getDestId();
        if (srcId == null || destId == null || srcId.equals(destId)) {
            return ResponseEntity.checkError(SystemConstants.SELF_CHANGE_FAIL);
        }
        // 删除关系key
        List<String> deleteKeys = new ArrayList<>();
        deleteKeys.add(OwnUtil.getRedisKey(this.key, srcId, destId));
        deleteKeys.add(OwnUtil.getRedisKey(this.key, "block", srcId));
        deleteKeys.add(OwnUtil.getRedisKey(this.key, "blocked", destId));
        deleteKeys.add(OwnUtil.getRedisKey(this.key, "follow", srcId));
        redisTemplate.delete(deleteKeys);
        // 改变数据库
        LambdaUpdateWrapper<UserRelation> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(UserRelation::getSrcId, userRelationDto.getSrcId()).eq(UserRelation::getDestId, userRelationDto.getDestId());
        this.saveOrUpdate(userRelationDto, updateWrapper);
        return ResponseEntity.ok(userRelationDto.getStatus().desc() + "成功");
    }

    public List<Long> getIdsByFunction(Long userId, String key, RelationEnums relationEnums, SFunction<UserRelation, Long> eqFunc, SFunction<UserRelation, Long> selectFunc) {
        // 缓存
        String idsString = redisTemplate.opsForValue().get(key);
        if (StringUtils.hasText(idsString)) {
            return JSON.parseArray(idsString, Long.class);
        }
        // 数据库
        LambdaQueryWrapper<UserRelation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(eqFunc, userId)
                .eq(UserRelation::getIsDeleted, MybatisEnums.NOT_DELETED.value())
                .eq(UserRelation::getStatus, relationEnums)
                .select(selectFunc);
        List<UserRelation> userRelations = baseMapper.selectList(queryWrapper);
        List<Long> ids = userRelations.stream().map(selectFunc).collect(Collectors.toList());
        // 存入缓存
        redisTemplate.opsForValue().set(key, JSON.toJSONString(ids), 1, TimeUnit.DAYS);
        return ids;
    }

    void setBaseNull(UserRelationDto userRelationDto) {
        userRelationDto.setRelationId(null);
        userRelationDto.setGmtCreate(null);
        userRelationDto.setIsDeleted(null);
    }

}
