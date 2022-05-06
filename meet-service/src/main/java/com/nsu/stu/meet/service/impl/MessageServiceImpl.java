package com.nsu.stu.meet.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.common.util.OwnUtil;
import com.nsu.stu.meet.dao.MessageMapper;
import com.nsu.stu.meet.model.Message;
import com.nsu.stu.meet.model.dto.MessageDto;
import com.nsu.stu.meet.service.MessageService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Xinhua X Yang
* @description 针对表【mt_message】的数据库操作Service实现
* @createDate 2022-05-05 14:38:18
*/
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message>
    implements MessageService {

    @Override
    public ResponseEntity<IPage<MessageDto>> list(Long srcId, Long destId, int page, int size) {
        int start = (page - 1) * size;
        int end = start + size;
        List<MessageDto> list = baseMapper.list(srcId, destId, start, end);
        return ResponseEntity.ok(OwnUtil.records2Page(list, page, size));
    }

}




