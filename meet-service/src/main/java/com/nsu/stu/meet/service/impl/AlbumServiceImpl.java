package com.nsu.stu.meet.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nsu.stu.meet.common.base.JwtStorage;
import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.common.util.CosUtil;
import com.nsu.stu.meet.common.util.JwtUtil;
import com.nsu.stu.meet.dao.AlbumMapper;
import com.nsu.stu.meet.model.Album;
import com.nsu.stu.meet.model.dto.AlbumDto;
import com.nsu.stu.meet.model.enums.LimitEnums;
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

    public ResponseEntity<String> createAlbum (AlbumDto albumDto)  {
        Long userId = JwtStorage.info().getUserId();
        if (!StringUtils.hasText(albumDto.getTitle())) {
            albumDto.setTitle(DateUtil.today());
        }
        albumDto.setUserId(userId);
        baseMapper.insert(albumDto);
        return ResponseEntity.ok();
    }

    @Override
    public ResponseEntity<String> modifyAlbum(AlbumDto albumDto) {
        Long userId = JwtStorage.info().getUserId();
        UpdateWrapper<Album> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_id", userId).eq("album_id", albumDto.getAlbumId());
        setBaseNull(albumDto);
        baseMapper.update(albumDto, updateWrapper);
        return ResponseEntity.ok();
    }

    @Override
    public ResponseEntity<String> deleteAlbumBatch(List<Long> albumIdList) {
        Long userId = JwtStorage.info().getUserId();
        LambdaQueryWrapper<Album> albumQueryWrapper = new LambdaQueryWrapper<>();
        albumQueryWrapper.eq(Album::getUserId, userId).in(Album::getAlbumId, albumIdList);
        baseMapper.delete(albumQueryWrapper);
        return ResponseEntity.ok();
    }

    @Override
    public Album selectAlbumByIdAndUserId(Long albumId, Long userId) {
        LambdaQueryWrapper<Album> albumQueryWrapper = new LambdaQueryWrapper<>();
        albumQueryWrapper.eq(Album::getUserId, userId).eq(Album::getAlbumId, albumId);
        return baseMapper.selectOne(albumQueryWrapper);
    }

    @Override
    public ResponseEntity<List<Album>> selectAlbumSelf() {
        Long userId = JwtStorage.info().getUserId();
        LambdaQueryWrapper<Album> albumQueryWrapper = new LambdaQueryWrapper<>();
        albumQueryWrapper.eq(Album::getUserId, userId);
        return ResponseEntity.ok(baseMapper.selectList(albumQueryWrapper));
    }

    @Override
    public ResponseEntity<List<Album>> selectAlbumByUserId(Long userId) {
        LambdaQueryWrapper<Album> albumQueryWrapper = new LambdaQueryWrapper<>();
        albumQueryWrapper.eq(Album::getUserId, userId)
                .ne(Album::getLimitId, LimitEnums.PRIVATE).;
        return null;
    }

    private void setBaseNull(AlbumDto albumDto) {
        albumDto.setUserId(null);
        albumDto.setIsDeleted(null);
        albumDto.setGmtCreate(null);
        albumDto.setGmtModified(null);
    }

}
