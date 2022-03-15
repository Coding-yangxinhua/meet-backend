package com.nsu.stu.meet.service.impl;

import cn.hutool.core.date.DateUtil;
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

@Service
public class AlbumServiceImpl extends ServiceImpl<AlbumMapper, Album> implements AlbumService {
    @Autowired
    CosUtil cosUtil;

    public ResponseEntity<String> createAlbum (String token, AlbumDto albumDto)  {
        Long tokenUserId = JwtUtil.getTokenUserId(token);
        if (!StringUtils.hasText(albumDto.getTitle())) {
            albumDto.setTitle(DateUtil.today());
        }
        albumDto.setUserId(tokenUserId);
        baseMapper.insert(albumDto);
        return ResponseEntity.ok();
    }

    @Override
    public Album getAlbumById(Long albumId) {
        return baseMapper.selectById(albumId);
    }

}
