package com.nsu.stu.meet.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.model.AlbumPhoto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AlbumPhotoService {
    ResponseEntity<String> uploadBatch(Long userId, Long albumId, MultipartFile[] files);

    ResponseEntity<String> deleteAlbumPhotoBatch (Long userId, List<Long> albumIdList);

    ResponseEntity<IPage<AlbumPhoto>> list(Long userId, Long albumId, Integer page, Integer size);
}
