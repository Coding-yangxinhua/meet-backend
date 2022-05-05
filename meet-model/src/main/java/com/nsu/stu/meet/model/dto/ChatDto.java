package com.nsu.stu.meet.model.dto;

import com.nsu.stu.meet.model.Chat;
import com.nsu.stu.meet.model.dto.user.UserBaseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatDto extends Chat {
    private UserBaseDto user;

    private String content;

}
