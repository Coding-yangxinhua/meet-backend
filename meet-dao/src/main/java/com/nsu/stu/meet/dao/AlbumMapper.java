package com.nsu.stu.meet.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nsu.stu.meet.model.Album;
import com.nsu.stu.meet.model.RelationLimit;
import com.nsu.stu.meet.model.dto.AlbumDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AlbumMapper extends BaseMapper<Album> {
    List<AlbumDto> selectAlbumListByUserId (@Param("userId") Long userId, @Param("limits") List<RelationLimit> limits);

    List<AlbumDto> selectAlbumListWithNoLimit (@Param("userId") Long userId);
}
