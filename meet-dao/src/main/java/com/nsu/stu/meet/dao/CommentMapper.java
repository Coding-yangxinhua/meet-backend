package com.nsu.stu.meet.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nsu.stu.meet.model.Comment;
import com.nsu.stu.meet.model.dto.comment.CommentBaseDto;
import com.nsu.stu.meet.model.dto.comment.CommentDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface CommentMapper extends BaseMapper<Comment> {
    List<CommentDto> selectCommentRootListLatest (@Param("articleId") Long articleId, @Param("userId") Long userId, @Param("blockList") List<Long> blockList, @Param("start") int start, @Param("end") int end);

    List<CommentDto> selectCommentRootListHot (@Param("articleId") Long articleId, @Param("userId") Long userId, @Param("blockList") List<Long> blockList, @Param("start") int start, @Param("end") int end);

    List<CommentDto> selectCommentChildren (@Param("firstId") Long firstId, @Param("userId") Long userId, @Param("blockList") List<Long> blockList, @Param("commentId") Long commentId, @Param("start") int start, @Param("end") int end);

    List<CommentDto> selectCommentSecond (@Param("secondId") Long secondId, @Param("userId") Long userId, @Param("blockList") List<Long> blockList, @Param("commentId") Long commentId, @Param("start") int start, @Param("end") int end);

    List<CommentBaseDto> selectCommentsByFirstIds (@Param("firstIds") List<Long> firstIds, @Param("blockList") List<Long> blockList, @Param("size") int size);
}
