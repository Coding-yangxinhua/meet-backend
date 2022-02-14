package com.nsu.stu.meet.dao.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nsu.stu.meet.common.util.OwnUtil;
import com.nsu.stu.meet.model.Article;
import com.nsu.stu.meet.model.ArticleDto;
import org.springframework.util.StringUtils;

import java.util.List;


public interface ArticleBaseMapper extends BaseMapper<Article> {
    String C_ARTICLE_ID = "article_id";
    String C_PARENT_ID = "parent_id";
    String C_USER_ID = "user_id";
    String C_VIEW_LIMIT_ID = "view_limit_id";
    String C_VIEW_LIMIT_DESC = "view_limit_desc";
    String C_CONTENT = "content";
    String C_GMT_CREATE = "gmt_create";
    String C_GMT_MODIFIED = "gmt_modified";
    String C_IS_DELETED = "is_deleted";

    default IPage<Article> findPage(ArticleDto condition, int currentPage, int pageSize) {
        QueryWrapper<Article> query = generateQuery(condition);
        IPage<Article> page = new Page<>(currentPage, pageSize);
        return selectPage(page, query);
    }


    default IPage<ArticleDto> findPageDto(ArticleDto condition, int currentPage, int pageSize) {
        IPage<Article> results = findPage(condition, currentPage, pageSize);
        return OwnUtil.transIPageRecords2T(results, ArticleDto.class);
    }

    /**
     * 按条件查询的基本方法
     * @param condition 传入的参数
     * @return 返回Application列表
     */
    default List<Article> findByCondition(ArticleDto condition) {
        return findByCondition(condition, null, null);
    }

    default List<ArticleDto> findByConditionDto(ArticleDto condition) {
        return findByConditionDto(condition, null, null);
    }
    default List<ArticleDto> findByConditionDto(ArticleDto condition, Integer currentPage, Integer pageSize) {
        return OwnUtil.copy2Ts(findByCondition(condition, currentPage, pageSize), ArticleDto.class);
    }


    default List<Article> findByCondition(ArticleDto condition, Integer currentPage, Integer pageSize) {
        QueryWrapper<Article> query = generateQuery(condition);
        if (currentPage != null && pageSize != null) {
            IPage<Article> page = new Page<>(currentPage, pageSize);
            return selectPage(page, query).getRecords();
        }
        return selectList(query);
    }

    default QueryWrapper<Article> generateQuery(ArticleDto condition) {
        if (condition == null) {
            return null;
        }
        QueryWrapper<Article> query = new QueryWrapper<>();
        defaultQuery(condition, query);
        customizeQuery(condition, query);
        return query;
    }
    default void defaultQuery(ArticleDto condition, QueryWrapper<Article> query) {
        query.eq(condition.getUserId()!=null, C_USER_ID, condition.getUserId());
        query.eq(condition.getArticleId()!=null, C_ARTICLE_ID, condition.getArticleId());
        query.eq(condition.getParentId()!=null, C_PARENT_ID, condition.getParentId());
        query.eq(StringUtils.hasText(condition.getContent()), C_CONTENT, condition.getContent());
        query.eq(condition.getViewLimitId()!=null, C_VIEW_LIMIT_ID, condition.getViewLimitId());
        query.eq(StringUtils.hasText(condition.getViewLimitDesc()), C_VIEW_LIMIT_DESC, condition.getViewLimitDesc());
        query.eq(condition.getGmtCreate()!=null, C_GMT_CREATE, condition.getGmtCreate());
        query.eq(condition.getGmtModified()!=null, C_GMT_MODIFIED, condition.getGmtModified());
        query.eq(condition.getIsDeleted()!=null, C_IS_DELETED, condition.getIsDeleted());
    }

    /**
     * 自定义查询语句方法,参照defaultQuery
     * @param condition 传入的参数
     * @param query 查询构造对象
     */
    void customizeQuery(ArticleDto condition, QueryWrapper<Article> query);
}
