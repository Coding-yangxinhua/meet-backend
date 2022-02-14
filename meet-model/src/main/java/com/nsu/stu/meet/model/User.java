package com.nsu.stu.meet.model;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.nsu.stu.meet.common.base.BaseModel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;

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
    @Length(max = 15)
    @TableField(value = "`nickname`")
    private String nickname;

    /**
     * 用户手机号
     */
    @Pattern(regexp = "/^1(3[0-9]|4[01456879]|5[0-35-9]|6[2567]|7[0-8]|8[0-9]|9[0-35-9])\\d{8}$/")
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
    @Range(min = 0, max = 1)
    @TableField(value = "`gender`")
    private Integer gender;

    /**
     * 用户简介
     */
    @Length(max = 511)
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
     * 修改时间
     */
    @TableField(value = "`gmt_modified`", fill = FieldFill.INSERT_UPDATE)
    private Long gmtModified;

    /**
     * 逻辑删除 0-未删除 1-删除
     */
    @TableField(value = "`is_deleted`", fill = FieldFill.INSERT)
    private Integer isDeleted;

}
