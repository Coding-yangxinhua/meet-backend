package com.nsu.stu.meet.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.common.util.CosUtil;
import com.nsu.stu.meet.common.util.JwtUtil;
import com.nsu.stu.meet.model.AlbumPhoto;
import com.nsu.stu.meet.service.AlbumPhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("albumPhoto")
public class AlbumPhotoController {
    @Autowired
    private CosUtil cosUtil;

    @Autowired
    private AlbumPhotoService albumPhotoService;

    @RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    public ResponseEntity<String> deleteBatch(@RequestBody List<Long> albumPhotoIdList, HttpServletRequest request) {
        Long tokenUserId = JwtUtil.getTokenUserId(request);
        return albumPhotoService.deleteAlbumPhotoBatch(tokenUserId, albumPhotoIdList);
    }

    @RequestMapping(value = "/uploadBatch", method = RequestMethod.POST, params = {"albumId"})
    public ResponseEntity<String> uploadBatch(@RequestPart("files") MultipartFile[] files, Long albumId, HttpServletRequest request) {
        Long tokenUserId = JwtUtil.getTokenUserId(request);
        return albumPhotoService.uploadBatch(tokenUserId, albumId, files);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET, params = {"page", "size", "albumId"})
    public ResponseEntity<IPage<AlbumPhoto>> list(Integer page, Integer size, Long albumId, HttpServletRequest request) {
        Long tokenUserId = JwtUtil.getTokenUserId(request);
        return albumPhotoService.list(tokenUserId, albumId, page, size);
    }
}
