package com.nsu.stu.meet.controller;

import com.nsu.stu.meet.annotation.LimitQuery;
import com.nsu.stu.meet.model.Article;
import com.nsu.stu.meet.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Xinhua X Yang
 */
@RestController
@RequestMapping("test")
public class TestController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("article")
    @LimitQuery
    public List<Article> getArticle() {
        return articleService.select();
    }

    @GetMapping("handler")
    public List<Article> getHandler() {
        return articleService.select();
    }
}
