package com.nsu.stu.meet.model.dto;

import com.nsu.stu.meet.model.Album;
import com.nsu.stu.meet.model.ArticleStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleStatusDto extends ArticleStatus {

    @Override
    public Long getQueryId() {
        return this.getArticleId();
    }
}
