package com.nsu.stu.meet.controller;

import com.nsu.stu.meet.annotation.Limit;
import com.nsu.stu.meet.common.base.JwtStorage;
import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.model.dto.ArticleStatusDto;
import com.nsu.stu.meet.model.dto.CommentStatusDto;
import com.nsu.stu.meet.service.ArticleStatusService;
import com.nsu.stu.meet.service.CommentStatusService;
import com.nsu.stu.meet.service.impl.ArticleServiceImpl;
import com.nsu.stu.meet.service.impl.CommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("commentStatus")
public class CommentStatusController {
    @Autowired
    private CommentStatusService commentStatusService;

    @RequestMapping(value = "/changeStatus", method = RequestMethod.POST)
    public ResponseEntity<String> changeStatus(@RequestBody CommentStatusDto commentStatusDto) {
        Long userId = JwtStorage.userId();
        commentStatusDto.setUserId(userId);
        return commentStatusService.changeStatus(commentStatusDto);
    }
}
