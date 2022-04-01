package com.nsu.stu.meet.model.dto;

import com.nsu.stu.meet.model.Article;
import com.nsu.stu.meet.model.ArticleStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleDto extends Article {

    private UserBaseDto userBaseDto;

    private ArticleStatusDto articleStatusDto;

    private int likeSum;

    private int dislikeSum;

    private int repostSum;

    private int starSum;

    private int commentSum;


}
