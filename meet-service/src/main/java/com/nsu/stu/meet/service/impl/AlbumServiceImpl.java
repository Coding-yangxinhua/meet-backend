package com.nsu.stu.meet.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.common.util.CosUtil;
import com.nsu.stu.meet.common.util.JwtUtil;
import com.nsu.stu.meet.dao.AlbumMapper;
import com.nsu.stu.meet.model.Album;
import com.nsu.stu.meet.model.dto.AlbumDto;
import com.nsu.stu.meet.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class AlbumServiceImpl extends ServiceImpl<AlbumMapper, Album> implements AlbumService {
    @Autowired
    CosUtil cosUtil;

    public ResponseEntity<String> createAlbum (Long userId, AlbumDto albumDto)  {
        if (!StringUtils.hasText(albumDto.getTitle())) {
            albumDto.setTitle(DateUtil.today());
        }
        albumDto.setUserId(userId);
        baseMapper.insert(albumDto);
        return ResponseEntity.ok();
    }

    @Override
    public ResponseEntity<String> modifyAlbum(Long userId, AlbumDto albumDto) {
        UpdateWrapper<Album> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_id", userId).eq("album_id", albumDto.getAlbumId());
        setBaseNull(albumDto);
        baseMapper.update(albumDto, updateWrapper);
        return ResponseEntity.ok();
    }

    @Override
    public ResponseEntity<String> deleteAlbumBatch(Long userId, List<Long> albumIdList) {
        QueryWrapper<Album> albumQueryWrapper = new QueryWrapper<>();
        albumQueryWrapper.eq("user_id", userId).in("album_id", albumIdList);
        baseMapper.delete(albumQueryWrapper);
        return ResponseEntity.ok();
    }

    @Override
    public Album selectAlbumByIdAndUserId(Long albumId, Long userId) {
        QueryWrapper<Album> albumQueryWrapper = new QueryWrapper<>();
        albumQueryWrapper.eq("user_id", userId).eq("album_id", albumId);
        return baseMapper.selectOne(albumQueryWrapper);
    }

    @Override
    public ResponseEntity<List<Album>> selectAlbumSelf(Long userId) {
        QueryWrapper<Album> albumQueryWrapper = new QueryWrapper<>();
        albumQueryWrapper.eq("user_id", userId);
        return ResponseEntity.ok(baseMapper.selectList(albumQueryWrapper));
    }

    @Override
    public ResponseEntity<List<Album>> selectAlbumByUserId(Long userId) {

        return null;
    }

    private void setBaseNull(AlbumDto albumDto) {
        albumDto.setUserId(null);
        albumDto.setIsDeleted(null);
        albumDto.setGmtCreate(null);
        albumDto.setGmtModified(null);
    }

}
