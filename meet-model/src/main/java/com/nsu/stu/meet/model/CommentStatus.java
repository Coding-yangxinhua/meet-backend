package com.nsu.stu.meet.model;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

/**
 * mt_article
 * @author 
 */
@Getter
@Setter
@TableName("mt_comment_status")
public class CommentStatus extends BaseModel {
    /**
     * 评论唯一标识
     */
    @TableId(type = IdType.ASSIGN_ID)
    @JsonFormat(shape= JsonFormat.Shape.STRING)
    private Long commentStatusId;

    /**
     * 评论状态用户
     */
    private Long userId;

    /**
     * 评论id
     */
    private Long commentId;

    /**
     * 是否点赞
     */
    private Integer likeStatus;

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