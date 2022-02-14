package com.nsu.stu.meet.dao.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nsu.stu.meet.common.util.OwnUtil;
import com.nsu.stu.meet.model.Message;
import com.nsu.stu.meet.model.MessageDto;
import org.springframework.util.StringUtils;

import java.util.List;


public interface MessageBaseMapper extends BaseMapper<Message> {
    String C_MESSAGE_ID = "message_id";
    String C_DEST_ID = "dest_id";
    String C_SRC_ID = "src_id";
    String C_CONTENT = "content";
    String C_STATUS = "status";
    String C_GMT_CREATE = "gmt_create";
    String C_GMT_MODIFIED = "gmt_modified";
    String C_IS_DELETED = "is_deleted";

    default IPage<Message> findPage(MessageDto condition, int currentPage, int pageSize) {
        QueryWrapper<Message> query = generateQuery(condition);
        IPage<Message> page = new Page<>(currentPage, pageSize);
        return selectPage(page, query);
    }


    default IPage<MessageDto> findPageDto(MessageDto condition, int currentPage, int pageSize) {
        IPage<Message> results = findPage(condition, currentPage, pageSize);
        return OwnUtil.transIPageRecords2T(results, MessageDto.class);
    }

    /**
     * 按条件查询的基本方法
     * @param condition 传入的参数
     * @return 返回Application列表
     */
    default List<Message> findByCondition(MessageDto condition) {
        return findByCondition(condition, null, null);
    }

    default List<MessageDto> findByConditionDto(MessageDto condition) {
        return findByConditionDto(condition, null, null);
    }
    default List<MessageDto> findByConditionDto(MessageDto condition, Integer currentPage, Integer pageSize) {
        return OwnUtil.copy2Ts(findByCondition(condition, currentPage, pageSize), MessageDto.class);
    }


    default List<Message> findByCondition(MessageDto condition, Integer currentPage, Integer pageSize) {
        QueryWrapper<Message> query = generateQuery(condition);
        if (currentPage != null && pageSize != null) {
            IPage<Message> page = new Page<>(currentPage, pageSize);
            return selectPage(page, query).getRecords();
        }
        return selectList(query);
    }

    default QueryWrapper<Message> generateQuery(MessageDto condition) {
        if (condition == null) {
            return null;
        }
        QueryWrapper<Message> query = new QueryWrapper<>();
        defaultQuery(condition, query);
        customizeQuery(condition, query);
        return query;
    }
    default void defaultQuery(MessageDto condition, QueryWrapper<Message> query) {
        query.eq(condition.getMessageId()!=null, C_MESSAGE_ID, condition.getMessageId());
        query.eq(condition.getDestId()!=null, C_DEST_ID, condition.getDestId());
        query.eq(condition.getSrcId()!=null, C_SRC_ID, condition.getSrcId());
        query.eq(condition.getStatus()!=null, C_STATUS, condition.getStatus());
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
    void customizeQuery(MessageDto condition, QueryWrapper<Message> query);
}
