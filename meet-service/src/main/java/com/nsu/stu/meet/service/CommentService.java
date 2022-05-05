package com.nsu.stu.meet.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.common.enums.OrderEnums;
import com.nsu.stu.meet.model.Comment;
import com.nsu.stu.meet.model.dto.comment.CommentDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentService extends CheckService{
    ResponseEntity<String> createComment (Long userId, CommentDto Comment);

    ResponseEntity<String> deleteCommentBatch (Long userId, List<Long> CommentIdList);

    ResponseEntity<IPage<CommentDto>> selectCommentRoot(Long articleId, Long userId, OrderEnums orderEnums, int page, int size);

    ResponseEntity<List<CommentDto>> selectCommentChildren (Long firstId, Long userId, Long commentId, int page, int size);

    ResponseEntity<List<CommentDto>> selectCommentSecond (Long secondId, Long userId, Long commentId, int page, int size);

}
