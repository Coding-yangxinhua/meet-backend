package com.nsu.stu.meet.dao.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nsu.stu.meet.common.util.OwnUtil;
import com.nsu.stu.meet.model.CommentLike;
import com.nsu.stu.meet.model.CommentLikeDto;

import java.util.List;


public interface CommentLikeBaseMapper extends BaseMapper<CommentLike> {
    String C_COMMENT_LIKE_ID = "comment_like_id";
    String C_COMMENT_ID = "comment_id";
    String C_USER_ID = "user_id";
    String C_GMT_CREATE = "gmt_create";
    String C_GMT_MODIFIED = "gmt_modified";
    String C_IS_DELETED = "is_deleted";

    default IPage<CommentLike> findPage(CommentLikeDto condition, int currentPage, int pageSize) {
        QueryWrapper<CommentLike> query = generateQuery(condition);
        IPage<CommentLike> page = new Page<>(currentPage, pageSize);
        return selectPage(page, query);
    }


    default IPage<CommentLikeDto> findPageDto(CommentLikeDto condition, int currentPage, int pageSize) {
        IPage<CommentLike> results = findPage(condition, currentPage, pageSize);
        return OwnUtil.transIPageRecords2T(results, CommentLikeDto.class);
    }

    /**
     * 按条件查询的基本方法
     * @param condition 传入的参数
     * @return 返回Application列表
     */
    default List<CommentLike> findByCondition(CommentLikeDto condition) {
        return findByCondition(condition, null, null);
    }

    default List<CommentLikeDto> findByConditionDto(CommentLikeDto condition) {
        return findByConditionDto(condition, null, null);
    }
    default List<CommentLikeDto> findByConditionDto(CommentLikeDto condition, Integer currentPage, Integer pageSize) {
        return OwnUtil.copy2Ts(findByCondition(condition, currentPage, pageSize), CommentLikeDto.class);
    }


    default List<CommentLike> findByCondition(CommentLikeDto condition, Integer currentPage, Integer pageSize) {
        QueryWrapper<CommentLike> query = generateQuery(condition);
        if (currentPage != null && pageSize != null) {
            IPage<CommentLike> page = new Page<>(currentPage, pageSize);
            return selectPage(page, query).getRecords();
        }
        return selectList(query);
    }

    default QueryWrapper<CommentLike> generateQuery(CommentLikeDto condition) {
        if (condition == null) {
            return null;
        }
        QueryWrapper<CommentLike> query = new QueryWrapper<>();
        defaultQuery(condition, query);
        customizeQuery(condition, query);
        return query;
    }
    default void defaultQuery(CommentLikeDto condition, QueryWrapper<CommentLike> query) {
        query.eq(condition.getUserId()!=null, C_USER_ID, condition.getUserId());
        query.eq(condition.getCommentId()!=null, C_COMMENT_ID, condition.getCommentId());
        query.eq(condition.getCommentLikeId()!=null, C_COMMENT_LIKE_ID, condition.getCommentLikeId());
        query.eq(condition.getGmtCreate()!=null, C_GMT_CREATE, condition.getGmtCreate());
        query.eq(condition.getGmtModified()!=null, C_GMT_MODIFIED, condition.getGmtModified());
        query.eq(condition.getIsDeleted()!=null, C_IS_DELETED, condition.getIsDeleted());
    }

    /**
     * 自定义查询语句方法,参照defaultQuery
     * @param condition 传入的参数
     * @param query 查询构造对象
     */
    void customizeQuery(CommentLikeDto condition, QueryWrapper<CommentLike> query);
}
