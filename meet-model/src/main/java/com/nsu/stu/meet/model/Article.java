package com.nsu.stu.meet.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * mt_article
 * @author 
 */
@Getter
@Setter
@TableName("mt_article")
public class Article extends BaseModel {
    /**
     * 文章唯一标识
     */
    @TableId(type = IdType.ASSIGN_ID)
    @JsonFormat(shape= JsonFormat.Shape.STRING)
    private Long articleId;

    /**
     * 文章父id
     */
    private Long parentId;

    /**
     * 文章发布用户
     */
    private Long userId;

    /**
     * 访问权限
     */
    private Long limitId;


    /**
     * 文章内容
     */
    @Length(max = 4095)
    private String content;

    @Length(max = 4095)
    private String picUrls;

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