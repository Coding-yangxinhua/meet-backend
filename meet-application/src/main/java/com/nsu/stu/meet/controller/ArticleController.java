package com.nsu.stu.meet.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nsu.stu.meet.annotation.LimitQuery;
import com.nsu.stu.meet.common.base.JwtStorage;
import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.common.constant.SystemConstants;
import com.nsu.stu.meet.common.enums.ArticleTypeEnums;
import com.nsu.stu.meet.model.dto.ArticleDto;
import com.nsu.stu.meet.service.ArticleHistoryService;
import com.nsu.stu.meet.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Xinhua X Yang
 */
@RestController
@RequestMapping("article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleHistoryService articleHistoryService;

    @PostMapping(value = "/delete")
    public ResponseEntity<String> deleteBatch(@RequestBody List<Long> articleList) {
        Long userId = JwtStorage.userId();
        return articleService.deleteArticleBatch(userId, articleList);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<String> create(@RequestPart(value = "files", required = false) MultipartFile[] files, @RequestParam(value = "article", required = false) String articleJson) {
        Long userId = JwtStorage.userId();
        ArticleDto article = JSON.parseObject(articleJson, ArticleDto.class);
        if (article == null && files.length == 0) {
            return ResponseEntity.checkError(SystemConstants.UNKNOWN_ERROR);
        }
        if (article == null) {
            article = new ArticleDto();
        }
        return articleService.createArticle(userId, article, files);
    }

    @GetMapping(value = "/modify")
    public ResponseEntity<String> modify(@RequestBody ArticleDto articleDto) {
        Long userId = JwtStorage.userId();
        return articleService.modifyArticle(userId, articleDto);
    }

    @LimitQuery(userColumn = "ma.user_id", limitColumn = "ma.limit_id")
    @GetMapping(value = "/list", params = {"type", "page", "size"})
    public ResponseEntity<IPage<ArticleDto>> list(int type, @RequestParam(value = "articleId", required = false) Long articleId, int page, int size) {
        Long userId = JwtStorage.userId();
        switch (ArticleTypeEnums.lookUp(type)){
            case NEW:
                return articleService.selectArticleListLatest(userId, articleId, page, size);
            case HOT:
                return articleService.selectArticleListHot(userId, articleId, page, size);
            case FOLLOW:
                return articleService.selectArticleByFollow(userId, articleId, page, size);
            default:
                return ResponseEntity.ok(null);
        }
    }

    @LimitQuery(userColumn = "ma.user_id", limitColumn = "ma.limit_id")
    @GetMapping(value = "/refresh", params = {"type", "page", "size"})
    public ResponseEntity<IPage<ArticleDto>> refresh(int type, @RequestParam(value = "articleId", required = false) Long articleId, int page, int size) {
        Long userId = JwtStorage.userId();
        switch (ArticleTypeEnums.lookUp(type)){
            case NEW:
                return articleService.refreshArticleListLatest(userId, articleId, page, size);
            case HOT:
                return articleService.refreshArticleListHot(userId, articleId, page, size);
            case FOLLOW:
                return articleService.refreshArticleByFollow(userId, articleId, page, size);
            default:
                return ResponseEntity.ok(null);
        }
    }

    @LimitQuery(userColumn = "ma.user_id", limitColumn = "ma.limit_id")
    @GetMapping(value = "/list/point", params = {"page", "size"})
    public ResponseEntity<IPage<ArticleDto>> listPoint(@RequestParam(value = "queryId", required = false) Long queryId, @RequestParam(value = "articleId", required = false)Long articleId, int page, int size) {
        Long userId = JwtStorage.userId();
        if (queryId == null) {
            queryId = userId;
        }
       return articleService.selectArticleByUserId(userId, queryId, articleId, page, size);

    }

    @LimitQuery(userColumn = "ma.user_id", limitColumn = "ma.limit_id")
    @GetMapping(value = "/refresh/point", params = {"page", "size"})
    public ResponseEntity<IPage<ArticleDto>> refreshPoint(@RequestParam(value = "queryId", required = false) Long queryId, @RequestParam(value = "articleId", required = false)Long articleId, int page, int size) {
        Long userId = JwtStorage.userId();
        if (queryId == null) {
            queryId = userId;
        }
        return articleService.refreshArticleByUserId(userId, queryId, articleId, page, size);
    }


    @GetMapping(value = "/list/history", params = {"page", "size"})
    public ResponseEntity<IPage<ArticleDto>> listHistory(int page, int size) {
        Long userId = JwtStorage.userId();
        return articleService.selectArticleByHistory(userId, page, size);

    }

    @LimitQuery(userColumn = "ma.user_id", limitColumn = "ma.limit_id")
    @GetMapping(value = "/detail", params = {"articleId"})
    public ResponseEntity<ArticleDto> articleDetail(Long articleId) {
        Long userId = JwtStorage.userId();
        if (userId != null) {
            articleHistoryService.setArticleHistory(userId, articleId);
        }
        return articleService.selectByArticleId(userId, articleId);
    }
}
