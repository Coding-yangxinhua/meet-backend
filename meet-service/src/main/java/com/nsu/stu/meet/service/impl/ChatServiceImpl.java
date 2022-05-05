package com.nsu.stu.meet.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.common.util.OwnUtil;
import com.nsu.stu.meet.dao.ChatMapper;
import com.nsu.stu.meet.model.Chat;
import com.nsu.stu.meet.model.dto.ChatDto;
import com.nsu.stu.meet.service.ChatService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Xinhua X Yang
* @description 针对表【mt_chat】的数据库操作Service实现
* @createDate 2022-05-05 11:45:55
*/
@Service
public class ChatServiceImpl extends ServiceImpl<ChatMapper, Chat> implements ChatService {

    @Override
    public ResponseEntity<IPage<ChatDto>> list(Long userId, Integer page, Integer size) {
        int start = (page - 1) * size;
        int end = start + size;
        List<ChatDto> list = baseMapper.list(userId, start, end);
        return ResponseEntity.ok(OwnUtil.records2Page(list, page, size));
    }
}




