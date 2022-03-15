package com.nsu.stu.meet.model;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.nsu.stu.meet.common.base.BaseModel;
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
    private Long likeStatus;


    /**
     * 是否收藏
     */
    private Long starStatus;

    /**
     * 是否点踩
     */
    private Long dislikeStatus;

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