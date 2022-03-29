package com.nsu.stu.meet.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nsu.stu.meet.common.base.JwtStorage;
import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.common.util.CosUtil;
import com.nsu.stu.meet.common.util.OwnUtil;
import com.nsu.stu.meet.dao.ArticleMapper;
import com.nsu.stu.meet.model.Article;
import com.nsu.stu.meet.model.dto.ArticleDto;
import com.nsu.stu.meet.service.ArticleService;
import com.nsu.stu.meet.service.RelationLimitService;
import com.nsu.stu.meet.service.UserRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Autowired
    CosUtil cosUtil;

    @Autowired
    RelationLimitService relationLimitService;

    @Autowired
    UserRelationService userRelationService;

    @Autowired
    StringRedisTemplate redisTemplate;


    public ResponseEntity<String> createArticle (Long userId, ArticleDto albumDto, MultipartFile[] files)  {
        albumDto.setUserId(userId);
        List<String> urls = cosUtil.upload(files, 9);
        albumDto.setPicUrls(JSON.toJSONString(urls));
        return ResponseEntity.ok();
    }

    @Override
    public ResponseEntity<String> modifyArticle(Long userId, ArticleDto articleDto) {
        return ResponseEntity.ok();
    }

    @Override
    public ResponseEntity<String> deleteArticleBatch(Long userId, List<Long> articleIdList) {
        if (articleIdList.isEmpty()) {
            return ResponseEntity.ok();
        }
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getUserId, userId).in(Article::getArticleId, articleIdList);
        baseMapper.delete(queryWrapper);
        return ResponseEntity.ok();
    }

    @Override
    public ResponseEntity<IPage<ArticleDto>> selectArticleByFollow(int start, int end) {
        Long userId = JwtStorage.userId();
        List<Long> followIds = userRelationService.getFollowIdByUserId(userId);
        List<ArticleDto> articleDtos = baseMapper.selectArticleByUserIdList(userId, followIds, start, end);
        return ResponseEntity.ok(OwnUtil.records2Page(articleDtos, end));
    }

    @Override
    public ResponseEntity<IPage<ArticleDto>> selectArticleListLatest(Long userId, int start, int end) {
        List<ArticleDto> articleDtos = baseMapper.selectArticleListLatest(userId, start, end);
        return ResponseEntity.ok(OwnUtil.records2Page(articleDtos, end));
    }

    @Override
    public ResponseEntity<IPage<ArticleDto>> selectArticleListHot(Long userId, int start, int end) {
        List<ArticleDto> articleDtos = baseMapper.selectArticleListHot(userId, start, end);
        return ResponseEntity.ok(OwnUtil.records2Page(articleDtos, end));
    }

    @Override
    public Long selectUserIdByArticle(Long articleId) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getArticleId, articleId).select(Article::getUserId);
        Article article = baseMapper.selectOne(queryWrapper);
        return article.getUserId();
    }

    @Override
    public Long getUserId(Long queryId) {
        return this.selectUserIdByArticle(queryId);
    }
}
