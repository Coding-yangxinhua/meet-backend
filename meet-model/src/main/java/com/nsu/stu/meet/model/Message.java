package com.nsu.stu.meet.model;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * mt_article_comment
 */
@Getter
@Setter
@TableName("mt_message")
public class Message extends BaseModel {
    /**
     * 聊天id
     */
    @TableId(type = IdType.ASSIGN_ID)
    @JsonFormat(shape= JsonFormat.Shape.STRING)
    private Long messageId;

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
     * 消息内容
     */
    @Length(max = 2047)
    @TableField(value = "`content`")
    private String content;

    /**
     * 状态 0-正常 1-删除 2-撤回
     */
    @TableField(value = "`status`")
    private Integer status;


    private static final long serialVersionUID = 1L;
}