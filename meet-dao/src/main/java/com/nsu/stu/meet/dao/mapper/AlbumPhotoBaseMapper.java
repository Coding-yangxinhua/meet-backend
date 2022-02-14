package com.nsu.stu.meet.dao.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nsu.stu.meet.common.util.OwnUtil;
import com.nsu.stu.meet.model.AlbumPhoto;
import com.nsu.stu.meet.model.AlbumPhotoDto;
import org.springframework.util.StringUtils;

import java.util.List;


public interface AlbumPhotoBaseMapper extends BaseMapper<AlbumPhoto> {
    String C_ALBUM_PHOTO_ID = "album_photo_id";
    String C_USER_ID = "user_id";
    String C_ALBUM_ID = "album_id";
    String C_GMT_CREATE = "gmt_create";
    String C_GMT_MODIFIED = "gmt_modified";
    String C_IS_DELETED = "is_deleted";

    default IPage<AlbumPhoto> findPage(AlbumPhotoDto condition, int currentPage, int pageSize) {
        QueryWrapper<AlbumPhoto> query = generateQuery(condition);
        IPage<AlbumPhoto> page = new Page<>(currentPage, pageSize);
        return selectPage(page, query);
    }


    default IPage<AlbumPhotoDto> findPageDto(AlbumPhotoDto condition, int currentPage, int pageSize) {
        IPage<AlbumPhoto> results = findPage(condition, currentPage, pageSize);
        return OwnUtil.transIPageRecords2T(results, AlbumPhotoDto.class);
    }

    /**
     * 按条件查询的基本方法
     * @param condition 传入的参数
     * @return 返回Application列表
     */
    default List<AlbumPhoto> findByCondition(AlbumPhotoDto condition) {
        return findByCondition(condition, null, null);
    }

    default List<AlbumPhotoDto> findByConditionDto(AlbumPhotoDto condition) {
        return findByConditionDto(condition, null, null);
    }
    default List<AlbumPhotoDto> findByConditionDto(AlbumPhotoDto condition, Integer currentPage, Integer pageSize) {
        return OwnUtil.copy2Ts(findByCondition(condition, currentPage, pageSize), AlbumPhotoDto.class);
    }


    default List<AlbumPhoto> findByCondition(AlbumPhotoDto condition, Integer currentPage, Integer pageSize) {
        QueryWrapper<AlbumPhoto> query = generateQuery(condition);
        if (currentPage != null && pageSize != null) {
            IPage<AlbumPhoto> page = new Page<>(currentPage, pageSize);
            return selectPage(page, query).getRecords();
        }
        return selectList(query);
    }

    default QueryWrapper<AlbumPhoto> generateQuery(AlbumPhotoDto condition) {
        if (condition == null) {
            return null;
        }
        QueryWrapper<AlbumPhoto> query = new QueryWrapper<>();
        defaultQuery(condition, query);
        customizeQuery(condition, query);
        return query;
    }
    default void defaultQuery(AlbumPhotoDto condition, QueryWrapper<AlbumPhoto> query) {
        query.eq(condition.getUserId()!=null, C_USER_ID, condition.getUserId());
        query.eq(condition.getAlbumPhotoId()!=null, C_ALBUM_PHOTO_ID, condition.getAlbumPhotoId());
        query.eq(condition.getAlbumId()!=null, C_ALBUM_ID, condition.getAlbumId());
        query.eq(condition.getGmtCreate()!=null, C_GMT_CREATE, condition.getGmtCreate());
        query.eq(condition.getGmtModified()!=null, C_GMT_MODIFIED, condition.getGmtModified());
        query.eq(condition.getIsDeleted()!=null, C_IS_DELETED, condition.getIsDeleted());
    }

    /**
     * 自定义查询语句方法,参照defaultQuery
     * @param condition 传入的参数
     * @param query 查询构造对象
     */
    void customizeQuery(AlbumPhotoDto condition, QueryWrapper<AlbumPhoto> query);
}
