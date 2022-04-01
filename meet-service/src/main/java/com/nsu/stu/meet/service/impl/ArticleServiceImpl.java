package com.nsu.stu.meet.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
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
import com.nsu.stu.meet.model.vo.LimitVo;
import com.nsu.stu.meet.service.ArticleService;
import com.nsu.stu.meet.service.CheckService;
import com.nsu.stu.meet.service.RelationLimitService;
import com.nsu.stu.meet.service.UserRelationService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public ResponseEntity<String> createArticle (Long userId, Article album, MultipartFile[] files)  {
        album.setUserId(userId);
        List<String> urls = cosUtil.upload(files, 9);
        album.setPicUrls(JSON.toJSONString(urls));
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
        List<Long> followIds = userRelationService.getUserFollow(userId);
        List<Long> blockList = userRelationService.getBlockedEach(userId);
        List<ArticleDto> articleDtos = baseMapper.selectArticleByUserIdList(userId, followIds, blockList, start, end);
        return ResponseEntity.ok(OwnUtil.records2Page(articleDtos, end));
    }

    @Override
    public ResponseEntity<IPage<ArticleDto>> selectArticleListLatest(Long userId, int start, int end) {
        List<Long> blockList = userRelationService.getBlockedEach(userId);
        List<Long> followIds = userRelationService.getUserFollow(userId);
        List<ArticleDto> articleDtos = baseMapper.selectArticleListLatest(userId, followIds, blockList, start, end);
        return ResponseEntity.ok(OwnUtil.records2Page(articleDtos, end));
    }

    @Override
    public ResponseEntity<IPage<ArticleDto>> selectArticleListHot(Long userId, int start, int end) {
        List<Long> blockList = userRelationService.getBlockedEach(userId);
        List<Long> followIds = userRelationService.getUserFollow(userId);
        List<ArticleDto> articleDtos = baseMapper.selectArticleListHot(userId, followIds, blockList, start, end);
        return ResponseEntity.ok(OwnUtil.records2Page(articleDtos, end));
    }

    @Override
    public Long selectUserIdByArticle(Long articleId) {
        Article article = this.getById(articleId);
        if (article == null || article.getUserId() == null) {
            return null;
        }
        return article.getUserId();
    }

    @Override
    public Article selectByArticleId(Long articleId) {
        return baseMapper.selectById(articleId);
    }

    @Override
    public LimitVo getLimitVo(Long queryId) {
        // 获取文章实体
        Article article = this.getById(queryId);
        // 判空
        if (article == null) {
            return null;
        }
        // 文章所需权限
        Integer limitId = article.getLimitId().value();
        // 好友间关系对于权限
        Long userId = article.getUserId();
        return new LimitVo(userId, limitId);
    }
}
