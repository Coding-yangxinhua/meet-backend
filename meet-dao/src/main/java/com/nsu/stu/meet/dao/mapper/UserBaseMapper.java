package com.nsu.stu.meet.dao.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nsu.stu.meet.common.util.OwnUtil;
import com.nsu.stu.meet.model.User;
import com.nsu.stu.meet.model.UserDto;
import org.springframework.util.StringUtils;

import java.util.List;


public interface UserBaseMapper extends BaseMapper<User> {
    String C_USER_ID = "userId";
    String C_NICKNAME = "nickname";
    String C_MOBILE = "mobile";
    String C_PASSWORD = "password";
    String C_GENDER = "gender";
    String C_INTRO = "intro";
    String C_BIRTH = "birth";
    String C_GMT_CREATE = "gmt_create";
    String C_CREATE_USER_NAME = "create_user_name";
    String C_GMT_MODIFIED = "gmt_modified";
    String C_MODIFY_USER_NAME = "modify_user_name";
    String C_IS_DELETED = "is_deleted";

    default IPage<User> findPage(UserDto condition, int currentPage, int pageSize) {
        QueryWrapper<User> query = generateQuery(condition);
        IPage<User> page = new Page<>(currentPage, pageSize);
        return selectPage(page, query);
    }


    default IPage<UserDto> findPageDto(UserDto condition, int currentPage, int pageSize) {
        IPage<User> results = findPage(condition, currentPage, pageSize);
        return OwnUtil.transIPageRecords2T(results, UserDto.class);
    }

    /**
     * 按条件查询的基本方法
     * @param condition 传入的参数
     * @return 返回Application列表
     */
    default List<User> findByCondition(UserDto condition) {
        return findByCondition(condition, null, null);
    }

    default List<UserDto> findByConditionDto(UserDto condition) {
        return findByConditionDto(condition, null, null);
    }
    default List<UserDto> findByConditionDto(UserDto condition, Integer currentPage, Integer pageSize) {
        return OwnUtil.copy2Ts(findByCondition(condition, currentPage, pageSize), UserDto.class);
    }


    default List<User> findByCondition(UserDto condition, Integer currentPage, Integer pageSize) {
        QueryWrapper<User> query = generateQuery(condition);
        if (currentPage != null && pageSize != null) {
            IPage<User> page = new Page<>(currentPage, pageSize);
            return selectPage(page, query).getRecords();
        }
        return selectList(query);
    }

    default QueryWrapper<User> generateQuery(UserDto condition) {
        if (condition == null) {
            return null;
        }
        QueryWrapper<User> query = new QueryWrapper<>();
        defaultQuery(condition, query);
        customizeQuery(condition, query);
        return query;
    }
    default void defaultQuery(UserDto condition, QueryWrapper<User> query) {
        query.eq(condition.getUserId()!=null, C_USER_ID, condition.getUserId());
        query.eq(StringUtils.hasText(condition.getNickname()), C_NICKNAME, condition.getNickname());
        query.eq(StringUtils.hasText(condition.getMobile()), C_MOBILE, condition.getMobile());
        query.eq(StringUtils.hasText(condition.getPassword()), C_PASSWORD, condition.getPassword());
        query.eq(StringUtils.hasText(condition.getMobile()), C_GENDER, condition.getMobile());
        query.eq(StringUtils.hasText(condition.getIntro()), C_INTRO, condition.getIntro());
        query.eq(condition.getBirth()!=null, C_BIRTH, condition.getBirth());
        query.eq(condition.getGmtCreate()!=null, C_GMT_CREATE, condition.getGmtCreate());
        query.eq(StringUtils.hasText(condition.getMobile()), C_CREATE_USER_NAME, condition.getMobile());
        query.eq(condition.getGmtModified()!=null, C_GMT_MODIFIED, condition.getMobile());
        query.eq(StringUtils.hasText(condition.getModifyUserName()), C_MODIFY_USER_NAME, condition.getModifyUserName());
        query.eq(condition.getIsDeleted()!=null, C_IS_DELETED, condition.getIsDeleted());
    }

    /**
     * 自定义查询语句方法,参照defaultQuery
     * @param condition 传入的参数
     * @param query 查询构造对象
     */
    void customizeQuery(UserDto condition, QueryWrapper<User> query);
}
