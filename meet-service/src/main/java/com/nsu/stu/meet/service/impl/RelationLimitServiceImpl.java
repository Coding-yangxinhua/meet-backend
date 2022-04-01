package com.nsu.stu.meet.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
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
import com.nsu.stu.meet.model.enums.RelationEnums;
import com.nsu.stu.meet.service.AlbumPhotoService;
import com.nsu.stu.meet.service.AlbumService;
import com.nsu.stu.meet.service.RelationLimitService;
import com.nsu.stu.meet.service.UserRelationService;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RelationLimitServiceImpl extends ServiceImpl<RelationLimitMapper, RelationLimit> implements RelationLimitService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    UserRelationService userRelationService;

    private final String key = "relation_limit";


    @Override
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

    @Override
    public List<Integer> getRelationLimit(Integer relation) {
        Map<Integer, List<Integer>> limitMap = getLimitMap();
        List<Integer> limits = limitMap.get(relation);
        if (limits == null) {
            return new ArrayList<>();
        }

        return limits;
    }

    @Override
    public List<Integer> getUserRelationLimit(Long userId, Long queryId) {
        UserRelation userRelation = userRelationService.getUserRelation(userId, queryId);
        int relation = userRelation.getStatus().value();
        return this.getRelationLimit(relation);
    }

    @Override
    public Map<Integer, List<Integer>> getLimitMap() {
        // 缓存
        String suffix = "all_map";
        String redisKey = OwnUtil.getRedisKey(key, suffix);
        String limitMapString = redisTemplate.opsForValue().get(redisKey);
        if (StringUtils.hasText(limitMapString)){
            return JSON.parseObject(limitMapString, new TypeReference<HashMap<Integer, List<Integer>>>() {});
        }
        // 数据库
        List<RelationLimitDto> allRelationLimit = this.getAllRelationLimit();
        Map<Integer, List<Integer>> limitMap = allRelationLimit.stream().collect(Collectors.toMap(RelationLimitDto::getRelationStatus, RelationLimitDto::getLimitIds));
        redisTemplate.opsForValue().set(redisKey, JSON.toJSONString(limitMap));
        return limitMap;
    }

    public boolean isLimitPass(Long userId, Long queryId, Integer limitId) {
        if (userId.equals(queryId)) {
            return true;
        }
        if (limitId == null) {
            return true;
        }
        UserRelation userRelation = userRelationService.getUserRelation(userId, queryId);
        if (userRelation == null) {
            return false;
        }
        RelationEnums status = userRelation.getStatus();
        Map<Integer, List<Integer>> limitMap = getLimitMap();
        List<Integer> limits = limitMap.get(status.value());
        if (limits.size() == 0) {
            return false;
        }
        return limits.contains(limitId);

    }


}
