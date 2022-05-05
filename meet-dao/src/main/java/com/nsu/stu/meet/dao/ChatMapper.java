package com.nsu.stu.meet.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nsu.stu.meet.model.Chat;
import com.nsu.stu.meet.model.dto.ChatDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface ChatMapper extends BaseMapper<Chat> {
    List<ChatDto> list(@Param("userId") Long userId, @Param("start") int start, @Param("end") int end);

}
