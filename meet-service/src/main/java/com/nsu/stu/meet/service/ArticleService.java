package com.nsu.stu.meet.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.model.Album;
import com.nsu.stu.meet.model.RelationLimit;
import com.nsu.stu.meet.model.dto.AlbumDto;
import com.nsu.stu.meet.model.dto.ArticleDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ArticleService {
    ResponseEntity<String> createArticle (Long userId, ArticleDto articleDto, MultipartFile[] files);

    ResponseEntity<String> modifyArticle (Long userId, ArticleDto articleDto);

    ResponseEntity<String> deleteArticleBatch (Long userId, List<Long> articleIdList);


    ResponseEntity<List<ArticleDto>> selectArticleByUserId (Long userId, Long queryId, List<RelationLimit> limits, int start, int end);

    ResponseEntity<List<ArticleDto>> selectArticleList (Long userId, int start, int end);

    ResponseEntity<List<ArticleDto>> selectArticleListWithNoLimit (Long userId, Long queryId, int start, int end);

    IPage<ArticleDto> test (IPage<ArticleDto> page, Long userId, Long queryId, int start, int end);
}
