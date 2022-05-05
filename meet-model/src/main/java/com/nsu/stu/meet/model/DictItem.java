package com.nsu.stu.meet.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName mt_dict_item
 */
@TableName(value ="mt_dict_item")
@Data
public class DictItem extends BaseModel {
    /**
     * 字典主键
     */
    @TableId(type = IdType.AUTO)
    private Long dictId;

    /**
     * 字典类型
     */
    private Long typeId;

    /**
     * 字典标签
     */
    private String itemLabel;

    /**
     * 字典值
     */
    private String itemValue;

    /**
     * 顺序
     */
    private Integer order;

    /**
     * 是否启用
     */
    private Integer isEnable;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}