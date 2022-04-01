package com.nsu.stu.meet.model.dto;

import com.nsu.stu.meet.model.Comment;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CommentDto extends Comment {

    private UserBaseDto userBaseDto;

    private CommentStatusDto commentStatusDto;

    private List<CommentDto> commentDtoList;

    private Integer commentSum;

    private Integer likeSum;

    private Integer dislikeSum;


    @Override
    public Long getQueryId() {
        return this.getArticleId();
    }
}
