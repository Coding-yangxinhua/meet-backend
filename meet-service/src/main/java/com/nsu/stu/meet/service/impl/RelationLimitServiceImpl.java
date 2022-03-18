package com.nsu.stu.meet.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nsu.stu.meet.common.base.JwtInfo;
import com.nsu.stu.meet.common.base.JwtStorage;
import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.common.constant.SystemConstants;
import com.nsu.stu.meet.common.util.CosUtil;
import com.nsu.stu.meet.dao.AlbumPhotoMapper;
import com.nsu.stu.meet.dao.RelationLimitMapper;
import com.nsu.stu.meet.model.Album;
import com.nsu.stu.meet.model.AlbumPhoto;
import com.nsu.stu.meet.model.RelationLimit;
import com.nsu.stu.meet.service.AlbumPhotoService;
import com.nsu.stu.meet.service.AlbumService;
import com.nsu.stu.meet.service.RelationLimitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class RelationLimitServiceImpl extends ServiceImpl<RelationLimitMapper, RelationLimit> implements RelationLimitService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    private final String key = "relation_limit_";

    @Override
    public List<RelationLimit> getUserRelationLimit(Long destId) {
        Long userId = JwtStorage.info().getUserId();
        List<RelationLimit> limitByUser = baseMapper.getLimitByUser(userId, destId);
        String relationLimit = redisTemplate.opsForValue().get(this.key + userId + "_" + destId);

    }
}
