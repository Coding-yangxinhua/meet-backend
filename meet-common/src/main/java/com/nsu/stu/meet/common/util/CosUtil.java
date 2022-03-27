package com.nsu.stu.meet.common.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.MultiObjectDeleteException;
import com.qcloud.cos.model.*;
import com.qcloud.cos.model.ciModel.persistence.CIUploadResult;
import com.qcloud.cos.transfer.TransferManager;
import com.qcloud.cos.transfer.Upload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CosUtil {

    @Value(value = "${cloud.cos.bucketName}")
    private String bucketName;

    @Value(value = "${cloud.cos.urlPrefix}")
    private String urlPrefix;

    @Autowired
    private TransferManager transferManager;

    @Autowired
    private COSClient cosClient;

    public String upload (MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            String filename = file.getResource().getFilename();
            String suffix = OwnUtil.getFileSuffix(filename);
            String prefix = RandomUtil.randomString(20);
            // 对象键(Key)是对象在存储桶中的唯一标识。
            String key = DateUtil.today() + "/" + prefix + suffix;
            // 这里创建一个 ByteArrayInputStream 来作为示例，实际中这里应该是您要上传的 InputStream 类型的流
            long inputStreamLength = file.getSize();

            ObjectMetadata objectMetadata = new ObjectMetadata();
            // 上传的流如果能够获取准确的流长度，则推荐一定填写 content-length
            objectMetadata.setContentLength(inputStreamLength);
            PutObjectRequest putObjectRequest = new PutObjectRequest(this.bucketName, key, inputStream, objectMetadata);
            // 高级接口会返回一个异步结果Upload
            // 可同步地调用 waitForUploadResult 方法等待上传完成，成功返回UploadResult, 失败抛出异常
            Upload upload = transferManager.upload(putObjectRequest);
            return urlPrefix + key;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public List<String> upload (MultipartFile[] files, int maxLength) {
        maxLength = Math.min(maxLength, files.length);
        List<String> urls = new ArrayList<>(files.length);
        for (int i = 0; i < maxLength; i ++) {
            MultipartFile file = files[i];
            urls.add(this.upload(file));
        }
        return urls;
    }

    public List<String> upload (MultipartFile[] files) {
        return upload(files, files.length);
    }

    public void delete(List<String> urls) {
        DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest(bucketName);
        // 设置要删除的key列表, 最多一次删除1000个
        // 传入要删除的文件名
        List<DeleteObjectsRequest.KeyVersion> keyList = url2key(urls);
        deleteObjectsRequest.setKeys(keyList);

        try {
            DeleteObjectsResult deleteObjectsResult = cosClient.deleteObjects(deleteObjectsRequest);
            List<DeleteObjectsResult.DeletedObject> deleteObjectResultArray = deleteObjectsResult.getDeletedObjects();
        } catch (MultiObjectDeleteException mde) {
            // 如果部分删除成功部分失败, 返回 MultiObjectDeleteException
            List<DeleteObjectsResult.DeletedObject> deleteObjects = mde.getDeletedObjects();
            List<MultiObjectDeleteException.DeleteError> deleteErrors = mde.getErrors();
        } catch (CosClientException e) {
            e.printStackTrace();
        }
    }

//    public void remove(List<String> urls) {
//        DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest(bucketName);
//        // 设置要删除的key列表, 最多一次删除1000个
//        // 传入要删除的文件名
//        List<DeleteObjectsRequest.KeyVersion> keyList = url2key(urls);
//        deleteObjectsRequest.setKeys(keyList);
//        CopyObjectRequest copyObjectRequest = new CopyObjectRequest(bucketName, srcBucketName,
//                srcKey, bucketName, destKey);
//        try {
//            DeleteObjectsResult deleteObjectsResult = cosClient.deleteObjects(deleteObjectsRequest);
//            List<DeleteObjectsResult.DeletedObject> deleteObjectResultArray = deleteObjectsResult.getDeletedObjects();
//        } catch (MultiObjectDeleteException mde) {
//            // 如果部分删除成功部分失败, 返回 MultiObjectDeleteException
//            List<DeleteObjectsResult.DeletedObject> deleteObjects = mde.getDeletedObjects();
//            List<MultiObjectDeleteException.DeleteError> deleteErrors = mde.getErrors();
//        } catch (CosClientException e) {
//            e.printStackTrace();
//        }
//    }

    private List<DeleteObjectsRequest.KeyVersion> url2key(List<String> urls) {
        return urls.stream().map(url -> new DeleteObjectsRequest.KeyVersion(url.replaceFirst(this.urlPrefix, ""))).collect(Collectors.toList());
    }

}
