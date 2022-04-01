package com.nsu.stu.meet.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.nsu.stu.meet.model.enums.LimitEnums;
import lombok.Data;
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
@TableName("mt_album")
public class Album extends BaseModel {
    /**
     * 相册id
     */
    @TableId(type = IdType.ASSIGN_ID)
    @JsonFormat(shape= JsonFormat.Shape.STRING)
    private Long albumId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 访问权限
     */
    private LimitEnums limitId;



    /**
     * 相册名称
     */
    @Length(max = 15)
    private String title;

    /**
     * 相册封面
     */
    @URL
    private String cover;



    private static final long serialVersionUID = 1L;
}