package com.nsu.stu.meet.service;

import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.model.AlbumPhoto;
import com.nsu.stu.meet.model.dto.AlbumDto;
import org.springframework.web.multipart.MultipartFile;

public interface AlbumPhotoService {
    ResponseEntity<String> uploadPhotoBatch (Long userId, AlbumDto albumDto, MultipartFile[] files);
}
