package com.nsu.stu.meet.controller;

import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.common.util.JwtUtil;
import com.nsu.stu.meet.model.Album;
import com.nsu.stu.meet.model.dto.AlbumDto;
import com.nsu.stu.meet.service.AlbumPhotoService;
import com.nsu.stu.meet.service.AlbumService;
import io.lettuce.core.dynamic.annotation.Param;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nullable;
import java.util.List;

@RestController
@RequestMapping("album")
public class AlbumController {
    @Autowired
    private AlbumService albumService;

    @Autowired
    private AlbumPhotoService albumPhotoService;

    @RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    public ResponseEntity<String> deleteBatch(@RequestBody List<Long> albumIdList) {
        return albumService.deleteAlbumBatch(albumIdList);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<String> create(@RequestBody AlbumDto albumDto) {
        return albumService.createAlbum(albumDto);
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public ResponseEntity<String> modify(@RequestBody AlbumDto albumDto) {
        return albumService.modifyAlbum(albumDto);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity<List<AlbumDto>> list(@RequestParam(name = "userId", required = false) Long userId) {
        if (userId == null) {
            return albumService.selectAlbumListSelf();
        }
        return albumService.selectAlbumListOther(userId);

    }
}
