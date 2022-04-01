package com.nsu.stu.meet.controller;

import com.nsu.stu.meet.annotation.Limit;
import com.nsu.stu.meet.common.base.JwtStorage;
import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.common.constant.SystemConstants;
import com.nsu.stu.meet.model.User;
import com.nsu.stu.meet.model.UserRelation;
import com.nsu.stu.meet.model.dto.RelationLimitDto;
import com.nsu.stu.meet.model.dto.UserRelationDto;
import com.nsu.stu.meet.model.enums.RelationEnums;
import com.nsu.stu.meet.service.RelationLimitService;
import com.nsu.stu.meet.service.UserRelationService;
import com.nsu.stu.meet.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nullable;
import java.util.List;


@RestController
@RequestMapping("userRelation")
public class UserRelationController {
    @Autowired
    private UserRelationService userRelationService;

    @Limit(clazz = UserServiceImpl.class)
    @RequestMapping(value = "/changeStatus", method = RequestMethod.POST)
    public ResponseEntity<String> changeStatus(@RequestBody UserRelationDto userRelationDto) {
        Long userId = JwtStorage.userId();
        userRelationDto.setSrcId(userId);
        if (userRelationDto.getStatus().equals(RelationEnums.BLOCK)) {
            return ResponseEntity.checkError(SystemConstants.UNKNOWN_ERROR);
        }
        return userRelationService.changeStatus(userRelationDto);
    }

    @Limit(clazz = UserServiceImpl.class, half = true)
    @RequestMapping(value = "/changeBlock", method = RequestMethod.POST, params = {"block"})
    @Transactional
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
