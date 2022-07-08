package com.nsu.stu.meet.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nsu.stu.meet.common.base.JwtStorage;
import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.model.dto.ChatDto;
import com.nsu.stu.meet.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Xinhua X Yang
 */
@RestController
@RequestMapping("chat")
public class ChatController {

    @Autowired
    public ChatService chatService;

    @GetMapping(value = "/list", params = {"page", "size"})
    public ResponseEntity<IPage<ChatDto>> list(int page, int size) {
        Long userId = JwtStorage.userId();
        return chatService.list(userId, page, size);
    }


}