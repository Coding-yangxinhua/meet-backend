package com.nsu.stu.meet.model;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.nsu.stu.meet.common.base.BaseModel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * mt_article_comment
 * @author 
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
     * 目标id
     */
    @TableField(value = "`dest_id`")
    private Long destId;

    /**
     * 用户id
     */
    @TableField(value = "`src_id`")
    private Long srcId;

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