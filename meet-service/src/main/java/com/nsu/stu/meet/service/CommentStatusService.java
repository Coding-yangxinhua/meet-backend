package com.nsu.stu.meet.service;

import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.model.dto.ArticleStatusDto;
import com.nsu.stu.meet.model.dto.CommentStatusDto;

public interface CommentStatusService {

    ResponseEntity<String> changeStatus(CommentStatusDto commentStatusDto);

}
