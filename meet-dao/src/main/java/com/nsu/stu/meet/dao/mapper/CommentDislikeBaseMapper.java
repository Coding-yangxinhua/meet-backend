package com.nsu.stu.meet.dao.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nsu.stu.meet.common.util.OwnUtil;
import com.nsu.stu.meet.model.CommentDislike;
import com.nsu.stu.meet.model.CommentDislikeDto;

import java.util.List;


public interface CommentDislikeBaseMapper extends BaseMapper<CommentDislike> {
    String C_COMMENT_DISLIKE_ID = "comment_dislike_id";
    String C_COMMENT_ID = "comment_id";
    String C_USER_ID = "user_id";
    String C_GMT_CREATE = "gmt_create";
    String C_GMT_MODIFIED = "gmt_modified";
    String C_IS_DELETED = "is_deleted";

    default IPage<CommentDislike> findPage(CommentDislikeDto condition, int currentPage, int pageSize) {
        QueryWrapper<CommentDislike> query = generateQuery(condition);
        IPage<CommentDislike> page = new Page<>(currentPage, pageSize);
        return selectPage(page, query);
    }


    default IPage<CommentDislikeDto> findPageDto(CommentDislikeDto condition, int currentPage, int pageSize) {
        IPage<CommentDislike> results = findPage(condition, currentPage, pageSize);
        return OwnUtil.transIPageRecords2T(results, CommentDislikeDto.class);
    }

    /**
     * 按条件查询的基本方法
     * @param condition 传入的参数
     * @return 返回Application列表
     */
    default List<CommentDislike> findByCondition(CommentDislikeDto condition) {
        return findByCondition(condition, null, null);
    }

    default List<CommentDislikeDto> findByConditionDto(CommentDislikeDto condition) {
        return findByConditionDto(condition, null, null);
    }
    default List<CommentDislikeDto> findByConditionDto(CommentDislikeDto condition, Integer currentPage, Integer pageSize) {
        return OwnUtil.copy2Ts(findByCondition(condition, currentPage, pageSize), CommentDislikeDto.class);
    }


    default List<CommentDislike> findByCondition(CommentDislikeDto condition, Integer currentPage, Integer pageSize) {
        QueryWrapper<CommentDislike> query = generateQuery(condition);
        if (currentPage != null && pageSize != null) {
            IPage<CommentDislike> page = new Page<>(currentPage, pageSize);
            return selectPage(page, query).getRecords();
        }
        return selectList(query);
    }

    default QueryWrapper<CommentDislike> generateQuery(CommentDislikeDto condition) {
        if (condition == null) {
            return null;
        }
        QueryWrapper<CommentDislike> query = new QueryWrapper<>();
        defaultQuery(condition, query);
        customizeQuery(condition, query);
        return query;
    }
    default void defaultQuery(CommentDislikeDto condition, QueryWrapper<CommentDislike> query) {
        query.eq(condition.getUserId()!=null, C_USER_ID, condition.getUserId());
        query.eq(condition.getCommentId()!=null, C_COMMENT_ID, condition.getCommentId());
        query.eq(condition.getCommentDislikeId()!=null, C_COMMENT_DISLIKE_ID, condition.getCommentDislikeId());
        query.eq(condition.getGmtCreate()!=null, C_GMT_CREATE, condition.getGmtCreate());
        query.eq(condition.getGmtModified()!=null, C_GMT_MODIFIED, condition.getGmtModified());
        query.eq(condition.getIsDeleted()!=null, C_IS_DELETED, condition.getIsDeleted());
    }

    /**
     * 自定义查询语句方法,参照defaultQuery
     * @param condition 传入的参数
     * @param query 查询构造对象
     */
    void customizeQuery(CommentDislikeDto condition, QueryWrapper<CommentDislike> query);
}
