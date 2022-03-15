package com.nsu.stu.meet.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nsu.stu.meet.common.base.ResponseEntity;
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

import java.util.List;

@Service
public class AlbumPhotoServiceImpl extends ServiceImpl<AlbumPhotoMapper, AlbumPhoto> implements AlbumPhotoService {
    @Autowired
    private CosUtil cosUtil;
    @Autowired
    private AlbumService albumService;


    @Override
    public ResponseEntity<String> uploadPhotoBatch(Long userId, AlbumDto albumDto, MultipartFile[] files) {
        Album album = albumService.getAlbumById(albumDto.getAlbumId());
        if (album != null && album.getUserId().equals(userId)) {
            List<String> urls = cosUtil.upload(files);
            return null;
        }

        return null;
    }

}
