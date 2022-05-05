package com.nsu.stu.meet.controller;

import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.model.DictItem;
import com.nsu.stu.meet.service.DictItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("dictItem")
public class DictItemController {
    @Autowired
    private DictItemService dictItemService;

    @RequestMapping(value = "/getItemsByType", method = RequestMethod.GET, params = {"type"})
    public ResponseEntity<List<DictItem>> getItemsByType(Integer type) {
        return ResponseEntity.ok(dictItemService.getDictItemsByType(type));
    }
}
