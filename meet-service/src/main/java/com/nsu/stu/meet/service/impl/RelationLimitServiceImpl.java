package com.nsu.stu.meet.service.impl;

import com.alibaba.fastjson.JSON;
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
import com.nsu.stu.meet.common.util.OwnUtil;
import com.nsu.stu.meet.dao.AlbumPhotoMapper;
import com.nsu.stu.meet.dao.RelationLimitMapper;
import com.nsu.stu.meet.model.Album;
import com.nsu.stu.meet.model.AlbumPhoto;
import com.nsu.stu.meet.model.RelationLimit;
import com.nsu.stu.meet.model.UserRelation;
import com.nsu.stu.meet.model.dto.RelationLimitDto;
import com.nsu.stu.meet.model.enums.LimitEnums;
import com.nsu.stu.meet.service.AlbumPhotoService;
import com.nsu.stu.meet.service.AlbumService;
import com.nsu.stu.meet.service.RelationLimitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RelationLimitServiceImpl extends ServiceImpl<RelationLimitMapper, RelationLimit> implements RelationLimitService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    private final String key = "relation_limit";

    @Override
    public List<RelationLimit> getUserRelationLimit(Long srcId, Long destId) {
        List<RelationLimit> limitList;
        String redisKey = OwnUtil.getRedisKey(this.key, srcId, destId);
        String relationLimit = redisTemplate.opsForValue().get(redisKey);
        if (relationLimit != null) {
            limitList = JSON.parseArray(relationLimit, RelationLimit.class);
        } else {
            limitList = baseMapper.getLimitByUser(srcId, destId);
            redisTemplate.opsForValue().set(redisKey, JSON.toJSONString(limitList), 1, TimeUnit.HOURS);
        }
        return limitList;

    }

    public List<RelationLimitDto> getAllRelationLimit() {
        String suffix = "all";
        String redisKey = OwnUtil.getRedisKey(key, suffix);
        String limitString = redisTemplate.opsForValue().get(redisKey);
        if (StringUtils.hasText(limitString)) {
            return JSON.parseArray(limitString, RelationLimitDto.class);
        }
        // 数据库
        List<RelationLimitDto> limitGroupByStatus = baseMapper.getLimitGroupByStatus();
        redisTemplate.opsForValue().set(redisKey, JSON.toJSONString(limitGroupByStatus), 1, TimeUnit.DAYS);
        return limitGroupByStatus;
    }

    public boolean isLimitPass(Long userId, Long queryId, UserRelation relation) {

    }


}
