package com.nsu.stu.meet.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nsu.stu.meet.common.base.JwtStorage;
import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.common.enums.ArticleTypeEnums;
import com.nsu.stu.meet.common.enums.OrderEnums;
import com.nsu.stu.meet.common.util.CosUtil;
import com.nsu.stu.meet.common.util.OwnUtil;
import com.nsu.stu.meet.dao.CommentMapper;
import com.nsu.stu.meet.dao.CommentMapper;
import com.nsu.stu.meet.model.Article;
import com.nsu.stu.meet.model.ArticleStatus;
import com.nsu.stu.meet.model.Comment;
import com.nsu.stu.meet.model.Comment;
import com.nsu.stu.meet.model.dto.CommentDto;
import com.nsu.stu.meet.model.vo.LimitVo;
import com.nsu.stu.meet.service.*;
import com.nsu.stu.meet.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    CosUtil cosUtil;

    @Autowired
    RelationLimitService relationLimitService;

    @Autowired
    UserRelationService userRelationService;

    @Autowired
    ArticleService articleService;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Override
    public ResponseEntity<String> createComment (Long userId, CommentDto commentDto)  {
        commentDto.setUserId(userId);
        commentDto.setCommentId(null);
        baseMapper.insert(commentDto);
        return ResponseEntity.ok();
    }

    @Override
    public ResponseEntity<String> modifyComment(Long userId, CommentDto CommentDto) {
        return ResponseEntity.ok();
    }

    @Override
    public ResponseEntity<String> deleteCommentBatch(Long userId, List<Long> CommentIdList) {
        if (CommentIdList.isEmpty()) {
            return ResponseEntity.ok();
        }
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getUserId, userId).in(Comment::getCommentId, CommentIdList);
        baseMapper.delete(queryWrapper);
        return ResponseEntity.ok();
    }

    @Override
    public ResponseEntity<IPage<CommentDto>> selectCommentList(Long articleId, Long userId, OrderEnums orderEnums, int start, int end) {
        List<Long> blockList = userRelationService.getBlockedEach(userId);
        List<CommentDto> commentDtos = null;
                switch (orderEnums){
            case TIME:
                commentDtos = baseMapper.selectCommentRootListLatest(articleId, userId, blockList, start, end);
                break;
            case HOT:
                commentDtos = baseMapper.selectCommentRootListLatest(articleId, userId, blockList, start, end);
        }

        this.combineRootWithChildren(commentDtos, userId, blockList, orderEnums);
        return ResponseEntity.ok(OwnUtil.records2Page(commentDtos, end));
    }


    @Override
    public Long selectUserIdByComment(Long CommentId) {
        Comment Comment = this.getById(CommentId);
        if (Comment == null || Comment.getUserId() == null) {
            return null;
        }
        return Comment.getUserId();
    }

    @Override
    public Comment selectByCommentId(Long CommentId) {
        return baseMapper.selectById(CommentId);
    }

    @Override
    public LimitVo getLimitVo(Long queryId) {
        // 获取文章实体
        Article article = articleService.selectByArticleId(queryId);
        if (article == null) {
            return null;
        }
        return new LimitVo(article.getUserId(), article.getLimitId().value());
    }

    public void combineRootWithChildren(List<CommentDto> commentDtos, Long userId, List<Long> blockList, OrderEnums orderEnums) {
        if (commentDtos.size() == 0) {
            return;
        }
        List<Long> commentIds = commentDtos.stream().map(Comment::getCommentId).collect(Collectors.toList());
        List<CommentDto> children = null;
        switch (OrderEnums.lookUp(orderEnums.value())) {
            case TIME:
                children= baseMapper.selectLatestChildrenByIds(commentIds, userId, blockList, 3);
                break;
            case HOT:
                children = baseMapper.selectHotChildrenByIds(commentIds, userId, blockList, 3);
                break;
            default:
                break;
        }
        // 根据 comment_id 分类
        Map<Long, List<CommentDto>> childrenMap = children.stream().collect(Collectors.groupingBy(Comment::getParentId));
        for (CommentDto commentDto:
             commentDtos) {
            commentDto.setCommentDtoList(childrenMap.get(commentDto.getCommentId()));
        }
    }
}
