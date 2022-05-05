package com.nsu.stu.meet.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.model.AlbumPhoto;
import com.nsu.stu.meet.model.dto.AlbumPhotoDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AlbumPhotoService {
    ResponseEntity<String> uploadBatch(Long albumId, MultipartFile[] files);

    ResponseEntity<String> deleteAlbumPhotoBatch (List<Long> albumIdList);

    ResponseEntity<IPage<AlbumPhotoDto>> listByAlbumId(Long albumId, Long photoId, Integer page, Integer size);
}
