package com.nsu.stu.meet.model;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.nsu.stu.meet.common.base.BaseModel;
import lombok.Getter;
import lombok.Setter;

/**
 * mt_article_comment
 * @author 
 */
@Getter
@Setter
@TableName("mt_chat_group")
public class ChatGroup extends BaseModel {
    /**
     * 聊天id
     */
    @TableId(type = IdType.ASSIGN_ID)
    @JsonFormat(shape= JsonFormat.Shape.STRING)
    private Long chatGroupId;

    /**
     * 用户id
     */
    @TableField(value = "`name`")
    private String name;

    /**
     * 目标id
     */
    @TableField(value = "`cover`")
    private String cover;

    /**
     * 房间类型 0-用户 1-群聊
     */
    @TableField(value = "`master_id`")
    private Long masterId;

    /**
     * 状态 0-正常接收 1-接收不提醒 2-屏蔽
     */
    @TableField(value = "`manager_id`")
    private Long managerId;

    /**
     * 是否置顶 0-不指定 1-置顶
     */
    @TableField(value = "`desc`")
    private String desc;

    /**
     *
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