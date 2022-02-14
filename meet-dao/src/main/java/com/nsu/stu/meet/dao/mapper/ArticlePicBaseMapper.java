package com.nsu.stu.meet.dao.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nsu.stu.meet.common.util.OwnUtil;
import com.nsu.stu.meet.model.ArticlePic;
import com.nsu.stu.meet.model.ArticlePicDto;

import java.util.List;


public interface ArticlePicBaseMapper extends BaseMapper<ArticlePic> {
    String C_ARTICLE_PIC_ID = "article_pic_id";
    String C_ARTICLE_ID = "article_id";
    String C_USER_ID = "user_id";
    String C_GMT_CREATE = "gmt_create";
    String C_GMT_MODIFIED = "gmt_modified";
    String C_IS_DELETED = "is_deleted";

    default IPage<ArticlePic> findPage(ArticlePicDto condition, int currentPage, int pageSize) {
        QueryWrapper<ArticlePic> query = generateQuery(condition);
        IPage<ArticlePic> page = new Page<>(currentPage, pageSize);
        return selectPage(page, query);
    }


    default IPage<ArticlePicDto> findPageDto(ArticlePicDto condition, int currentPage, int pageSize) {
        IPage<ArticlePic> results = findPage(condition, currentPage, pageSize);
        return OwnUtil.transIPageRecords2T(results, ArticlePicDto.class);
    }

    /**
     * 按条件查询的基本方法
     * @param condition 传入的参数
     * @return 返回Application列表
     */
    default List<ArticlePic> findByCondition(ArticlePicDto condition) {
        return findByCondition(condition, null, null);
    }

    default List<ArticlePicDto> findByConditionDto(ArticlePicDto condition) {
        return findByConditionDto(condition, null, null);
    }
    default List<ArticlePicDto> findByConditionDto(ArticlePicDto condition, Integer currentPage, Integer pageSize) {
        return OwnUtil.copy2Ts(findByCondition(condition, currentPage, pageSize), ArticlePicDto.class);
    }


    default List<ArticlePic> findByCondition(ArticlePicDto condition, Integer currentPage, Integer pageSize) {
        QueryWrapper<ArticlePic> query = generateQuery(condition);
        if (currentPage != null && pageSize != null) {
            IPage<ArticlePic> page = new Page<>(currentPage, pageSize);
            return selectPage(page, query).getRecords();
        }
        return selectList(query);
    }

    default QueryWrapper<ArticlePic> generateQuery(ArticlePicDto condition) {
        if (condition == null) {
            return null;
        }
        QueryWrapper<ArticlePic> query = new QueryWrapper<>();
        defaultQuery(condition, query);
        customizeQuery(condition, query);
        return query;
    }
    default void defaultQuery(ArticlePicDto condition, QueryWrapper<ArticlePic> query) {
        query.eq(condition.getUserId()!=null, C_USER_ID, condition.getUserId());
        query.eq(condition.getArticleId()!=null, C_ARTICLE_ID, condition.getArticleId());
        query.eq(condition.getArticlePicId()!=null, C_ARTICLE_PIC_ID, condition.getArticlePicId());
        query.eq(condition.getGmtCreate()!=null, C_GMT_CREATE, condition.getGmtCreate());
        query.eq(condition.getGmtModified()!=null, C_GMT_MODIFIED, condition.getGmtModified());
        query.eq(condition.getIsDeleted()!=null, C_IS_DELETED, condition.getIsDeleted());
    }

    /**
     * 自定义查询语句方法,参照defaultQuery
     * @param condition 传入的参数
     * @param query 查询构造对象
     */
    void customizeQuery(ArticlePicDto condition, QueryWrapper<ArticlePic> query);
}
