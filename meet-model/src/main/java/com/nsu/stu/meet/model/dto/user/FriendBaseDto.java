package com.nsu.stu.meet.model.dto.user;

import com.nsu.stu.meet.model.enums.RelationEnums;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class FriendBaseDto implements Serializable {
    private Long userId;

    private String avatar;

    private String nickname;

    private String intro;

    private Boolean isFan;

    private Boolean isFollow;

    private String background;

}
