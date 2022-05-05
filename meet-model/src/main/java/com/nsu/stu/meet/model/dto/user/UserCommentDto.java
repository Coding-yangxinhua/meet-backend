package com.nsu.stu.meet.model.dto.user;

import com.nsu.stu.meet.model.CheckModel;
import lombok.Data;

@Data
public class UserCommentDto extends CheckModel {
    private Long userId;

    private String avatar;

    private String nickname;

    @Override
    public Long getQueryId() {
        return userId;
    }
}
