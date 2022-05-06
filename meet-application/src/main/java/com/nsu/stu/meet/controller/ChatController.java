package com.nsu.stu.meet.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.eventbus.Subscribe;
import com.nsu.stu.meet.common.base.JwtStorage;
import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.common.enums.ArticleTypeEnums;
import com.nsu.stu.meet.model.Message;
import com.nsu.stu.meet.model.dto.ArticleDto;
import com.nsu.stu.meet.model.dto.ChatDto;
import com.nsu.stu.meet.service.ChatService;
import com.tencentcloudapi.ccc.v20200210.models.MessageBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("chat")
public class ChatController {

    @Autowired
    public ChatService chatService;

    @RequestMapping(value = "/list", method = RequestMethod.GET, params = {"page", "size"})
    public ResponseEntity<IPage<ChatDto>> list(int page, int size) {
        Long userId = JwtStorage.userId();
        return chatService.list(userId, page, size);
    }


}