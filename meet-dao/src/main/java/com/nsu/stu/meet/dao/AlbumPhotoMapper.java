package com.nsu.stu.meet.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nsu.stu.meet.model.AlbumPhoto;
import com.nsu.stu.meet.model.RelationLimit;
import com.nsu.stu.meet.model.dto.AlbumDto;
import com.nsu.stu.meet.model.dto.AlbumPhotoDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface AlbumPhotoMapper extends BaseMapper<AlbumPhoto> {
}
