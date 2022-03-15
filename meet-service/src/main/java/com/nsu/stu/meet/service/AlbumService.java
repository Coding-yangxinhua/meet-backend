package com.nsu.stu.meet.service;

import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.model.Album;
import com.nsu.stu.meet.model.dto.AlbumDto;

public interface AlbumService {
    ResponseEntity<String> createAlbum (String token, AlbumDto albumDto);

    Album getAlbumById (Long albumId);
}
