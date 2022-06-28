package com.nsu.stu.meet.service;

import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.model.Album;
import com.nsu.stu.meet.model.dto.AlbumDto;

import java.util.List;

public interface AlbumService extends CheckService{
    ResponseEntity<String> createAlbum (AlbumDto albumDto);

    ResponseEntity<String> modifyAlbum (AlbumDto albumDto);

    ResponseEntity<String> deleteAlbumBatch (List<Long> albumIdList);

    ResponseEntity<AlbumDto> selectAlbumById (Long albumId);

    Album selectAlbumByIdAndUserId (Long albumId, Long userId);


    ResponseEntity<List<AlbumDto>> selectAlbumListSelf ();

    void updateAlbum(Album album);

    ResponseEntity<List<AlbumDto>> selectAlbumListOther (Long userId);
}
