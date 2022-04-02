package com.nsu.stu.meet.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nsu.stu.meet.common.base.JwtStorage;
import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.common.enums.ArticleTypeEnums;
import com.nsu.stu.meet.model.Article;
import com.nsu.stu.meet.model.dto.ArticleDto;
import com.nsu.stu.meet.service.AlbumService;
import com.nsu.stu.meet.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseEntity<String> deleteBatch(@RequestBody List<Long> articleList) {
        Long userId = JwtStorage.userId();
        return articleService.deleteArticleBatch(userId, articleList);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<String> create(@RequestPart("files") MultipartFile[] files, @RequestBody Article article) {
        Long userId = JwtStorage.userId();
        return articleService.createArticle(userId, article, files);
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public ResponseEntity<String> modify(@RequestBody ArticleDto articleDto) {
        Long userId = JwtStorage.userId();
        return articleService.modifyArticle(userId, articleDto);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET, params = {"type", "page", "size"})
    public ResponseEntity<IPage<ArticleDto>> list(int type, int page, int size) {
        Long userId = JwtStorage.userId();
        switch (ArticleTypeEnums.lookUp(type)){
            case NEW:
                return articleService.selectArticleListLatest(userId, page, size);
            case HOT:
                return articleService.selectArticleListHot(userId, page, size);
            case FOLLOW:
                return articleService.selectArticleByFollow(page, size);
            default:
                return ResponseEntity.ok(null);
        }
    }
}
