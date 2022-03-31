package com.nsu.stu.meet.service;

import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.model.dto.ArticleStatusDto;

public interface ArticleStatusService{

    ResponseEntity<String> changeStatus(ArticleStatusDto articleStatusDto);

}
