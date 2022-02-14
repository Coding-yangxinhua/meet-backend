package com.nsu.stu.meet.model;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.nsu.stu.meet.common.base.BaseModel;
import lombok.Getter;
import lombok.Setter;

/**
 * mt_article_comment_Like
 * @author 
 */
@Getter
@Setter
@TableName("mt_comment_like")
public class CommentLike extends BaseModel {
    /**
     * 评论点赞id
     */
    @TableId(type = IdType.ASSIGN_ID)
    @JsonFormat(shape= JsonFormat.Shape.STRING)
    private Long commentLikeId;

    /**
     * 点赞人id
     */
    @TableField(value = "`user_id`")
    private Long userId;

    /**
     * 点赞评论id
     */
    @TableField(value = "`comment_id`")
    private Long commentId;

    /**
     * 创建时间
     */
    @TableField(value = "`gmt_create`",fill = FieldFill.INSERT)
    private Long gmtCreate;

    /**
     * 修改时间
     */
    @TableField(value = "`gmt_modified`", fill = FieldFill.INSERT_UPDATE)
    private Long gmtModified;

    /**
     * 逻辑删除 0-未删除 1-删除
     */
    @TableField(value = "`is_deleted`", fill = FieldFill.INSERT)
    private Integer isDeleted;

    private static final long serialVersionUID = 1L;
}