package com.nsu.stu.meet.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.common.util.CosUtil;
import com.nsu.stu.meet.common.util.OwnUtil;
import com.nsu.stu.meet.dao.ArticleMapper;
import com.nsu.stu.meet.model.Article;
import com.nsu.stu.meet.model.dto.ArticleDto;
import com.nsu.stu.meet.model.vo.LimitVo;
import com.nsu.stu.meet.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Xinhua X Yang
 */
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

    public ResponseEntity<String> createArticle (Long userId, Article article, MultipartFile[] files)  {
        article.setUserId(userId);
        List<String> urls = null;
        if (files != null && files.length > 0) {
            urls = cosUtil.upload(files, 9);
        }
        article.setPicUrls(JSON.toJSONString(urls));
        this.save(article);
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
    public ResponseEntity<IPage<ArticleDto>> selectArticleByFollow(Long userId, Long articleId, int page, int size) {
        List<Long> userFollowIds = userRelationService.getUserFollowIds(userId);
        int start = (page - 1) * size;
        List<ArticleDto> articleDtoList = baseMapper.selectArticleByUserIdList(userId, articleId, userFollowIds, start, size + size);
        return ResponseEntity.ok(OwnUtil.records2Page(articleDtoList, page, size));
    }

    @Override
    public ResponseEntity<IPage<ArticleDto>> selectArticleListLatest(Long userId, Long articleId, int page, int size) {
        int start = (page - 1) * size;
        List<ArticleDto> articleDtoList = baseMapper.selectArticleListLatest(userId, articleId, start, start + size);
        return ResponseEntity.ok(OwnUtil.records2Page(articleDtoList, page, size));
    }

    @Override
    public ResponseEntity<IPage<ArticleDto>> selectArticleListHot(Long userId, Long articleId, int page, int size) {
        int start = (page - 1) * size;
        List<ArticleDto> articleDtoList = baseMapper.selectArticleListHot(userId, articleId, start, start + size);
        return ResponseEntity.ok(OwnUtil.records2Page(articleDtoList, page, size));
    }

    @Override
    public ResponseEntity<IPage<ArticleDto>> selectArticleByUserId(Long userId, Long queryId, Long articleId, int page, int size) {
        int start = (page - 1) * size;
        List<ArticleDto> articleDtoList = baseMapper.selectArticleByUserId(userId, queryId, articleId, start, start + size);
        return ResponseEntity.ok(OwnUtil.records2Page(articleDtoList, page, size));
    }

    @Override
    public ResponseEntity<IPage<ArticleDto>> selectArticleByHistory(Long userId, int page, int size) {
        int start = (page - 1) * size;
        List<ArticleDto> articleDtoList = baseMapper.selectArticleByHistory(userId, start, start + size);
        return ResponseEntity.ok(OwnUtil.records2Page(articleDtoList, page, size));
    }

    @Override
    public ResponseEntity<IPage<ArticleDto>> refreshArticleByFollow(Long userId, Long articleId, int page, int size) {
        int start = (page - 1) * size;
        List<ArticleDto> articleDtoList = baseMapper.refreshArticleListHot(userId, articleId, start, start + size);
        return ResponseEntity.ok(OwnUtil.records2Page(articleDtoList, page, size));
    }

    @Override
    public ResponseEntity<IPage<ArticleDto>> refreshArticleListLatest(Long userId, Long articleId, int page, int size) {
        int start = (page - 1) * size;
        List<ArticleDto> articleDtoList = baseMapper.refreshArticleListLatest(userId, articleId, start, start + size);
        return ResponseEntity.ok(OwnUtil.records2Page(articleDtoList, page, size));
    }

    @Override
    public ResponseEntity<IPage<ArticleDto>> refreshArticleListHot(Long userId, Long articleId, int page, int size) {
        int start = (page - 1) * size;
        List<ArticleDto> articleDtoList = baseMapper.refreshArticleListHot(userId, articleId, start, start + size);
        return ResponseEntity.ok(OwnUtil.records2Page(articleDtoList, page, size));
    }

    @Override
    public ResponseEntity<IPage<ArticleDto>> refreshArticleByUserId(Long userId, Long queryId, Long articleId, int page, int size) {
        int start = (page - 1) * size;
        List<ArticleDto> articleDtoList = baseMapper.refreshArticleByUserId(userId, queryId, articleId, start, start + size);
        return ResponseEntity.ok(OwnUtil.records2Page(articleDtoList, page, size));
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
    public Article selectById(Long articleId) {
        return this.getById(articleId);
    }

    @Override
    public ResponseEntity<ArticleDto> selectByArticleId(Long userId, Long articleId) {
        return ResponseEntity.ok(baseMapper.selectArticleById(userId, articleId));
    }

    @Override
    public List<Article> select() {
        return this.list();
    }


    @Override
    public LimitVo getLimitVo(Long queryId) {
        // 获取文章实体
        Article article = this.getById(queryId);
        // 判空
        if (article == null) {
            return null;
        }
        return new LimitVo(article.getUserId(), article.getLimitId());
    }
}
