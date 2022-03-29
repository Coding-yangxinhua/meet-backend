package com.nsu.stu.meet.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nsu.stu.meet.model.Article;
import com.nsu.stu.meet.model.RelationLimit;
import com.nsu.stu.meet.model.dto.AlbumDto;
import com.nsu.stu.meet.model.dto.ArticleDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface ArticleMapper extends BaseMapper<Article> {
    List<ArticleDto> selectArticleByUserIdList (@Param("userId") Long userId, @Param("queryIdList") List<Long> queryIdList, @Param("start") int start, @Param("end") int end);

    List<ArticleDto> selectArticleListLatest (@Param("userId") Long userId, @Param("start") int start, @Param("end") int end);
    List<ArticleDto> selectArticleListHot (@Param("userId") Long userId, @Param("start") int start, @Param("end") int end);

}
