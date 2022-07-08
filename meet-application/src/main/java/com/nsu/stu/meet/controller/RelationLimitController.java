package com.nsu.stu.meet.controller;

import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.model.dto.RelationLimitDto;
import com.nsu.stu.meet.service.RelationLimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("relationLimit")
public class RelationLimitController {
    @Autowired
    private RelationLimitService relationLimitService;

    @GetMapping(value = "/all")
    public ResponseEntity<List<RelationLimitDto>> all() {
        List<RelationLimitDto> allRelationLimit = relationLimitService.getAllRelationLimit();
        return ResponseEntity.ok(allRelationLimit);
    }
}
