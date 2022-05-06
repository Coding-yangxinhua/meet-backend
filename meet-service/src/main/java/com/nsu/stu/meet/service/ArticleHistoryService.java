package com.nsu.stu.meet.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nsu.stu.meet.model.ArticleHistory;

import java.util.List;

/**
* @author Xinhua X Yang
* @description 针对表【mt_article_history】的数据库操作Service
* @createDate 2022-05-06 14:22:55
*/
public interface ArticleHistoryService extends IService<ArticleHistory> {
    ArticleHistory getHistoryByUidAndArticleId(Long userId, Long articleId);

    void setArticleHistory(Long userId, Long articleId);
}
