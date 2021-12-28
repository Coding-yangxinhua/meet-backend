package com.nsu.stu.meet.dao.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nsu.stu.meet.common.util.OwnUtil;
import com.nsu.stu.meet.model.Album;
import com.nsu.stu.meet.model.AlbumDto;
import org.springframework.util.StringUtils;

import java.util.List;


public interface ArticleBaseMapper extends BaseMapper<Album> {
    String C_ALBUM_ID = "albumId";
    String C_USER_ID = "userId";
    String C_PERMISSION_ID = "permissionId";
    String C_TITLE = "title";
    String C_COVER = "cover";
    String C_GMT_CREATE = "gmt_create";
    String C_GMT_MODIFIED = "gmt_modified";
    String C_IS_DELETED = "is_deleted";

    default IPage<Album> findPage(AlbumDto condition, int currentPage, int pageSize) {
        QueryWrapper<Album> query = generateQuery(condition);
        IPage<Album> page = new Page<>(currentPage, pageSize);
        return selectPage(page, query);
    }


    default IPage<AlbumDto> findPageDto(AlbumDto condition, int currentPage, int pageSize) {
        IPage<Album> results = findPage(condition, currentPage, pageSize);
        return OwnUtil.transIPageRecords2T(results, AlbumDto.class);
    }

    /**
     * 按条件查询的基本方法
     * @param condition 传入的参数
     * @return 返回Application列表
     */
    default List<Album> findByCondition(AlbumDto condition) {
        return findByCondition(condition, null, null);
    }

    default List<AlbumDto> findByConditionDto(AlbumDto condition) {
        return findByConditionDto(condition, null, null);
    }
    default List<AlbumDto> findByConditionDto(AlbumDto condition, Integer currentPage, Integer pageSize) {
        return OwnUtil.copy2Ts(findByCondition(condition, currentPage, pageSize), AlbumDto.class);
    }


    default List<Album> findByCondition(AlbumDto condition, Integer currentPage, Integer pageSize) {
        QueryWrapper<Album> query = generateQuery(condition);
        if (currentPage != null && pageSize != null) {
            IPage<Album> page = new Page<>(currentPage, pageSize);
            return selectPage(page, query).getRecords();
        }
        return selectList(query);
    }

    default QueryWrapper<Album> generateQuery(AlbumDto condition) {
        if (condition == null) {
            return null;
        }
        QueryWrapper<Album> query = new QueryWrapper<>();
        defaultQuery(condition, query);
        customizeQuery(condition, query);
        return query;
    }
    default void defaultQuery(AlbumDto condition, QueryWrapper<Album> query) {
        query.eq(condition.getUserId()!=null, C_USER_ID, condition.getUserId());
        query.eq(condition.getAlbumId()!=null, C_ALBUM_ID, condition.getAlbumId());
        query.eq(condition.getPermissionId()!=null, C_PERMISSION_ID, condition.getPermissionId());
        query.eq(StringUtils.hasText(condition.getTitle()), C_TITLE, condition.getTitle());
        query.eq(StringUtils.hasText(condition.getCover()), C_COVER, condition.getCover());
        query.eq(condition.getGmtCreate()!=null, C_GMT_CREATE, condition.getGmtCreate());
        query.eq(condition.getGmtModified()!=null, C_GMT_MODIFIED, condition.getGmtModified());
        query.eq(condition.getIsDeleted()!=null, C_IS_DELETED, condition.getIsDeleted());
    }

    /**
     * 自定义查询语句方法,参照defaultQuery
     * @param condition 传入的参数
     * @param query 查询构造对象
     */
    void customizeQuery(AlbumDto condition, QueryWrapper<Album> query);
}
