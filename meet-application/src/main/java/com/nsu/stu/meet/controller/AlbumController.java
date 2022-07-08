package com.nsu.stu.meet.controller;

import com.nsu.stu.meet.annotation.Limit;
import com.nsu.stu.meet.annotation.LimitQuery;
import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.model.dto.AlbumDto;
import com.nsu.stu.meet.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Xinhua X Yang
 */
@RestController
@RequestMapping("album")
public class AlbumController {
    @Autowired
    private AlbumService albumService;

    @PostMapping(value = "/deleteBatch")
    public ResponseEntity<String> deleteBatch(@RequestBody List<Long> albumIdList) {
        return albumService.deleteAlbumBatch(albumIdList);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<String> create(@RequestBody AlbumDto albumDto) {
        return albumService.createAlbum(albumDto);
    }

    @PostMapping(value = "/modify")
    public ResponseEntity<String> modify(@RequestBody AlbumDto albumDto) {
        return albumService.modifyAlbum(albumDto);
    }

    @LimitQuery(userColumn = "a.user_id", limitColumn = "a.limit_id")
    @GetMapping(value = "/list")
    public ResponseEntity<List<AlbumDto>> list(@RequestParam(name = "userId") Long userId) {
        return albumService.selectAlbumListById(userId);

    }

    @Limit(clazz = AlbumService.class)
    @GetMapping(value = "/getAlbumById", params = {"albumId"})
    public ResponseEntity<AlbumDto> getAlbumById(Long albumId) {
        return albumService.selectAlbumById(albumId);
    }
}
