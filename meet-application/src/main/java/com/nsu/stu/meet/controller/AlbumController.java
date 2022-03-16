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
    public ResponseEntity<String> deleteBatch(@RequestBody List<Long> albumIdList, HttpServletRequest request) {
        Long tokenUserId = JwtUtil.getTokenUserId(request);
        return albumService.deleteAlbumBatch(tokenUserId, albumIdList);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<String> create(@RequestBody AlbumDto albumDto, HttpServletRequest request) {
        Long tokenUserId = JwtUtil.getTokenUserId(request);
        return albumService.createAlbum(tokenUserId, albumDto);
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public ResponseEntity<String> modify(@RequestBody AlbumDto albumDto, HttpServletRequest request) {
        Long tokenUserId = JwtUtil.getTokenUserId(request);
        return albumService.modifyAlbum(tokenUserId, albumDto);
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.POST)
    public ResponseEntity<List<Album>> list(HttpServletRequest request) {
        Long tokenUserId = JwtUtil.getTokenUserId(request);
        return albumService.selectAlbumByUserId(tokenUserId);
    }
}
