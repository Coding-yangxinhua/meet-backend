package com.nsu.stu.meet.service;

import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.model.Album;
import com.nsu.stu.meet.model.AlbumPhoto;
import com.nsu.stu.meet.model.dto.AlbumDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AlbumPhotoService {
    ResponseEntity<String> uploadBatch(Long userId, Long albumId, MultipartFile[] files);

    ResponseEntity<String> deleteAlbumPhotoBatch (Long userId, List<Long> albumIdList);
}
