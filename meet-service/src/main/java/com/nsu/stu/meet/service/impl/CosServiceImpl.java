package com.nsu.stu.meet.service.impl;


import com.nsu.stu.meet.service.CosService;
import com.qcloud.cos.transfer.TransferManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CosServiceImpl implements CosService {
    private static final String BUCKET_NAME = "meet-1302770283";

    @Autowired
    private TransferManager transferManager;


}
