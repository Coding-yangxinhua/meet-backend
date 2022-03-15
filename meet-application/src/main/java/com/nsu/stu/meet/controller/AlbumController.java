package com.nsu.stu.meet.controller;

import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.common.util.CosUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("album")
public class AlbumController {
    @Autowired
    private CosUtil cosUtil;

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseEntity<String> deleteAlbumBatch(@RequestBody List<String> urls) {
        cosUtil.delete(urls);
        return ResponseEntity.ok();
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseEntity<String> uploadBatch(@RequestPart("files") MultipartFile[] multipartFiles) {
        return ResponseEntity.ok();
    }
}
