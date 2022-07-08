package com.nsu.stu.meet.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nsu.stu.meet.model.Article;
import com.nsu.stu.meet.model.dto.ArticleDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author Xinhua X Yang
 */
public interface ArticleMapper extends BaseMapper<Article>{
    List<ArticleDto> selectArticleByUserIdList (@Param("userId") Long userId, @Param("articleId") Long articleId, @Param("queryIdList") List<Long> queryIdList,  @Param("start") int start, @Param("end") int end);

    List<ArticleDto> selectArticleListLatest (@Param("userId") Long userId, @Param("articleId") Long articleId, @Param("start") int start, @Param("end") int end);

    List<ArticleDto> selectArticleListHot (@Param("userId") Long userId, @Param("articleId") Long articleId, @Param("start") int start, @Param("end") int end);

    List<ArticleDto> selectArticleByUserId (@Param("userId") Long userId, @Param("queryId") Long queryId, @Param("articleId") Long articleId, @Param("start") int start, @Param("end") int end);


    List<ArticleDto> refreshArticleByUserIdList (@Param("userId") Long userId, @Param("articleId") Long articleId, @Param("queryIdList") List<Long> queryIdList, @Param("start") int start, @Param("end") int end);

    List<ArticleDto> refreshArticleListLatest (@Param("userId") Long userId, @Param("articleId") Long articleId, @Param("start") int start, @Param("end") int end);

    List<ArticleDto> refreshArticleListHot (@Param("userId") Long userId, @Param("articleId") Long articleId, @Param("start") int start, @Param("end") int end);

    List<ArticleDto> refreshArticleByUserId (@Param("userId") Long userId, @Param("queryId") Long queryId, @Param("articleId") Long articleId, @Param("start") int start, @Param("end") int end);

    List<ArticleDto> selectArticleByHistory(@Param("userId") Long userId, @Param("start") int start, @Param("end") int end);

    ArticleDto selectArticleById(@Param("userId") Long userId, @Param("articleId") Long articleId);
}
