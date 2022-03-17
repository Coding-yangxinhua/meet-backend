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
    @JsonFormat(shape= JsonFormat.Shape.STRING)
    private Long commentId;

    /**
     * 评论父id
     */
    @TableField(value = "`parent_id`")
    private Long parentId;

    /**
     * 评论的文章id
     */
    @TableField(value = "`article_id`")
    private Long articleId;

    /**
     * 评论人id
     */
    @TableField(value = "`user_id`")
    private Long userId;

    /**
     * 评论内容
     */
    @Length(max = 511)
    @TableField(value = "`content`")
    private String content;

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