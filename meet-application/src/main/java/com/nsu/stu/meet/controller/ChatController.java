package com.nsu.stu.meet.controller;

import org.springframework.stereotype.Component;

import javax.websocket.server.ServerEndpoint;

@Component
@ServerEndpoint("/wx/{fromOpenid}/{toOpenid}")
public class ChatController {
}
