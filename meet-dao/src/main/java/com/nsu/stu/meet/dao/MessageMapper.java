package com.nsu.stu.meet.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nsu.stu.meet.model.Message;
import com.nsu.stu.meet.model.dto.MessageDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface MessageMapper extends BaseMapper<Message> {
    List<MessageDto> list(@Param("srcId") Long srcId, @Param("destId") Long destId, @Param("start") int start, @Param("end") int end);
}
