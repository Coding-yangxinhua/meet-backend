package com.nsu.stu.meet.service;

import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.model.Album;
import com.nsu.stu.meet.model.dto.AlbumDto;

import java.util.List;

public interface AlbumService extends CheckService{
    /**
     * 创建相册
     * @param albumDto
     * @return
     */
    ResponseEntity<String> createAlbum (AlbumDto albumDto);

    /**
     * 修改相册
     * @param albumDto
     * @return
     */
    ResponseEntity<String> modifyAlbum (AlbumDto albumDto);

    /**
     * 批量删除相册
     * @param albumIdList
     * @return
     */
    ResponseEntity<String> deleteAlbumBatch (List<Long> albumIdList);

    /**
     * 查询指定相册
     * @param albumId
     * @return
     */
    ResponseEntity<AlbumDto> selectAlbumById (Long albumId);

    /**
     * 通过相册id和user id查询
     * @param albumId
     * @param userId
     * @return
     */
    Album selectAlbumByIdAndUserId (Long albumId, Long userId);

    /**
     * 更新相册
     * @param album
     */
    void updateAlbum(Album album);

    /**
     * 查询指定用户相册
     * @param userId
     * @return
     */
    ResponseEntity<List<AlbumDto>> selectAlbumListById(Long userId);
}
