package com.nsu.stu.meet.model;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * mt_article
 * @author 
 */
@Getter
@Setter
@TableName("mt_article_status")
public class ArticleStatus extends BaseModel {
    /**
     * 文章唯一标识
     */
    @TableId(type = IdType.ASSIGN_ID)
    @JsonFormat(shape= JsonFormat.Shape.STRING)
    private Long articleStatusId;

    /**
     * 文章父id
     */
    private Long userId;

    /**
     * 文章发布用户
     */
    private Long articleId;

    /**
     * 是否点赞
     */
    private Integer likeStatus;


    /**
     * 是否收藏
     */
    private Integer starStatus;

    /**
     * 是否点踩
     */
    private Integer dislikeStatus;

    /**
     * 是否转发
     */
    private Integer repostStatus;


    private static final long serialVersionUID = 1L;
}