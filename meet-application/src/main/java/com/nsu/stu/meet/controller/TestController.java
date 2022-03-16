package com.nsu.stu.meet.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.common.constant.SystemConstants;
import com.nsu.stu.meet.common.enums.ResultStatus;
import com.nsu.stu.meet.common.util.JwtUtil;
import com.nsu.stu.meet.dao.AlbumMapper;
import com.nsu.stu.meet.service.AlbumService;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.UploadResult;
import com.qcloud.cos.transfer.TransferManager;
import com.qcloud.cos.transfer.Upload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("test")
public class TestController {

    @Autowired
    private TransferManager transferManager;
    private final String bucketName = "meet-1302770283";

    @Value("${system.cookie.host}")
    private String host;
    @Value("${system.cookie.path}")
    private String path;
    @Autowired
    private AlbumMapper albumMapper;

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

    @RequestMapping(value = "/set_cookie", method = RequestMethod.POST)
    public String testSetCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie(SystemConstants.TOKEN_NAME, "utf-8");
        cookie.setDomain(host);
        cookie.setPath(path);
        cookie.setValue("token:11111111111111111111");
        response.addCookie(cookie);
        return cookie.getValue();
    }
    @RequestMapping(value = "/get_cookie", method = RequestMethod.GET)
    public String testGetCookie(HttpServletRequest request) {
        List<Cookie> collect = Arrays.stream(request.getCookies()).filter(Cookie -> Cookie.getName().equals(SystemConstants.TOKEN_NAME)).collect(Collectors.toList());
        String value = null;
        if (!collect.isEmpty()) {
            Cookie cookie = collect.get(0);
            value = cookie.getValue();
        }
        return value;
    }

    @RequestMapping(value = "/testException", method = RequestMethod.GET)
    public String testException() {
        return JwtUtil.getTokenUserId("ddddd").toString();
    }

    @RequestMapping(value = "/testDeleteBatch", method = RequestMethod.GET)
    public String testDeleteBatch() {
        List<Long> ids = Arrays.asList(1L, 2L, 3L);
        albumMapper.deleteBatchIds(ids);
        return "success";
    }
}
