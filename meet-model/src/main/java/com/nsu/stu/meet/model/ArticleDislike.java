package com.nsu.stu.meet.model;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.nsu.stu.meet.common.base.BaseModel;
import lombok.Getter;
import lombok.Setter;

/**
 * mt_article_like
 * @author 
 */
@Getter
@Setter
@TableName("mt_article_dislike")
public class ArticleDislike extends BaseModel {
    /**
     * 文章点踩id
     */
    @TableId(type = IdType.ASSIGN_ID)
    @JsonFormat(shape= JsonFormat.Shape.STRING)
    private Long articleDislikeId;

    /**
     * 点踩人id
     */
    @TableField(value = "`user_id`")
    private Long userId;

    /**
     * 点踩文章id
     */
    @TableField(value = "`article_id`")
    private Long articleId;

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