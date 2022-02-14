package com.nsu.stu.meet.dao.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nsu.stu.meet.common.util.OwnUtil;
import com.nsu.stu.meet.model.ArticleDislike;
import com.nsu.stu.meet.model.ArticleDislikeDto;

import java.util.List;


public interface ArticleDislikeBaseMapper extends BaseMapper<ArticleDislike> {
    String C_ARTICLE_DISLIKE_ID = "article_dislike_id";
    String C_ARTICLE_ID = "article_id";
    String C_USER_ID = "user_id";
    String C_GMT_CREATE = "gmt_create";
    String C_GMT_MODIFIED = "gmt_modified";
    String C_IS_DELETED = "is_deleted";

    default IPage<ArticleDislike> findPage(ArticleDislikeDto condition, int currentPage, int pageSize) {
        QueryWrapper<ArticleDislike> query = generateQuery(condition);
        IPage<ArticleDislike> page = new Page<>(currentPage, pageSize);
        return selectPage(page, query);
    }


    default IPage<ArticleDislikeDto> findPageDto(ArticleDislikeDto condition, int currentPage, int pageSize) {
        IPage<ArticleDislike> results = findPage(condition, currentPage, pageSize);
        return OwnUtil.transIPageRecords2T(results, ArticleDislikeDto.class);
    }

    /**
     * 按条件查询的基本方法
     * @param condition 传入的参数
     * @return 返回Application列表
     */
    default List<ArticleDislike> findByCondition(ArticleDislikeDto condition) {
        return findByCondition(condition, null, null);
    }

    default List<ArticleDislikeDto> findByConditionDto(ArticleDislikeDto condition) {
        return findByConditionDto(condition, null, null);
    }
    default List<ArticleDislikeDto> findByConditionDto(ArticleDislikeDto condition, Integer currentPage, Integer pageSize) {
        return OwnUtil.copy2Ts(findByCondition(condition, currentPage, pageSize), ArticleDislikeDto.class);
    }


    default List<ArticleDislike> findByCondition(ArticleDislikeDto condition, Integer currentPage, Integer pageSize) {
        QueryWrapper<ArticleDislike> query = generateQuery(condition);
        if (currentPage != null && pageSize != null) {
            IPage<ArticleDislike> page = new Page<>(currentPage, pageSize);
            return selectPage(page, query).getRecords();
        }
        return selectList(query);
    }

    default QueryWrapper<ArticleDislike> generateQuery(ArticleDislikeDto condition) {
        if (condition == null) {
            return null;
        }
        QueryWrapper<ArticleDislike> query = new QueryWrapper<>();
        defaultQuery(condition, query);
        customizeQuery(condition, query);
        return query;
    }
    default void defaultQuery(ArticleDislikeDto condition, QueryWrapper<ArticleDislike> query) {
        query.eq(condition.getUserId()!=null, C_USER_ID, condition.getUserId());
        query.eq(condition.getArticleId()!=null, C_ARTICLE_ID, condition.getArticleId());
        query.eq(condition.getArticleDislikeId()!=null, C_ARTICLE_DISLIKE_ID, condition.getArticleDislikeId());
        query.eq(condition.getGmtCreate()!=null, C_GMT_CREATE, condition.getGmtCreate());
        query.eq(condition.getGmtModified()!=null, C_GMT_MODIFIED, condition.getGmtModified());
        query.eq(condition.getIsDeleted()!=null, C_IS_DELETED, condition.getIsDeleted());
    }

    /**
     * 自定义查询语句方法,参照defaultQuery
     * @param condition 传入的参数
     * @param query 查询构造对象
     */
    void customizeQuery(ArticleDislikeDto condition, QueryWrapper<ArticleDislike> query);
}
