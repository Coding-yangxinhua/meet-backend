package com.nsu.stu.meet.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.model.Chat;
import com.nsu.stu.meet.model.dto.ChatDto;

import java.util.List;

/**
* @author Xinhua X Yang
* @description 针对表【mt_chat】的数据库操作Service
* @createDate 2022-05-05 11:45:55
*/
public interface ChatService extends IService<Chat> {
    ResponseEntity<IPage<ChatDto>> list(Long userId, Integer page, Integer size);

    Chat getChatBySrcAndDest(Long srcId, Long destId);

    void createOrUpdateChat(List<Chat> chats);


}
