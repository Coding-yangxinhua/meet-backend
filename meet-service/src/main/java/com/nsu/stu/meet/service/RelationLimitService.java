package com.nsu.stu.meet.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.model.AlbumPhoto;
import com.nsu.stu.meet.model.RelationLimit;
import com.nsu.stu.meet.model.dto.RelationLimitDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RelationLimitService {
    /**
     * 获得对应人物关系对应的权限
     * @param destId
     * @return
     */
    List<RelationLimit> getUserRelationLimit(Long srcId, Long destId);

    List<RelationLimitDto> getAllRelationLimit();
}
