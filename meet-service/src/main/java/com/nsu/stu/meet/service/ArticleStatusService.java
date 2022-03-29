package com.nsu.stu.meet.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nsu.stu.meet.common.base.LimitBaseService;
import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.model.dto.ArticleDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ArticleStatusService extends LimitBaseService {

    ResponseEntity<String> like(Long articleId);

    ResponseEntity<String> dislike(Long articleId);

    ResponseEntity<String> repost(Long articleId);

    ResponseEntity<String> star(Long articleId);
}
