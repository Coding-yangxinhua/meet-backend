package com.nsu.stu.meet.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nsu.stu.meet.model.Comment;
import com.nsu.stu.meet.model.dto.ArticleDto;
import com.nsu.stu.meet.model.dto.CommentDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface CommentMapper extends BaseMapper<Comment> {
    List<CommentDto> selectCommentRootListLatest (@Param("articleId") Long articleId, @Param("userId") Long userId, @Param("blockList") List<Long> blockList, @Param("start") int start, @Param("end") int end);

    List<CommentDto> selectCommentRootListHot (@Param("userId") Long userId, @Param("blockList") List<Long> blockList, @Param("start") int start, @Param("end") int end);

    List<CommentDto> selectCommentChildrenLatest (@Param("commentId") Long commentId, @Param("userId") Long userId, @Param("blockList") List<Long> blockList, @Param("start") int start, @Param("end") int end);

    List<CommentDto> selectCommentChildrenHot (@Param("commentId") Long commentId, @Param("userId") Long userId, @Param("blockList") List<Long> blockList, @Param("start") int start, @Param("end") int end);

    List<CommentDto> selectHotChildrenByIds (@Param("commentIds") List<Long> commentIds, @Param("userId") Long userId, @Param("blockList") List<Long> blockList, @Param("size") int size);

    List<CommentDto> selectLatestChildrenByIds (@Param("commentIds") List<Long> commentIds, @Param("userId") Long userId, @Param("blockList") List<Long> blockList, @Param("size") int size);
}
