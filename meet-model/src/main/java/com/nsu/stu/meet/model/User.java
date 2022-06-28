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
     * 用户背景图
     */
    private String background;

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
     * 被赞总数
     */
    private Integer likeTotal;

}
