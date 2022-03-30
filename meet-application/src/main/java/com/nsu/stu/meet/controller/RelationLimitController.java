package com.nsu.stu.meet.controller;

import com.nsu.stu.meet.annotation.Limit;
import com.nsu.stu.meet.common.base.JwtStorage;
import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.model.dto.RelationLimitDto;
import com.nsu.stu.meet.service.ArticleStatusService;
import com.nsu.stu.meet.service.RelationLimitService;
import com.nsu.stu.meet.service.impl.ArticleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("relationLimit")
public class RelationLimitController {
    @Autowired
    private RelationLimitService relationLimitService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<RelationLimitDto>> all() {
        List<RelationLimitDto> allRelationLimit = relationLimitService.getAllRelationLimit();
        return ResponseEntity.ok(allRelationLimit);
    }
}
