package com.nsu.stu.meet.controller;

import com.nsu.stu.meet.annotation.Limit;
import com.nsu.stu.meet.common.base.JwtStorage;
import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.service.ArticleService;
import com.nsu.stu.meet.service.ArticleStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("articleStatus")
public class ArticleStatusController {
    @Autowired
    private ArticleStatusService articleStatusService;

    @Limit(clazz = ArticleService.class)
    @RequestMapping(value = "/like", method = RequestMethod.POST)
    public ResponseEntity<String> deleteBatch(Long articleId) {
        Long userId = JwtStorage.userId();
        return null;
    }
}
