package com.nsu.stu.meet.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nsu.stu.meet.common.base.JwtStorage;
import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.common.constant.SystemConstants;
import com.nsu.stu.meet.common.util.CosUtil;
import com.nsu.stu.meet.common.util.OwnUtil;
import com.nsu.stu.meet.dao.AlbumPhotoMapper;
import com.nsu.stu.meet.model.Album;
import com.nsu.stu.meet.model.AlbumPhoto;
import com.nsu.stu.meet.model.dto.AlbumDto;
import com.nsu.stu.meet.model.dto.AlbumPhotoDto;
import com.nsu.stu.meet.service.AlbumPhotoService;
import com.nsu.stu.meet.service.AlbumService;
import com.tencentcloudapi.cms.v20190321.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlbumPhotoServiceImpl extends ServiceImpl<AlbumPhotoMapper, AlbumPhoto> implements AlbumPhotoService {
    @Autowired
    private CosUtil cosUtil;
    @Autowired
    private AlbumService albumService;

    @Override
    @Transactional
    public ResponseEntity<String> uploadBatch(Long albumId, MultipartFile[] files) {
        Long userId = JwtStorage.userId();
        Album album = albumService.selectAlbumByIdAndUserId(albumId, userId);
        if (album == null) {
            return ResponseEntity.checkError(SystemConstants.ALBUM_NOT_EXISTS_ERROR);
        }
        List<String> urls = cosUtil.upload(files);
        List<AlbumPhoto> albumPhotos = url2AlbumPhoto(userId, albumId, urls);
        if(!StringUtils.hasText(album.getCover())) {
            album.setCover(urls.get(0));
            albumService.updateAlbum(album);
        }
        this.saveBatch(albumPhotos);
        return ResponseEntity.ok();
    }

    @Override
    public ResponseEntity<IPage<AlbumPhotoDto>> listByAlbumId(Long albumId, Long photoId, Integer page, Integer size) {
        int start = (page - 1) * size;
        List<AlbumPhotoDto> albumPhotoDtos = baseMapper.listByAlbumId(albumId, photoId, start, start + size);
        return ResponseEntity.ok(OwnUtil.records2Page(albumPhotoDtos, page, size));
    }

    @Override
    public ResponseEntity<String> deleteAlbumPhotoBatch(List<Long> albumIdList) {
        Long userId = JwtStorage.userId();
        // 构造查询条件
        QueryWrapper<AlbumPhoto> albumPhotoQueryWrapper = new QueryWrapper<>();
        albumPhotoQueryWrapper.eq("user_id", userId).in("album_photo_id", albumIdList);
        List<AlbumPhoto> albumPhotos = baseMapper.selectList(albumPhotoQueryWrapper);
        // 删除
        List<String> urls = new ArrayList<>();
        List<Long> albumIds = new ArrayList<>();
        albumPhotos.forEach(albumPhoto -> {
            urls.add(albumPhoto.getUrl());
            albumIds.add(albumPhoto.getAlbumPhotoId());
        });
        baseMapper.deleteBatchIds(albumIds);
        cosUtil.delete(urls);
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
