package com.nsu.stu.meet.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nsu.stu.meet.common.base.JwtStorage;
import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.common.util.OwnUtil;
import com.nsu.stu.meet.model.Chat;
import com.nsu.stu.meet.model.Message;
import com.nsu.stu.meet.model.dto.MessageDto;
import com.nsu.stu.meet.service.ChatService;
import com.nsu.stu.meet.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * @author Xinhua X Yang
 */
@RestController
@RequestMapping("chat/message")
public class MessageController {

    @Autowired
    public SimpMessagingTemplate template;

    @Autowired
    public MessageService messageService;

    @Autowired
    public ChatService chatService;

    @GetMapping(value = "/list", params = {"destId", "page", "size"})
    public ResponseEntity<IPage<MessageDto>> list(Long destId, int page, int size) {
        Long srcId = JwtStorage.userId();
        return messageService.list(srcId, destId, page, size);
    }

    @ResponseBody
    @PostMapping(value = "/pushToAll")
    public void pushToAll( @RequestBody Message msg) {
        template.convertAndSend("/topic/all", msg);
    }

    @ResponseBody
    @PostMapping(value = "/user")
    public ResponseEntity<MessageDto> pushToOne( @RequestBody MessageDto messageDto) {
        Long srcId = JwtStorage.userId();
        Long destId = messageDto.getDestId();
        messageDto.setSrcId(srcId);
        // 查看是否存在对应房间
        Chat srcChat = chatService.getChatBySrcAndDest(srcId, destId);
        Chat destChat = chatService.getChatBySrcAndDest(destId, srcId);
        Chat newSrcChat = new Chat();
        Chat newDestChat = new Chat();
        // 设置源房间参数
        if (srcChat != null){
            newSrcChat.setChatId(srcChat.getChatId());
        }
        newSrcChat.setContent(messageDto.getContent());
        newSrcChat.setSrcId(srcId);
        newSrcChat.setDestId(destId);
        // 设置目标房间参数
        if (destChat != null){
            newDestChat.setChatId(destChat.getChatId());
        }
        newDestChat.setContent(messageDto.getContent());
        newDestChat.setSrcId(destId);
        newDestChat.setDestId(srcId);
        chatService.createOrUpdateChat(Arrays.asList(newSrcChat, newDestChat));
        messageService.save(messageDto);
        String path = OwnUtil.concatString(':', destId, srcId);
        template.convertAndSendToUser(path, "/message", messageDto);
        return ResponseEntity.ok(messageDto);
    }
}