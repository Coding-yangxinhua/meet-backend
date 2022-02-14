package com.nsu.stu.meet.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.common.enums.ResultStatus;
import com.nsu.stu.meet.common.util.SnowflakeUtil;
import com.nsu.stu.meet.util.SpringContextUtil;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.UploadResult;
import com.qcloud.cos.transfer.TransferManager;
import com.qcloud.cos.transfer.Upload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("test")
public class TestController {
    @Autowired
    private TransferManager transferManager;
    private final String bucketName = "meet-1302770283";

    @RequestMapping(value = "testUpload", method = RequestMethod.POST)
    public ResponseEntity<ResultStatus> testUpload(@RequestPart(value = "files") MultipartFile[] files) {
        // 对象键(Key)是对象在存储桶中的唯一标识。
        String today = DateUtil.today();
        String randomName = RandomUtil.randomString(20);
        String originalFilename = files[0].getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        String key = today + "/" + randomName + suffix;
        ObjectMetadata objectMetadata = new ObjectMetadata();
        // 设置 content-length
        objectMetadata.setContentLength(files[0].getSize());
        PutObjectRequest putObjectRequest = null;
        try {
            putObjectRequest = new PutObjectRequest(bucketName, key, files[0].getInputStream(), objectMetadata);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            // 高级接口会返回一个异步结果Upload
            // 可同步地调用 waitForUploadResult 方法等待上传完成，成功返回UploadResult, 失败抛出异常
            Upload upload = transferManager.upload(putObjectRequest);
            UploadResult uploadResult = upload.waitForUploadResult();
        } catch (CosClientException | InterruptedException e) {
            e.printStackTrace();
        }
        // 关闭
        transferManager.shutdownNow();
        return ResponseEntity.ok();
    }

}
