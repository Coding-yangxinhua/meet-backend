package com.nsu.stu.meet.dao.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nsu.stu.meet.common.util.OwnUtil;
import com.nsu.stu.meet.model.ChatGroup;
import com.nsu.stu.meet.model.ChatGroupDto;
import org.springframework.util.StringUtils;

import java.util.List;


public interface ChatGroupMapper extends BaseMapper<ChatGroup> {
    String C_CHAT_GROUP_ID = "chat_group_id";
    String C_NAME = "name";
    String C_COVER = "cover";
    String C_MASTER_ID = "masterId";
    String C_MANAGER_ID = "managerId";
    String C_DESC = "desc";
    String C_GMT_CREATE = "gmt_create";
    String C_GMT_MODIFIED = "gmt_modified";
    String C_IS_DELETED = "is_deleted";

    default IPage<ChatGroup> findPage(ChatGroupDto condition, int currentPage, int pageSize) {
        QueryWrapper<ChatGroup> query = generateQuery(condition);
        IPage<ChatGroup> page = new Page<>(currentPage, pageSize);
        return selectPage(page, query);
    }


    default IPage<ChatGroupDto> findPageDto(ChatGroupDto condition, int currentPage, int pageSize) {
        IPage<ChatGroup> results = findPage(condition, currentPage, pageSize);
        return OwnUtil.transIPageRecords2T(results, ChatGroupDto.class);
    }

    /**
     * 按条件查询的基本方法
     * @param condition 传入的参数
     * @return 返回Application列表
     */
    default List<ChatGroup> findByCondition(ChatGroupDto condition) {
        return findByCondition(condition, null, null);
    }

    default List<ChatGroupDto> findByConditionDto(ChatGroupDto condition) {
        return findByConditionDto(condition, null, null);
    }
    default List<ChatGroupDto> findByConditionDto(ChatGroupDto condition, Integer currentPage, Integer pageSize) {
        return OwnUtil.copy2Ts(findByCondition(condition, currentPage, pageSize), ChatGroupDto.class);
    }


    default List<ChatGroup> findByCondition(ChatGroupDto condition, Integer currentPage, Integer pageSize) {
        QueryWrapper<ChatGroup> query = generateQuery(condition);
        if (currentPage != null && pageSize != null) {
            IPage<ChatGroup> page = new Page<>(currentPage, pageSize);
            return selectPage(page, query).getRecords();
        }
        return selectList(query);
    }

    default QueryWrapper<ChatGroup> generateQuery(ChatGroupDto condition) {
        if (condition == null) {
            return null;
        }
        QueryWrapper<ChatGroup> query = new QueryWrapper<>();
        defaultQuery(condition, query);
        customizeQuery(condition, query);
        return query;
    }
    default void defaultQuery(ChatGroupDto condition, QueryWrapper<ChatGroup> query) {
        query.eq(condition.getChatGroupId()!=null, C_CHAT_GROUP_ID, condition.getChatGroupId());
        query.eq(StringUtils.hasText(condition.getName()), C_NAME, condition.getName());
        query.eq(StringUtils.hasText(condition.getCover()), C_COVER, condition.getCover());
        query.eq(condition.getMasterId()!=null, C_MASTER_ID, condition.getMasterId());
        query.eq(condition.getManagerId()!=null, C_MANAGER_ID, condition.getManagerId());
        query.eq(StringUtils.hasText(condition.getDesc()), C_DESC, condition.getDesc());
        query.eq(condition.getGmtCreate()!=null, C_GMT_CREATE, condition.getGmtCreate());
        query.eq(condition.getGmtModified()!=null, C_GMT_MODIFIED, condition.getGmtModified());
        query.eq(condition.getIsDeleted()!=null, C_IS_DELETED, condition.getIsDeleted());
    }

    /**
     * 自定义查询语句方法,参照defaultQuery
     * @param condition 传入的参数
     * @param query 查询构造对象
     */
    void customizeQuery(ChatGroupDto condition, QueryWrapper<ChatGroup> query);
}
