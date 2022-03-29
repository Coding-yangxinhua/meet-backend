package com.nsu.stu.meet.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nsu.stu.meet.common.base.LimitBaseService;
import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.model.Album;
import com.nsu.stu.meet.model.RelationLimit;
import com.nsu.stu.meet.model.dto.AlbumDto;
import com.nsu.stu.meet.model.dto.ArticleDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ArticleService extends LimitBaseService {
    ResponseEntity<String> createArticle (Long userId, ArticleDto articleDto, MultipartFile[] files);

    ResponseEntity<String> modifyArticle (Long userId, ArticleDto articleDto);

    ResponseEntity<String> deleteArticleBatch (Long userId, List<Long> articleIdList);


    ResponseEntity<IPage<ArticleDto>> selectArticleByFollow (int start, int end);

    ResponseEntity<IPage<ArticleDto>> selectArticleListLatest (Long userId, int start, int end);

    ResponseEntity<IPage<ArticleDto>> selectArticleListHot(Long userId, int start, int end);

    Long selectUserIdByArticle(Long articleId);
}
