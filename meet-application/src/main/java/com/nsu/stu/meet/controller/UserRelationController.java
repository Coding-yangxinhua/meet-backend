package com.nsu.stu.meet.controller;

import com.nsu.stu.meet.annotation.Limit;
import com.nsu.stu.meet.common.base.JwtStorage;
import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.model.UserRelation;
import com.nsu.stu.meet.model.dto.RelationLimitDto;
import com.nsu.stu.meet.model.dto.UserRelationDto;
import com.nsu.stu.meet.model.enums.RelationEnums;
import com.nsu.stu.meet.service.RelationLimitService;
import com.nsu.stu.meet.service.UserRelationService;
import com.nsu.stu.meet.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
    @RequestMapping(value = "/changeStatus", method = RequestMethod.GET)
    public ResponseEntity<String> changeStatus(UserRelationDto userRelationDto) {
        Long userId = JwtStorage.userId();
        userRelationDto.setSrcId(userId);
        return userRelationService.changeStatus(userRelationDto);
    }
}
