package com.nsu.stu.meet.model.dto.user;

import com.nsu.stu.meet.model.enums.RelationEnums;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserBaseDto {
    private Long userId;

    private String avatar;

    private String nickname;

    private RelationEnums relation;

    private String intro;

    private Integer fanSum;

    private Integer followSum;

    private Integer likeTotal;

    private Boolean isFan;

    private Boolean isFollow;

    private Boolean isSelf;
}
