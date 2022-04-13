package com.nsu.stu.meet.controller;

import com.google.common.eventbus.Subscribe;
import com.nsu.stu.meet.common.base.JwtStorage;
import com.nsu.stu.meet.model.Message;
import com.nsu.stu.meet.model.dto.ChatDto;
import com.tencentcloudapi.ccc.v20200210.models.MessageBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ChatController {

    @Autowired
    public SimpMessagingTemplate template;

    @ResponseBody
    @RequestMapping(value = "/pushToAll", method = RequestMethod.POST)
    public void pushToAll( @RequestBody Message msg) {
        template.convertAndSend("/topic/all", msg);
    }

    @ResponseBody
    @MessageMapping("/alone")
    @RequestMapping(value = "/pushToOne", method = RequestMethod.POST)
    public void pushToOne( @RequestBody Message msg) {
        template.convertAndSendToUser(msg.getDestId().toString(), "/message", msg);
    }
}