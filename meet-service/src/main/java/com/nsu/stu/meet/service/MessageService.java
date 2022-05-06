package com.nsu.stu.meet.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.model.Message;
import com.nsu.stu.meet.model.dto.MessageDto;

import java.util.List;

/**
* @author Xinhua X Yang
* @description 针对表【mt_message】的数据库操作Service
* @createDate 2022-05-05 14:38:18
*/
public interface MessageService extends IService<Message> {
    ResponseEntity<IPage<MessageDto>> list(Long srcId, Long destId, int page, int size);
}
