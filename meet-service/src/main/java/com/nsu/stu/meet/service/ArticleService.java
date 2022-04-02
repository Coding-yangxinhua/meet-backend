package com.nsu.stu.meet.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.model.Article;
import com.nsu.stu.meet.model.dto.ArticleDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ArticleService extends CheckService{
    ResponseEntity<String> createArticle (Long userId, Article article, MultipartFile[] files);

    ResponseEntity<String> modifyArticle (Long userId, ArticleDto articleDto);

    ResponseEntity<String> deleteArticleBatch (Long userId, List<Long> articleIdList);

    ResponseEntity<IPage<ArticleDto>> selectArticleByFollow (int page, int size);

    ResponseEntity<IPage<ArticleDto>> selectArticleListLatest (Long userId, int page, int size);

    ResponseEntity<IPage<ArticleDto>> selectArticleListHot(Long userId, int page, int size);

    Long selectUserIdByArticle(Long articleId);

    Article selectByArticleId(Long articleId);
}
