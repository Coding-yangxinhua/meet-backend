package com.nsu.stu.meet.service;

import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.model.Album;
import com.nsu.stu.meet.model.dto.AlbumDto;

import java.util.List;

public interface AlbumService {
    ResponseEntity<String> createAlbum (Long userId, AlbumDto albumDto);

    ResponseEntity<String> modifyAlbum (Long userId, AlbumDto albumDto);

    ResponseEntity<String> deleteAlbumBatch (Long userId, List<Long> albumIdList);

    Album selectAlbumByIdAndUserId (Long albumId, Long userId);

    ResponseEntity<List<Album>> selectAlbumByUserId (Long userId);
}
