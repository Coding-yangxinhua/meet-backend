package com.nsu.stu.meet.controller;

import com.nsu.stu.meet.common.base.JwtStorage;
import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.common.enums.ArticleTypeEnums;
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
    public ResponseEntity<String> create(@RequestBody ArticleDto articleDto, @RequestPart("files") MultipartFile[] files) {
        Long userId = JwtStorage.userId();
        return articleService.createArticle(userId, articleDto, files);
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public ResponseEntity<String> modify(@RequestBody ArticleDto articleDto) {
        Long userId = JwtStorage.userId();
        return articleService.modifyArticle(userId, articleDto);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET, params = {"type", "page", "size"})
    public ResponseEntity<List<ArticleDto>> list(int type, int page, int size) {
        Long userId = JwtStorage.userId();
        switch (ArticleTypeEnums.lookUp(type)){
            case NEW:
                return articleService.selectArticleList(userId, page, page + size);
            case HOT:
                break;
            case FOLLOW:
                break;
            default:
                return ResponseEntity.ok();
        }
        return ResponseEntity.ok();
    }
}
