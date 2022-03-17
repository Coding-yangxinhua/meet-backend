package com.nsu.stu.meet.model;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.nsu.stu.meet.model.enums.GenderEnums;
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
    private String nickname;

    /**
     * 用户手机号
     */

    private String mobile;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户性别 0-男 1-女
     */
    private GenderEnums gender;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 用户简介
     */
    @Length(max = 511)
    private String intro;

    /**
     * 用户生日
     */
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
