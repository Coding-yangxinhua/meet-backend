package com.nsu.stu.meet.dao.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nsu.stu.meet.common.util.OwnUtil;
import com.nsu.stu.meet.model.ArticleLike;
import com.nsu.stu.meet.model.ArticleLikeDto;

import java.util.List;


public interface ArticleLikeBaseMapper extends BaseMapper<ArticleLike> {
    String C_ARTICLE_LIKE_ID = "article_like_id";
    String C_ARTICLE_ID = "article_id";
    String C_USER_ID = "user_id";
    String C_GMT_CREATE = "gmt_create";
    String C_GMT_MODIFIED = "gmt_modified";
    String C_IS_DELETED = "is_deleted";

    default IPage<ArticleLike> findPage(ArticleLikeDto condition, int currentPage, int pageSize) {
        QueryWrapper<ArticleLike> query = generateQuery(condition);
        IPage<ArticleLike> page = new Page<>(currentPage, pageSize);
        return selectPage(page, query);
    }


    default IPage<ArticleLikeDto> findPageDto(ArticleLikeDto condition, int currentPage, int pageSize) {
        IPage<ArticleLike> results = findPage(condition, currentPage, pageSize);
        return OwnUtil.transIPageRecords2T(results, ArticleLikeDto.class);
    }

    /**
     * 按条件查询的基本方法
     * @param condition 传入的参数
     * @return 返回Application列表
     */
    default List<ArticleLike> findByCondition(ArticleLikeDto condition) {
        return findByCondition(condition, null, null);
    }

    default List<ArticleLikeDto> findByConditionDto(ArticleLikeDto condition) {
        return findByConditionDto(condition, null, null);
    }
    default List<ArticleLikeDto> findByConditionDto(ArticleLikeDto condition, Integer currentPage, Integer pageSize) {
        return OwnUtil.copy2Ts(findByCondition(condition, currentPage, pageSize), ArticleLikeDto.class);
    }


    default List<ArticleLike> findByCondition(ArticleLikeDto condition, Integer currentPage, Integer pageSize) {
        QueryWrapper<ArticleLike> query = generateQuery(condition);
        if (currentPage != null && pageSize != null) {
            IPage<ArticleLike> page = new Page<>(currentPage, pageSize);
            return selectPage(page, query).getRecords();
        }
        return selectList(query);
    }

    default QueryWrapper<ArticleLike> generateQuery(ArticleLikeDto condition) {
        if (condition == null) {
            return null;
        }
        QueryWrapper<ArticleLike> query = new QueryWrapper<>();
        defaultQuery(condition, query);
        customizeQuery(condition, query);
        return query;
    }
    default void defaultQuery(ArticleLikeDto condition, QueryWrapper<ArticleLike> query) {
        query.eq(condition.getUserId()!=null, C_USER_ID, condition.getUserId());
        query.eq(condition.getArticleId()!=null, C_ARTICLE_ID, condition.getArticleId());
        query.eq(condition.getArticleLikeId()!=null, C_ARTICLE_LIKE_ID, condition.getArticleLikeId());
        query.eq(condition.getGmtCreate()!=null, C_GMT_CREATE, condition.getGmtCreate());
        query.eq(condition.getGmtModified()!=null, C_GMT_MODIFIED, condition.getGmtModified());
        query.eq(condition.getIsDeleted()!=null, C_IS_DELETED, condition.getIsDeleted());
    }

    /**
     * 自定义查询语句方法,参照defaultQuery
     * @param condition 传入的参数
     * @param query 查询构造对象
     */
    void customizeQuery(ArticleLikeDto condition, QueryWrapper<ArticleLike> query);
}
