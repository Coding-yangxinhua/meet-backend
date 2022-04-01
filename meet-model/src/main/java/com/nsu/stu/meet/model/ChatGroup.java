package com.nsu.stu.meet.model;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;

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


    private static final long serialVersionUID = 1L;
}