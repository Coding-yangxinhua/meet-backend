package com.nsu.stu.meet.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nsu.stu.meet.common.base.JwtStorage;
import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.common.enums.OrderEnums;
import com.nsu.stu.meet.model.dto.comment.CommentDto;
import com.nsu.stu.meet.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Xinhua X Yang
 */
@RestController
@RequestMapping("comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping(value = "/deleteBatch")
    public ResponseEntity<String> deleteBatch(@RequestBody List<Long> commentIdList) {
        Long userId = JwtStorage.userId();
        return commentService.deleteCommentBatch(userId, commentIdList);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<String> create(@RequestBody CommentDto commentDto) {
        Long userId = JwtStorage.userId();
        return commentService.createComment(userId, commentDto);
    }

    @PostMapping(value = "/modify")
    public ResponseEntity<String> modify(@RequestBody CommentDto commentDto) {
        return null;
    }

    @GetMapping(value = "/listRoot", params = {"articleId", "order", "page", "size"})
    public ResponseEntity<IPage<CommentDto>> listRoot(Long articleId, Integer order, Integer page, Integer size) {
        Long userId = JwtStorage.userId();
        return commentService.selectCommentRoot(articleId, userId, OrderEnums.lookUp(order), page, size);
    }

    @GetMapping(value = "/listChildren", params = {"firstId", "page", "size"})
    public ResponseEntity<List<CommentDto>> listChildren(Long firstId, @RequestParam(value = "commentId", required = false) Long commentId, Integer page, Integer size) {
        Long userId = JwtStorage.userId();
        return commentService.selectCommentChildren(firstId, userId, commentId, page, size);
    }

    @GetMapping(value = "/listSecond", params = {"secondId", "page", "size"})
    public ResponseEntity<List<CommentDto>> listSecond(Long secondId, @RequestParam(value = "commentId", required = false) Long commentId, Integer page, Integer size) {
        Long userId = JwtStorage.userId();
        return commentService.selectCommentSecond(secondId, userId, commentId, page, size);
    }
}
