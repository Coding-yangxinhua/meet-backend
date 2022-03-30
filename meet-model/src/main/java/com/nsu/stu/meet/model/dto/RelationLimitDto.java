package com.nsu.stu.meet.model.dto;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@TableName("mt_relation_limit")
@Getter
@Setter
public class RelationLimitDto {
    /**
     * 关系id
     */
    private Integer relationStatus;

    /**
     * 访问权限
     */
    private String limitIdString;

    /**
     * 访问权限
     */
    private List<Integer> limitIds;

    public List<Integer> getLimitIds() {
        return Arrays.stream(limitIdString.split(",")).map(Integer::valueOf).collect(Collectors.toList());
    }

}
