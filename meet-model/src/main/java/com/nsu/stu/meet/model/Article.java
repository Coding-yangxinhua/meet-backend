package com.nsu.stu.meet.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.nsu.stu.meet.model.enums.LimitEnums;
import com.nsu.stu.meet.model.enums.RelationEnums;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * mt_article
 * @author 
 */
@Getter
@Setter
@TableName("mt_article")
public class Article extends BaseModel {
    /**
     * 文章唯一标识
     */
    @TableId(type = IdType.ASSIGN_ID)
    @JsonFormat(shape= JsonFormat.Shape.STRING)
    private Long articleId;


    /**
     * 根id
     */
    private Long rootId;

    /**
     * 文章父id
     */
    private Long parentId;
    /**
     * 文章父用户
     */
    private Long parentUserId;
    /**
     * 文章父用户昵称
     */
    private String parentUserNickname;

    /**
     * 文章父文章内容
     */
    private String parentContent;


    /**
     * 文章发布用户
     */
    private Long userId;

    /**
     * 访问权限
     */
    private LimitEnums limitId;


    /**
     * 文章内容
     */
    @Length(max = 4095)
    private String content;

    @Length(max = 4095)
    private String picUrls;

    private static final long serialVersionUID = 1L;
}