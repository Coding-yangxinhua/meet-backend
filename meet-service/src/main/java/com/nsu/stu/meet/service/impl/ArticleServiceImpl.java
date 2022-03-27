package com.nsu.stu.meet.service.impl;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nsu.stu.meet.common.base.JwtInfo;
import com.nsu.stu.meet.common.base.JwtStorage;
import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.common.util.CosUtil;
import com.nsu.stu.meet.common.util.JwtUtil;
import com.nsu.stu.meet.dao.AlbumMapper;
import com.nsu.stu.meet.dao.ArticleMapper;
import com.nsu.stu.meet.model.Album;
import com.nsu.stu.meet.model.Article;
import com.nsu.stu.meet.model.RelationLimit;
import com.nsu.stu.meet.model.dto.AlbumDto;
import com.nsu.stu.meet.model.dto.ArticleDto;
import com.nsu.stu.meet.service.AlbumService;
import com.nsu.stu.meet.service.ArticleService;
import com.nsu.stu.meet.service.RelationLimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Autowired
    CosUtil cosUtil;

    @Autowired
    RelationLimitService relationLimitService;


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
    public ResponseEntity<List<ArticleDto>> selectArticleByUserId(Long userId, Long queryId, List<RelationLimit> limits, int start, int end) {
        return ResponseEntity.ok(baseMapper.selectArticleByUserId(userId, queryId, limits, start, end));
    }

    @Override
    public ResponseEntity<List<ArticleDto>> selectArticleList(Long userId, int start, int end) {
        return ResponseEntity.ok(baseMapper.selectArticleList(userId, start, end));
    }

    @Override
    public ResponseEntity<List<ArticleDto>> selectArticleListWithNoLimit(Long userId, Long queryId, int start, int end) {
        return ResponseEntity.ok(baseMapper.selectArticleListWithNoLimit(userId, queryId, start, end));
    }

    @Override
    public IPage<ArticleDto> test(IPage<ArticleDto> page, Long userId, Long queryId, int start, int end) {
        return baseMapper.test(page, userId, queryId, start, end);
    }


    private void setBaseNull(ArticleDto articleDto) {

    }

}
