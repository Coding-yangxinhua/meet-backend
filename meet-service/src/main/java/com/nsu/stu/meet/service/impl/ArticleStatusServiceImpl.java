package com.nsu.stu.meet.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nsu.stu.meet.common.base.JwtStorage;
import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.common.util.CosUtil;
import com.nsu.stu.meet.common.util.OwnUtil;
import com.nsu.stu.meet.dao.ArticleMapper;
import com.nsu.stu.meet.dao.ArticleStatusMapper;
import com.nsu.stu.meet.model.Article;
import com.nsu.stu.meet.model.ArticleStatus;
import com.nsu.stu.meet.model.dto.ArticleDto;
import com.nsu.stu.meet.service.*;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ArticleStatusServiceImpl extends ServiceImpl<ArticleStatusMapper, ArticleStatus> implements ArticleStatusService {

    @Autowired
    RelationLimitService relationLimitService;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    ArticleService articleService;


    @Override
    public ResponseEntity<String> like(Long articleId) {
        return null;
    }

    @Override
    public ResponseEntity<String> dislike(Long articleId) {
        return null;
    }

    @Override
    public ResponseEntity<String> repost(Long articleId) {
        return null;
    }

    @Override
    public ResponseEntity<String> star(Long articleId) {
        return null;
    }

    @Override
    public Long getUserId(Long queryId) {
        return articleService.selectUserIdByArticle(queryId);
    }
}
