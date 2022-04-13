package com.nsu.stu.meet.configuration;

import com.alibaba.nacos.common.utils.StringUtils;
import com.nsu.stu.meet.common.base.IllegalParamException;
import com.nsu.stu.meet.common.base.JwtStorage;
import com.nsu.stu.meet.common.util.OwnUtil;
import org.springframework.core.Ordered;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.List;

@Component
public class AuthChannelInterceptor implements ChannelInterceptor {

    /**
     * 连接前监听
     *
     * @param message
     * @param channel
     * @return
     */
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        Long userId = JwtStorage.userId();
        String dest = OwnUtil.concatString('/', "/user", userId, "message");
        if (accessor != null && StompCommand.SUBSCRIBE.equals(accessor.getCommand())) {
            String simpDestination = String.valueOf(message.getHeaders().get("simpDestination"));
            if (!simpDestination.equals(dest)) {
               throw new IllegalParamException("非法参数");
            }
        }
        return message;
    }


}