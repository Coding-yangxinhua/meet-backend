package com.nsu.stu.meet.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.common.constant.SystemConstants;
import com.nsu.stu.meet.common.util.CosUtil;
import com.nsu.stu.meet.common.util.JwtUtil;
import com.nsu.stu.meet.dao.AlbumMapper;
import com.nsu.stu.meet.dao.AlbumPhotoMapper;
import com.nsu.stu.meet.model.Album;
import com.nsu.stu.meet.model.AlbumPhoto;
import com.nsu.stu.meet.model.dto.AlbumDto;
import com.nsu.stu.meet.service.AlbumPhotoService;
import com.nsu.stu.meet.service.AlbumService;
import com.nsu.stu.meet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class AlbumPhotoServiceImpl extends ServiceImpl<AlbumPhotoMapper, AlbumPhoto> implements AlbumPhotoService {
    @Autowired
    private CosUtil cosUtil;
    @Autowired
    private AlbumService albumService;

    @Override
    public ResponseEntity<String> uploadBatch(Long userId, Long albumId, MultipartFile[] files) {
        List<String> urls = cosUtil.upload(files);
        List<AlbumPhoto> albumPhotos = url2AlbumPhoto(userId, albumId, urls);
        Album album = albumService.selectAlbumByIdAndUserId(albumId, userId);
        if (album == null) {
            return ResponseEntity.checkError(SystemConstants.ALBUM_NOT_EXISTS_ERROR);
        }
        this.saveBatch(albumPhotos);
        return ResponseEntity.ok();
    }

    @Override
    public ResponseEntity<String> deleteAlbumPhotoBatch(Long userId, List<Long> albumIdList) {
        QueryWrapper<AlbumPhoto> albumPhotoQueryWrapper = new QueryWrapper<>();
        albumPhotoQueryWrapper.eq("user_id", userId).in("album_photo_id", albumIdList);
        baseMapper.delete(albumPhotoQueryWrapper);
        return ResponseEntity.ok();
    }

    private List<AlbumPhoto> url2AlbumPhoto(Long userId, Long albumId, List<String> urls) {
        List<AlbumPhoto> albumPhotos = new ArrayList<>();
        urls.forEach(url -> {
            AlbumPhoto albumPhoto = new AlbumPhoto();
            albumPhoto.setUserId(userId);
            albumPhoto.setAlbumId(albumId);
            albumPhoto.setUrl(url);
            albumPhotos.add(albumPhoto);
        });
        return albumPhotos;
    }

}
