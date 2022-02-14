package com.nsu.stu.meet.dao.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nsu.stu.meet.common.util.OwnUtil;
import com.nsu.stu.meet.model.Chat;
import com.nsu.stu.meet.model.ChatDto;
import org.springframework.util.StringUtils;

import java.util.List;


public interface ChatBaseMapper extends BaseMapper<Chat> {
    String C_CHAT_ID = "chat_id";
    String C_USER_ID = "user_id";
    String C_DEST_ID = "dest_id";
    String C_TYPE = "type";
    String C_STATUS = "status";
    String C_IS_TOP = "is_top";
    String C_IS_HIDE = "is_hide";
    String C_CONTENT_TIME= "content_time";
    String C_CONTENT = "content";
    String C_TOP_TIME = "top_time";
    String C_GMT_CREATE = "gmt_create";
    String C_GMT_MODIFIED = "gmt_modified";
    String C_IS_DELETED = "is_deleted";

    default IPage<Chat> findPage(ChatDto condition, int currentPage, int pageSize) {
        QueryWrapper<Chat> query = generateQuery(condition);
        IPage<Chat> page = new Page<>(currentPage, pageSize);
        return selectPage(page, query);
    }


    default IPage<ChatDto> findPageDto(ChatDto condition, int currentPage, int pageSize) {
        IPage<Chat> results = findPage(condition, currentPage, pageSize);
        return OwnUtil.transIPageRecords2T(results, ChatDto.class);
    }

    /**
     * 按条件查询的基本方法
     * @param condition 传入的参数
     * @return 返回Application列表
     */
    default List<Chat> findByCondition(ChatDto condition) {
        return findByCondition(condition, null, null);
    }

    default List<ChatDto> findByConditionDto(ChatDto condition) {
        return findByConditionDto(condition, null, null);
    }
    default List<ChatDto> findByConditionDto(ChatDto condition, Integer currentPage, Integer pageSize) {
        return OwnUtil.copy2Ts(findByCondition(condition, currentPage, pageSize), ChatDto.class);
    }


    default List<Chat> findByCondition(ChatDto condition, Integer currentPage, Integer pageSize) {
        QueryWrapper<Chat> query = generateQuery(condition);
        if (currentPage != null && pageSize != null) {
            IPage<Chat> page = new Page<>(currentPage, pageSize);
            return selectPage(page, query).getRecords();
        }
        return selectList(query);
    }

    default QueryWrapper<Chat> generateQuery(ChatDto condition) {
        if (condition == null) {
            return null;
        }
        QueryWrapper<Chat> query = new QueryWrapper<>();
        defaultQuery(condition, query);
        customizeQuery(condition, query);
        return query;
    }
    default void defaultQuery(ChatDto condition, QueryWrapper<Chat> query) {
        query.eq(condition.getChatId()!=null, C_CHAT_ID, condition.getChatId());
        query.eq(condition.getUserId()!=null, C_USER_ID, condition.getUserId());
        query.eq(condition.getDestId()!=null, C_DEST_ID, condition.getDestId());
        query.eq(condition.getType()!=null, C_TYPE, condition.getChatId());
        query.eq(condition.getStatus()!=null, C_STATUS, condition.getStatus());
        query.eq(condition.getIsTop()!=null, C_IS_TOP, condition.getIsTop());
        query.eq(condition.getIsHide()!=null, C_IS_HIDE, condition.getIsHide());
        query.eq(condition.getContentTime()!=null, C_CONTENT_TIME, condition.getContentTime());
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
    void customizeQuery(ChatDto condition, QueryWrapper<Chat> query);
}
