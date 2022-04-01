package com.nsu.stu.meet.model;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

/**
 * mt_article_like
 * @author 
 */
@Getter
@Setter
@TableName("mt_article_history")
public class ArticleHistory extends BaseModel {
    /**
     * 文章浏览记录id
     */
    @TableId(type = IdType.ASSIGN_ID)
    @JsonFormat(shape= JsonFormat.Shape.STRING)
    private Long articleHistoryId;

    /**
     * 浏览人id
     */
    private Long userId;

    /**
     * 浏览文章id
     */
    private Long articleId;


    private static final long serialVersionUID = 1L;
}