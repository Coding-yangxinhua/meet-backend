package com.nsu.stu.meet.model;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.nsu.stu.meet.model.enums.LimitEnums;
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
@TableName("mt_relation_limit")
public class RelationLimit extends BaseModel {
    /**
     * 相册id
     */
    @TableId(type = IdType.ASSIGN_ID)
    @JsonFormat(shape= JsonFormat.Shape.STRING)
    private Long relationLimitId;

    /**
     * 关系id
     */
    private Long relationStatus;

    /**
     * 访问权限
     */
    private LimitEnums limitId;



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