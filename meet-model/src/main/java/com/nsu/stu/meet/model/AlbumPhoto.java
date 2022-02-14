package com.nsu.stu.meet.model;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.nsu.stu.meet.common.base.BaseModel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

/**
 * mt_album
 * @author 
 */
@Getter
@Setter
@TableName("mt_album_photo")
public class AlbumPhoto extends BaseModel {
    /**
     * 相册图片id
     */
    @TableId(type = IdType.ASSIGN_ID)
    @JsonFormat(shape= JsonFormat.Shape.STRING)
    private Long albumPhotoId;

    /**
     * 图片拥有者id
     */
    @TableField(value = "`user_id`")
    private Long userId;

    /**
     * 所属相册id
     */
    @TableField(value = "`album_id`")
    private Long albumId;

    /**
     * 相册封面
     */
    @URL
    @TableField(value = "`url`")
    private String url;

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