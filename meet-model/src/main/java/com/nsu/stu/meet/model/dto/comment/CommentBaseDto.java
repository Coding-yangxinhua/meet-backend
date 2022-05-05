package com.nsu.stu.meet.model.dto.comment;

import com.nsu.stu.meet.model.dto.user.UserCommentDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentBaseDto {

    private Long firstId;

    private Long commentId;

    private String content;

    private UserCommentDto user;
}
