package com.nsu.stu.meet.model;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.nsu.stu.meet.common.base.BaseModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("mt_user")
public class User extends BaseModel {
    /**
     * 用户唯一标识
     */
    @TableId(type = IdType.ASSIGN_ID)
    @JsonFormat(shape= JsonFormat.Shape.STRING)
    private Long userId;

    /**
     * 用户昵称
     */
    @TableField(value = "`nickname`")
    private String nickname;

    /**
     * 用户手机号
     */
    @TableField(value = "`mobile`")
    private String mobile;

    /**
     * 用户密码
     */
    @TableField(value = "`password`")
    private String password;

    /**
     * 用户性别 0-男 1-女
     */
    @TableField(value = "`gender`")
    private Integer gender;

    /**
     * 用户简介
     */
    @TableField(value = "`intro`")
    private String intro;

    /**
     * 用户生日
     */
    @TableField(value = "`birth`")
    private Long birth;

    /**
     * 创建时间
     */
    @TableField(value = "`gmt_create`",fill = FieldFill.INSERT)
    private Long gmtCreate;

    /**
     * 创建人
     */
    @TableField(value = "`create_user_name`",fill = FieldFill.INSERT)
    private String createUserName;

    /**
     * 修改时间
     */
    @TableField(value = "`gmt_modified`", fill = FieldFill.INSERT_UPDATE)
    private Long gmtModified;

    /**
     * 修改人
     */
    @TableField(value = "`modify_user_name`", fill = FieldFill.INSERT_UPDATE)
    private String modifyUserName;

    /**
     * 逻辑删除 0-未删除 1-删除
     */
    @TableField(value = "`is_deleted`", fill = FieldFill.INSERT)
    private Integer isDeleted;
}
