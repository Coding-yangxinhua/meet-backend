package com.nsu.stu.meet.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nsu.stu.meet.dao.ArticleHistoryMapper;
import com.nsu.stu.meet.model.ArticleHistory;
import com.nsu.stu.meet.service.ArticleHistoryService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Xinhua X Yang
* @description 针对表【mt_article_history】的数据库操作Service实现
* @createDate 2022-05-06 14:22:55
*/
@Service
public class ArticleHistoryServiceImpl extends ServiceImpl<ArticleHistoryMapper, ArticleHistory>
    implements ArticleHistoryService {

    @Override
    public ArticleHistory getHistoryByUidAndArticleId(Long userId, Long articleId) {
        LambdaQueryWrapper<ArticleHistory> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ArticleHistory::getUserId, userId).eq(ArticleHistory::getArticleId, articleId);
        return baseMapper.selectOne(lambdaQueryWrapper);
    }

    @Override
    public void setArticleHistory(Long userId, Long articleId) {
        ArticleHistory articleHistory = this.getHistoryByUidAndArticleId(userId, articleId);
        if (articleHistory == null) {
            articleHistory = new ArticleHistory();
            articleHistory.setUserId(userId);
            articleHistory.setArticleId(articleId);
        }
        this.saveOrUpdate(articleHistory);
    }

}




