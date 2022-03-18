package com.nsu.stu.meet.controller;

import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.common.util.JwtUtil;
import com.nsu.stu.meet.model.Album;
import com.nsu.stu.meet.model.dto.AlbumDto;
import com.nsu.stu.meet.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("album")
public class AlbumController {
    @Autowired
    private AlbumService albumService;

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

    @RequestMapping(value = "/getAll", method = RequestMethod.GET, params = {"userId"})
    public ResponseEntity<List<Album>> getAlbumList(Long userId) {
        if (userId == null) {
            return albumService.selectAlbumSelf();
        }

    }
}
