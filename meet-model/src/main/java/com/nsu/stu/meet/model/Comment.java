package com.nsu.stu.meet.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * mt_article_comment
 * @author 
 */
@Getter
@Setter
@TableName("mt_comment")
public class Comment extends BaseModel {
    /**
     * 评论id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long commentId;

    /**
     * 一级评论id
     */
    private Long firstId;

    /**
     * 二级评论id
     */
    private Long secondId;

    /**
     * 二级评论id
     */
    private Long replyUserId;
    /**
     * 评论的文章id
     */
    private Long articleId;

    /**
     * 评论人id
     */
    private Long userId;

    /**
     * 评论内容
     */
    @Length(max = 511)
    private String content;


    private static final long serialVersionUID = 1L;
}