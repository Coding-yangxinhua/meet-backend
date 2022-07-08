package com.nsu.stu.meet.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nsu.stu.meet.annotation.Limit;
import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.model.dto.AlbumPhotoDto;
import com.nsu.stu.meet.service.AlbumPhotoService;
import com.nsu.stu.meet.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Xinhua X Yang
 */
@RestController
@RequestMapping("albumPhoto")
public class AlbumPhotoController {

    @Autowired
    private AlbumPhotoService albumPhotoService;

    @PostMapping(value = "/deleteBatch")
    public ResponseEntity<String> deleteBatch(@RequestBody List<Long> albumPhotoIdList) {
        return albumPhotoService.deleteAlbumPhotoBatch(albumPhotoIdList);
    }

    @PostMapping(value = "/uploadBatch", params = {"albumId"})
    public ResponseEntity<String> uploadBatch(@RequestPart("files") MultipartFile[] files, Long albumId) {
        return albumPhotoService.uploadBatch(albumId, files);
    }

    @Limit(clazz = AlbumService.class)
    @GetMapping(value = "/list", params = {"albumId", "page", "size"})
    public ResponseEntity<IPage<AlbumPhotoDto>> list(Long albumId, @RequestParam(value = "photoId", required = false) Long photoId, Integer page, Integer size) {
        return albumPhotoService.listByAlbumId(albumId, photoId, page, size);
    }
}
