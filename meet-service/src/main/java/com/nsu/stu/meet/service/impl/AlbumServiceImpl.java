package com.nsu.stu.meet.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nsu.stu.meet.common.base.JwtStorage;
import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.common.util.CosUtil;
import com.nsu.stu.meet.common.util.OwnUtil;
import com.nsu.stu.meet.dao.AlbumMapper;
import com.nsu.stu.meet.model.Album;
import com.nsu.stu.meet.model.Article;
import com.nsu.stu.meet.model.RelationLimit;
import com.nsu.stu.meet.model.dto.AlbumDto;
import com.nsu.stu.meet.model.vo.LimitVo;
import com.nsu.stu.meet.service.AlbumService;
import com.nsu.stu.meet.service.RelationLimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class AlbumServiceImpl extends ServiceImpl<AlbumMapper, Album> implements AlbumService {
    @Autowired
    CosUtil cosUtil;

    @Autowired
    RelationLimitService relationLimitService;

    public ResponseEntity<String> createAlbum (AlbumDto albumDto)  {
        Long userId = JwtStorage.userId();
        if (!StringUtils.hasText(albumDto.getTitle())) {
            albumDto.setTitle(DateUtil.today());
        }
        albumDto.setUserId(userId);
        baseMapper.insert(albumDto);
        return ResponseEntity.ok();
    }

    @Override
    public ResponseEntity<String> modifyAlbum(AlbumDto albumDto) {
        Long userId = JwtStorage.userId();
        LambdaUpdateWrapper<Album> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Album::getUserId, userId).eq(Album::getAlbumId, albumDto.getAlbumId());
        setBaseNull(albumDto);
        baseMapper.update(albumDto, updateWrapper);
        return ResponseEntity.ok();
    }

    @Override
    public ResponseEntity<String> deleteAlbumBatch(List<Long> albumIdList) {
        Long userId = JwtStorage.userId();
        LambdaQueryWrapper<Album> albumQueryWrapper = new LambdaQueryWrapper<>();
        albumQueryWrapper.eq(Album::getUserId, userId).in(Album::getAlbumId, albumIdList);
        baseMapper.delete(albumQueryWrapper);
        return ResponseEntity.ok();
    }

    @Override
    public ResponseEntity<AlbumDto> selectAlbumById(Long albumId) {
        AlbumDto albumDto = baseMapper.selectAlbumById(albumId);
        albumDto.setSelf(JwtStorage.isSelf());
        return ResponseEntity.ok(albumDto);
    }

    @Override
    public Album selectAlbumByIdAndUserId(Long albumId, Long userId) {
        LambdaQueryWrapper<Album> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Album::getAlbumId, albumId).eq(Album::getUserId, userId);
        return baseMapper.selectOne(lambdaQueryWrapper);
    }

    @Override
    public ResponseEntity<List<AlbumDto>> selectAlbumListSelf() {
        Long userId = JwtStorage.userId();
        return ResponseEntity.ok(baseMapper.selectAlbumListWithNoLimit(userId));
    }

    @Override
    public ResponseEntity<List<AlbumDto>> selectAlbumListOther(Long userId) {
        Long tokenUserId = JwtStorage.userId();
        List<Integer> userRelationLimit = relationLimitService.getUserRelationLimit(tokenUserId, userId);
        List<AlbumDto> albumDtos = baseMapper.selectAlbumListByUserId(userId, userRelationLimit);
        return ResponseEntity.ok(albumDtos);
    }

    @Override
    public void updateAlbum(Album album) {
        this.updateById(album);
    }

    private void setBaseNull(AlbumDto albumDto) {
        albumDto.setUserId(null);
        albumDto.setIsDeleted(null);
        albumDto.setGmtCreate(null);
        albumDto.setGmtModified(null);
    }

    @Override
    public LimitVo getLimitVo(Long queryId) {
        // 获取文章实体
        Album album = this.getById(queryId);
        // 判空
        if (album == null) {
            return null;
        }
        return new LimitVo(album.getUserId(), album.getLimitId());
    }
}
