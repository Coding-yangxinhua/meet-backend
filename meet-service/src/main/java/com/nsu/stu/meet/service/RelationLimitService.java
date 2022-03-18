package com.nsu.stu.meet.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.model.AlbumPhoto;
import com.nsu.stu.meet.model.RelationLimit;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RelationLimitService {
    List<RelationLimit> getUserRelationLimit(Long destId);
}
