package com.nsu.stu.meet.service;

import com.nsu.stu.meet.common.base.ResponseEntity;

public interface ArticleStatusService{

    ResponseEntity<String> like(Long articleId);

    ResponseEntity<String> dislike(Long articleId);

    ResponseEntity<String> repost(Long articleId);

    ResponseEntity<String> star(Long articleId);
}
