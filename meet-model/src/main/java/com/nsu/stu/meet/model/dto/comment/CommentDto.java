package com.nsu.stu.meet.model.dto.comment;

import com.nsu.stu.meet.model.Comment;
import com.nsu.stu.meet.model.dto.CommentStatusDto;
import com.nsu.stu.meet.model.dto.user.UserCommentDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CommentDto extends Comment {

    private Long parentId;

    private UserCommentDto user;

    private UserCommentDto replyUser;

    private CommentStatusDto commentStatus;

    private List<CommentBaseDto> childrenComments;

    private Integer commentSum;

    private Integer likeSum;

    private Integer dislikeSum;


    @Override
    public Long getQueryId() {
        return this.getArticleId();
    }
}
