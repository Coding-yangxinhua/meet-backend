package com.nsu.stu.meet.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.nsu.stu.meet.common.base.BaseModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * mt_article_star
 * @author
 */
@Getter
@Setter
@TableName("mt_article_star")
public class ArticleStar extends BaseModel {
    /**
     * 文章收藏 id
     */
    @TableId(type = IdType.ASSIGN_ID)
    @JsonFormat(shape= JsonFormat.Shape.STRING)
    private Long articleStarId;

    /**
     * 收藏人id
     */
    @TableField(value = "`user_id`")
    private Long userId;

    /**
     * 收藏文章id
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