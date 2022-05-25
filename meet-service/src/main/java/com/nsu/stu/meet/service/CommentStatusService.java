package com.nsu.stu.meet.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.model.CommentStatus;
import com.nsu.stu.meet.model.dto.ArticleStatusDto;
import com.nsu.stu.meet.model.dto.CommentStatusDto;

/**
* @author Xinhua X Yang
* @description 针对表【mt_comment_status】的数据库操作Service
* @createDate 2022-05-04 22:24:33
*/
public interface CommentStatusService extends IService<CommentStatus> {

    ResponseEntity<String> changeStatus(CommentStatusDto commentStatusDto);
}
