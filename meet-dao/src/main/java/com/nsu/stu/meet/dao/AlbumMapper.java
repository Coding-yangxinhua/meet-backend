package com.nsu.stu.meet.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nsu.stu.meet.model.Album;
import com.nsu.stu.meet.model.dto.AlbumDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Xinhua X Yang
 */
public interface AlbumMapper extends BaseMapper<Album> {
    /**
     * 查询指定用户相册
     * @param userId
     * @return
     */
    List<AlbumDto> selectAlbumListByUserId (@Param("userId") Long userId);

    /**
     * 查询指定相册
     * @param albumId
     * @return
     */
    AlbumDto selectAlbumById (@Param("albumId") Long albumId);
}
