package com.nsu.stu.meet.service;


import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.model.User;
import com.nsu.stu.meet.model.UserRelation;
import com.nsu.stu.meet.model.dto.UserDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserRelationService {

    UserRelation getUserRelation (Long srcId, Long destId);

    List<Long> getFollowIdByUserId (Long userId);

}
