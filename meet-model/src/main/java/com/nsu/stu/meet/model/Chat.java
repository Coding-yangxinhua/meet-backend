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
@TableName("mt_chat")
public class Chat extends BaseModel {
    /**
     * 聊天id
     */
    @TableId(type = IdType.ASSIGN_ID)
    @JsonFormat(shape= JsonFormat.Shape.STRING)
    private Long chatId;

    /**
     * 用户id
     */
    @TableField(value = "`src_id`")
    private Long srcId;

    /**
     * 目标id
     */
    @TableField(value = "`dest_id`")
    private Long destId;

    /**
     * 房间类型 0-用户 1-群聊
     */
    @TableField(value = "`type`")
    private Integer type;

    /**
     * 最新消息
     */
    @TableField(value = "`content`")
    private String content;

    /**
     * 状态 0-正常接收 1-接收不提醒 2-屏蔽
     */
    @TableField(value = "`status`")
    private Integer status;

    /**
     * 是否置顶 0-不指定 1-置顶
     */
    @TableField(value = "`is_top`")
    private Integer isTop;
    /**
     * 评论内容
     */
    @TableField(value = "`is_hide`")
    private Integer isHide;

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