package com.nsu.stu.meet.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nsu.stu.meet.model.RelationLimit;
import com.nsu.stu.meet.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface RelationLimitMapper extends BaseMapper<RelationLimit> {
    List<RelationLimit> getLimitByUser(@Param("srcId") Long srcId, @Param("destId") Long destId);

}
