package com.nsu.stu.meet.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.dao.CommentStatusMapper;
import com.nsu.stu.meet.model.CommentStatus;
import com.nsu.stu.meet.model.dto.CommentStatusDto;
import com.nsu.stu.meet.service.CommentService;
import com.nsu.stu.meet.service.CommentStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author Xinhua X Yang
* @description 针对表【mt_comment_status】的数据库操作Service实现
* @createDate 2022-05-04 22:24:33
*/
@Service
public class CommentStatusServiceImpl extends ServiceImpl<CommentStatusMapper, CommentStatus> implements CommentStatusService {

    @Autowired
    private CommentService commentService;

    @Override
    public ResponseEntity<String> changeStatus(CommentStatusDto commentStatusDto) {
        LambdaUpdateWrapper<CommentStatus> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(CommentStatus::getCommentId, commentStatusDto.getCommentId()).eq(CommentStatus::getUserId, commentStatusDto.getUserId());
        this.saveOrUpdate(commentStatusDto, updateWrapper);
        return ResponseEntity.ok();
    }

}




