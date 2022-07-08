package com.nsu.stu.meet.common.configuration;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.transfer.TransferManager;
import com.qcloud.cos.transfer.TransferManagerConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

@Configuration
@Component
public class CosConfiguration {

    @Value(value = "${cloud.secretId}")
    private String secretId;
    @Value(value = "${cloud.secretKey}")
    private String secretKey;

    @Bean
    public COSClient cosClient() {


        COSCredentials cred = new BasicCOSCredentials(this.secretId, this.secretKey);
        // 2 设置 bucket 的地域
        String regionName = "ap-chengdu";
        Region region = new Region(regionName);
        // 3 设置客户端配置文件
        ClientConfig clientConfig = new ClientConfig(region);
        // 设置为 http请求
        clientConfig.setHttpProtocol(HttpProtocol.http);
        // 4 生成 cos 客户端。
        return new COSClient(cred, clientConfig);
    }

    @Bean
    public TransferManager transferManager(COSClient cosClient) {
        // 创建一个 COSClient 实例，这是访问 COS 服务的基础实例。
        // 详细代码参见本页: 简单操作 -> 创建 COSClient
        // 自定义线程池大小，建议在客户端与 COS 网络充足（例如使用腾讯云的 CVM，同地域上传 COS）的情况下，设置成16或32即可，可较充分的利用网络资源
        // 对于使用公网传输且网络带宽质量不高的情况，建议减小该值，避免因网速过慢，造成请求超时。
        // 传入一个 threadpool, 若不传入线程池，默认 TransferManager 中会生成一个单线程的线程池。
        TransferManager transferManager = new TransferManager(cosClient, new ThreadPoolExecutor(16, 16,  0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>()));

        // 设置高级接口的配置项
        // 分块上传阈值和分块大小分别为 5MB 和 1MB
        TransferManagerConfiguration transferManagerConfiguration = new TransferManagerConfiguration();
        transferManager.setConfiguration(transferManagerConfiguration);
        return transferManager;
    }

}
