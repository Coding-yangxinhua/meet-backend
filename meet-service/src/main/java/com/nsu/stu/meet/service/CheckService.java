package com.nsu.stu.meet.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nsu.stu.meet.model.vo.LimitVo;


public interface CheckService {

    LimitVo getLimitVo(Long queryId);
}
