package com.nsu.stu.meet.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.common.constant.SystemConstants;
import com.nsu.stu.meet.common.enums.OrderEnums;
import com.nsu.stu.meet.common.util.CosUtil;
import com.nsu.stu.meet.common.util.OwnUtil;
import com.nsu.stu.meet.dao.CommentMapper;
import com.nsu.stu.meet.model.Article;
import com.nsu.stu.meet.model.Comment;
import com.nsu.stu.meet.model.dto.comment.CommentBaseDto;
import com.nsu.stu.meet.model.dto.comment.CommentDto;
import com.nsu.stu.meet.model.vo.LimitVo;
import com.nsu.stu.meet.service.*;
import com.nsu.stu.meet.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

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
        // 初始化数据
        commentDto.setUserId(userId);
        commentDto.setCommentId(null);
        commentDto.setFirstId(null);
        commentDto.setSecondId(null);
        commentDto.setReplyUserId(null);

        // 处理回复评论的评论
        if (commentDto.getParentId() != null) {
            Comment comment = baseMapper.selectById(commentDto.getParentId());
            if (comment == null) {
                return ResponseEntity.checkError(SystemConstants.INFO_NOT_EXISTS);
            }
            commentDto.setArticleId(comment.getArticleId());
            commentDto.setReplyUserId(comment.getUserId());
            if (comment.getSecondId() != null) {
                commentDto.setSecondId(comment.getSecondId());
                commentDto.setFirstId(comment.getFirstId());
            } else if (comment.getFirstId() != null) {
                commentDto.setFirstId(comment.getFirstId());
                commentDto.setSecondId(comment.getCommentId());
            } else {
                commentDto.setFirstId(comment.getCommentId());
            }
        } else if (commentDto.getArticleId() != null) {
            // 处理回复文章的评论
            commentDto.setParentId(null);
        }
        baseMapper.insert(commentDto);
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
    public ResponseEntity<IPage<CommentDto>> selectCommentRoot(Long articleId, Long userId, OrderEnums orderEnums, int page, int size) {
        List<Long> blockList = userRelationService.getBlockedEach(userId);
        int start = (page - 1) * size;
        int end = start + size;
        List<CommentDto> commentDtos = null;
                switch (orderEnums){
            case TIME:
                commentDtos = baseMapper.selectCommentRootListLatest(articleId, userId, blockList, start, end);
                break;
            case HOT:
                commentDtos = baseMapper.selectCommentRootListHot(articleId, userId, blockList, start, end);
        }

        this.combineRootWithChildren(commentDtos, blockList);
        return ResponseEntity.ok(OwnUtil.records2Page(commentDtos, page, size));
    }

    @Override
    public ResponseEntity<List<CommentDto>> selectCommentChildren(Long firstId, Long userId, Long commentId, int page, int size) {
        List<Long> blockList = userRelationService.getBlockedEach(userId);
        int start = (page - 1) * size;
        int end = start + size;
        return ResponseEntity.ok(baseMapper.selectCommentChildren(firstId, userId, blockList, commentId, start, end));
    }

    @Override
    public ResponseEntity<List<CommentDto>> selectCommentSecond(Long secondId, Long userId, Long commentId, int page, int size) {
        List<Long> blockList = userRelationService.getBlockedEach(userId);
        int start = (page - 1) * size;
        int end = start + size;

        return ResponseEntity.ok(baseMapper.selectCommentSecond(secondId, userId, blockList, commentId, start, end));
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

    public void combineRootWithChildren(List<CommentDto> commentDtos, List<Long> blockList) {
        if (commentDtos.size() == 0) {
            return;
        }
        List<Long> commentIds = commentDtos.stream().map(Comment::getCommentId).collect(Collectors.toList());
        List<CommentBaseDto> children = baseMapper.selectCommentsByFirstIds(commentIds, blockList, 3);
        // 根据 comment_id 分类
        Map<Long, List<CommentBaseDto>> childrenMap = children.stream().collect(Collectors.groupingBy(CommentBaseDto::getFirstId));
        for (CommentDto commentDto:
             commentDtos) {
            commentDto.setChildrenComments(childrenMap.get(commentDto.getCommentId()));
        }
    }
}
