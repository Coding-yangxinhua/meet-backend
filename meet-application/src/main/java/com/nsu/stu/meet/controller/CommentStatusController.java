package com.nsu.stu.meet.controller;

import com.nsu.stu.meet.common.base.JwtStorage;
import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.model.dto.CommentStatusDto;
import com.nsu.stu.meet.service.CommentStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author Xinhua X Yang
 */
@RestController
@RequestMapping("commentStatus")
public class CommentStatusController {
    @Autowired
    private CommentStatusService commentStatusService;

    @PostMapping(value = "/changeStatus")
    public ResponseEntity<String> changeStatus(@RequestBody CommentStatusDto commentStatusDto) {
        Long userId = JwtStorage.userId();
        commentStatusDto.setUserId(userId);
        return commentStatusService.changeStatus(commentStatusDto);
    }
}
