package com.nsu.stu.meet.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nsu.stu.meet.common.base.JwtStorage;
import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.model.Message;
import com.nsu.stu.meet.model.dto.ChatDto;
import com.nsu.stu.meet.model.dto.MessageDto;
import com.nsu.stu.meet.service.ChatService;
import com.nsu.stu.meet.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("chat/message")
public class MessageController {

    @Autowired
    public SimpMessagingTemplate template;

    @Autowired
    public MessageService messageService;

    @RequestMapping(value = "/list", method = RequestMethod.GET, params = {"destId", "page", "size"})
    public ResponseEntity<IPage<MessageDto>> list(Long destId, int page, int size) {
        Long srcId = JwtStorage.userId();
        return messageService.list(srcId, destId, page, size);
    }
}