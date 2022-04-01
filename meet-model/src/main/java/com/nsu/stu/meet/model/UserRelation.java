package com.nsu.stu.meet.model;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.nsu.stu.meet.model.enums.RelationEnums;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Pattern;

@Getter
@Setter
@TableName("mt_user_relation")
public class UserRelation extends BaseModel {
    /**
     * 关系id
     */
    @TableId(type = IdType.ASSIGN_ID)
    @JsonFormat(shape= JsonFormat.Shape.STRING)
    private Long relationId;

    /**
     * 用户id
     */
    @TableField(value = "`src_id`")
    private Long srcId;

    /**
     * 目标用户id
     */
    @TableField(value = "`dest_id`")
    private Long destId;

    /**
     * 用户关系 -1 拉黑 1关注 2特别关心
     */
    @TableField(value = "`status`")
    private RelationEnums status;

}
