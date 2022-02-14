package com.nsu.stu.meet.dao.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nsu.stu.meet.common.util.OwnUtil;
import com.nsu.stu.meet.model.Comment;
import com.nsu.stu.meet.model.CommentDto;
import org.springframework.util.StringUtils;

import java.util.List;


public interface CommentBaseMapper extends BaseMapper<Comment> {
    String C_COMMENT_ID = "comment_id";
    String C_PARENT_ID = "parent_id";
    String C_ARTICLE_ID = "article_id";
    String C_USER_ID = "user_id";
    String C_CONTENT = "content";
    String C_GMT_CREATE = "gmt_create";
    String C_GMT_MODIFIED = "gmt_modified";
    String C_IS_DELETED = "is_deleted";

    default IPage<Comment> findPage(CommentDto condition, int currentPage, int pageSize) {
        QueryWrapper<Comment> query = generateQuery(condition);
        IPage<Comment> page = new Page<>(currentPage, pageSize);
        return selectPage(page, query);
    }


    default IPage<CommentDto> findPageDto(CommentDto condition, int currentPage, int pageSize) {
        IPage<Comment> results = findPage(condition, currentPage, pageSize);
        return OwnUtil.transIPageRecords2T(results, CommentDto.class);
    }

    /**
     * 按条件查询的基本方法
     * @param condition 传入的参数
     * @return 返回Application列表
     */
    default List<Comment> findByCondition(CommentDto condition) {
        return findByCondition(condition, null, null);
    }

    default List<CommentDto> findByConditionDto(CommentDto condition) {
        return findByConditionDto(condition, null, null);
    }
    default List<CommentDto> findByConditionDto(CommentDto condition, Integer currentPage, Integer pageSize) {
        return OwnUtil.copy2Ts(findByCondition(condition, currentPage, pageSize), CommentDto.class);
    }


    default List<Comment> findByCondition(CommentDto condition, Integer currentPage, Integer pageSize) {
        QueryWrapper<Comment> query = generateQuery(condition);
        if (currentPage != null && pageSize != null) {
            IPage<Comment> page = new Page<>(currentPage, pageSize);
            return selectPage(page, query).getRecords();
        }
        return selectList(query);
    }

    default QueryWrapper<Comment> generateQuery(CommentDto condition) {
        if (condition == null) {
            return null;
        }
        QueryWrapper<Comment> query = new QueryWrapper<>();
        defaultQuery(condition, query);
        customizeQuery(condition, query);
        return query;
    }
    default void defaultQuery(CommentDto condition, QueryWrapper<Comment> query) {
        query.eq(condition.getCommentId()!=null, C_COMMENT_ID, condition.getCommentId());
        query.eq(condition.getParentId()!=null, C_PARENT_ID, condition.getParentId());
        query.eq(condition.getArticleId()!=null, C_ARTICLE_ID, condition.getArticleId());
        query.eq(condition.getUserId()!=null, C_USER_ID, condition.getUserId());
        query.eq(StringUtils.hasText(condition.getContent()), C_CONTENT, condition.getContent());
        query.eq(condition.getGmtCreate()!=null, C_GMT_CREATE, condition.getGmtCreate());
        query.eq(condition.getGmtModified()!=null, C_GMT_MODIFIED, condition.getGmtModified());
        query.eq(condition.getIsDeleted()!=null, C_IS_DELETED, condition.getIsDeleted());
    }

    /**
     * 自定义查询语句方法,参照defaultQuery
     * @param condition 传入的参数
     * @param query 查询构造对象
     */
    void customizeQuery(CommentDto condition, QueryWrapper<Comment> query);
}
