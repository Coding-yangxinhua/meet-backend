package com.nsu.stu.meet.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserBaseDto {
    private Long userId;

    private String avatar;

    private String nickname;
}
