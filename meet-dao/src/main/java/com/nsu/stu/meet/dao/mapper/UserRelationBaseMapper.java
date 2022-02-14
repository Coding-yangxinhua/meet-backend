package com.nsu.stu.meet.dao.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nsu.stu.meet.common.util.OwnUtil;
import com.nsu.stu.meet.model.UserRelation;
import com.nsu.stu.meet.model.UserRelationDto;
import org.springframework.util.StringUtils;

import java.util.List;


public interface UserRelationBaseMapper extends BaseMapper<UserRelation> {
    String C_RELATION_ID = "relation_id";
    String C_DEST_ID = "dest_id";
    String C_SRC_ID = "src_id";
    String C_STATUS = "status";
    String C_GMT_CREATE = "gmt_create";
    String C_GMT_MODIFIED = "gmt_modified";
    String C_IS_DELETED = "is_deleted";

    default IPage<UserRelation> findPage(UserRelationDto condition, int currentPage, int pageSize) {
        QueryWrapper<UserRelation> query = generateQuery(condition);
        IPage<UserRelation> page = new Page<>(currentPage, pageSize);
        return selectPage(page, query);
    }


    default IPage<UserRelationDto> findPageDto(UserRelationDto condition, int currentPage, int pageSize) {
        IPage<UserRelation> results = findPage(condition, currentPage, pageSize);
        return OwnUtil.transIPageRecords2T(results, UserRelationDto.class);
    }

    /**
     * 按条件查询的基本方法
     * @param condition 传入的参数
     * @return 返回Application列表
     */
    default List<UserRelation> findByCondition(UserRelationDto condition) {
        return findByCondition(condition, null, null);
    }

    default List<UserRelationDto> findByConditionDto(UserRelationDto condition) {
        return findByConditionDto(condition, null, null);
    }
    default List<UserRelationDto> findByConditionDto(UserRelationDto condition, Integer currentPage, Integer pageSize) {
        return OwnUtil.copy2Ts(findByCondition(condition, currentPage, pageSize), UserRelationDto.class);
    }


    default List<UserRelation> findByCondition(UserRelationDto condition, Integer currentPage, Integer pageSize) {
        QueryWrapper<UserRelation> query = generateQuery(condition);
        if (currentPage != null && pageSize != null) {
            IPage<UserRelation> page = new Page<>(currentPage, pageSize);
            return selectPage(page, query).getRecords();
        }
        return selectList(query);
    }

    default QueryWrapper<UserRelation> generateQuery(UserRelationDto condition) {
        if (condition == null) {
            return null;
        }
        QueryWrapper<UserRelation> query = new QueryWrapper<>();
        defaultQuery(condition, query);
        customizeQuery(condition, query);
        return query;
    }
    default void defaultQuery(UserRelationDto condition, QueryWrapper<UserRelation> query) {
        query.eq(condition.getRelationId()!=null, C_RELATION_ID, condition.getRelationId());
        query.eq(condition.getDestId()!=null, C_DEST_ID, condition.getDestId());
        query.eq(condition.getSrcId()!=null, C_SRC_ID, condition.getSrcId());
        query.eq(condition.getStatus()!=null, C_STATUS, condition.getStatus());
        query.eq(condition.getGmtCreate()!=null, C_GMT_CREATE, condition.getGmtCreate());
        query.eq(condition.getGmtModified()!=null, C_GMT_MODIFIED, condition.getGmtModified());
        query.eq(condition.getIsDeleted()!=null, C_IS_DELETED, condition.getIsDeleted());
    }

    /**
     * 自定义查询语句方法,参照defaultQuery
     * @param condition 传入的参数
     * @param query 查询构造对象
     */
    void customizeQuery(UserRelationDto condition, QueryWrapper<UserRelation> query);
}
