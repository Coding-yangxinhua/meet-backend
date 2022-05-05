package com.nsu.stu.meet.model.dto;

import com.alibaba.fastjson.JSON;
import com.nsu.stu.meet.model.Article;
import com.nsu.stu.meet.model.dto.user.UserBaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ArticleDto extends Article {

    private UserBaseDto userBase;

    private ArticleStatusDto articleStatus;

    private int likeSum;

    private int dislikeSum;

    private int repostSum;

    private int starSum;

    private int commentSum;

    private List<String> picList;

    public List<String> getPicList() {
        String picUrlString = this.getPicUrls();
        return JSON.parseArray(picUrlString, String.class);
    }
}