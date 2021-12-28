package com.nsu.stu.meet.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.nsu.stu.meet.common.base.BaseModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * mt_album
 * @author 
 */
@Getter
@Setter
@TableName("mt_album")
public class Album extends BaseModel {
    /**
     * 相册id
     */
    @TableId(type = IdType.ASSIGN_ID)
    @JsonFormat(shape= JsonFormat.Shape.STRING)
    private Long albumId;

    /**
     * 用户id
     */
    @TableField(value = "`user_id`")
    private Long userId;

    /**
     * 访问权限
     */
    @TableField(value = "`permission_id`")
    private Long permissionId;

    /**
     * 相册名称
     */
    @TableField(value = "`title`")
    private String title;

    /**
     * 相册封面
     */
    @TableField(value = "`cover`")
    private String cover;

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