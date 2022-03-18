package com.nsu.stu.meet.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nsu.stu.meet.common.base.JwtStorage;
import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.common.constant.SystemConstants;
import com.nsu.stu.meet.common.util.CosUtil;
import com.nsu.stu.meet.common.util.OwnUtil;
import com.nsu.stu.meet.dao.AlbumPhotoMapper;
import com.nsu.stu.meet.dao.UserRelationMapper;
import com.nsu.stu.meet.model.Album;
import com.nsu.stu.meet.model.AlbumPhoto;
import com.nsu.stu.meet.model.UserRelation;
import com.nsu.stu.meet.service.AlbumPhotoService;
import com.nsu.stu.meet.service.AlbumService;
import com.nsu.stu.meet.service.UserRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserRelationServiceImpl extends ServiceImpl<UserRelationMapper, UserRelation> implements UserRelationService {
    @Autowired
    private StringRedisTemplate redisTemplate;

    private final String key = "user_relation_";

    @Override
    public UserRelation getUserRelation(Long srcId, Long destId) {
        String key = OwnUtil.getRedisKey(this.key, srcId, destId);
        String userRelationKey = redisTemplate.opsForValue().get(key);
        if (userRelationKey != null) {
            return JSON.parseObject(userRelationKey, UserRelation.class);
        }
        LambdaQueryWrapper<UserRelation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRelation::getSrcId, srcId).eq(UserRelation::getDestId, destId);
        UserRelation userRelation = baseMapper.selectOne(queryWrapper);
        redisTemplate.opsForValue().set(key, JSON.toJSONString(userRelation));
        return userRelation;
    }
}
