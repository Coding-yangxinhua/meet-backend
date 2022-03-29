package com.nsu.stu.meet.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nsu.stu.meet.model.ArticleStatus;
import com.nsu.stu.meet.model.Chat;
import com.nsu.stu.meet.model.dto.ArticleStatusDto;
import org.apache.ibatis.annotations.Param;


public interface ArticleStatusMapper extends BaseMapper<ArticleStatus> {
    ArticleStatusDto selectArticleStatus(@Param("userId") Long userId, @Param("articleId") Long articleId);
}
