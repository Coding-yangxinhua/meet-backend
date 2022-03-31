package com.nsu.stu.meet.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.model.AlbumPhoto;
import com.nsu.stu.meet.model.RelationLimit;
import com.nsu.stu.meet.model.dto.RelationLimitDto;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

public interface RelationLimitService {
    /**
     * 用户关系对应权限
     * @param userId
     * @param queryId
     * @return
     */
    List<Integer> getUserRelationLimit (Long userId, Long queryId);
    /**
     * 获得关系对应的权限map
     */
    Map<Integer, List<Integer>> getLimitMap();

    /**
     * 关系对应权限
     * @param relation
     * @return
     */
    List<Integer> getRelationLimit(Integer relation);

    /**
     * 获得所有关系权限
     */
    List<RelationLimitDto> getAllRelationLimit();


    /**
     * 判断是否用户拥有对应权限
     * @param userId
     * @param queryId
     * @param limitId
     * @return
     */
    boolean isLimitPass(Long userId, Long queryId, Integer limitId);
}
