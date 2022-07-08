package com.nsu.stu.meet.controller;

import com.nsu.stu.meet.annotation.Limit;
import com.nsu.stu.meet.common.base.JwtStorage;
import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.common.constant.SystemConstants;
import com.nsu.stu.meet.model.dto.UserRelationDto;
import com.nsu.stu.meet.model.enums.RelationEnums;
import com.nsu.stu.meet.service.UserRelationService;
import com.nsu.stu.meet.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("userRelation")
public class UserRelationController {
    @Autowired
    private UserRelationService userRelationService;

    @Limit(clazz = UserServiceImpl.class)
    @PostMapping(value = "/changeStatus")
    public ResponseEntity<String> changeStatus(@RequestBody UserRelationDto userRelationDto) {
        Long userId = JwtStorage.userId();
        userRelationDto.setSrcId(userId);
        if (userRelationDto.getStatus().equals(RelationEnums.BLOCK)) {
            return ResponseEntity.checkError(SystemConstants.UNKNOWN_ERROR);
        }
        return userRelationService.changeStatus(userRelationDto);
    }

    @Limit(clazz = UserServiceImpl.class, half = true)
    @PostMapping(value = "/changeBlock", params = {"block"})
    public ResponseEntity<String> changeBlock(@RequestBody UserRelationDto userRelationDto, boolean block) {
        Long userId = JwtStorage.userId();
        userRelationDto.setSrcId(userId);
        UserRelationDto otherRelation = new UserRelationDto();
        otherRelation.setStatus(RelationEnums.NORMAL);
        otherRelation.setSrcId(userRelationDto.getDestId());
        otherRelation.setDestId(userId);
        if (block) {
            userRelationDto.setStatus(RelationEnums.BLOCK);
            userRelationService.changeStatus(userRelationDto);
            userRelationService.changeStatus(otherRelation);
        } else {
            userRelationDto.setStatus(RelationEnums.NORMAL);
        }
        return userRelationService.changeStatus(userRelationDto);
    }


}
