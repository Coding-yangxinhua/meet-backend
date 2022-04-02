package com.nsu.stu.meet.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.common.enums.OrderEnums;
import com.nsu.stu.meet.model.Comment;
import com.nsu.stu.meet.model.dto.CommentDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CommentService extends CheckService{
    ResponseEntity<String> createComment (Long userId, CommentDto Comment);

    ResponseEntity<String> modifyComment (Long userId, CommentDto CommentDto);

    ResponseEntity<String> deleteCommentBatch (Long userId, List<Long> CommentIdList);

    ResponseEntity<IPage<CommentDto>> selectCommentList(Long articleId, Long userId, OrderEnums orderEnums, int page, int size);


    Long selectUserIdByComment(Long CommentId);

    Comment selectByCommentId(Long CommentId);
}
