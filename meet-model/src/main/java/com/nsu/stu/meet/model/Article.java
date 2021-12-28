package com.nsu.stu.meet.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.nsu.stu.meet.common.base.BaseModel;
import lombok.Data;

/**
 * mt_article
 * @author 
 */
@Data
public class Article extends BaseModel {
    /**
     * 文章唯一标识
     */
    @TableId(type = IdType.ASSIGN_ID)
    @JsonFormat(shape= JsonFormat.Shape.STRING)
    private Long article_id;

    /**
     * 文章发布用户
     */
    @TableField(value = "`user_id`")
    private Long user_id;

    /**
     * 访问权限
     */
    @TableField(value = "`permission_id`")
    private Long permissionId;

    /**
     * 文章内容
     */
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