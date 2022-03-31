package com.nsu.stu.meet.controller;

import com.nsu.stu.meet.annotation.Limit;
import com.nsu.stu.meet.common.base.JwtStorage;
import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.model.dto.ArticleStatusDto;
import com.nsu.stu.meet.service.ArticleStatusService;
import com.nsu.stu.meet.service.impl.ArticleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("articleStatus")
public class ArticleStatusController {
    @Autowired
    private ArticleStatusService articleStatusService;

    @Limit(clazz = ArticleServiceImpl.class)
    @RequestMapping(value = "/changeStatus", method = RequestMethod.POST, params = {"articleId"})
    public ResponseEntity<String> changeStatus(@RequestBody ArticleStatusDto articleStatusDto) {
        Long userId = JwtStorage.userId();
        articleStatusDto.setUserId(userId);
        return articleStatusService.changeStatus(articleStatusDto);
    }
}
