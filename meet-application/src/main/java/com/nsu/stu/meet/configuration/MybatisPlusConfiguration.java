package com.nsu.stu.meet.configuration;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusPropertiesCustomizer;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.handlers.MybatisEnumTypeHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.DataPermissionInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.google.common.collect.Lists;
import com.nsu.stu.meet.annotation.LimitHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author Xinhua X Yang
 */
@Configuration
public class MybatisPlusConfiguration {

    /**
     * mybatis-plus 自动完成insert和update时的值设置
     */
    @Bean
    public MetaObjectHandler mybatisPlusColumnSetterInterceptor() {
        return new MetaObjectHandler() {
            /**
             * 在insert前自动设置值
             */
            @Override
            public void insertFill(MetaObject metaObject) {
                Long currentTimeMillis = System.currentTimeMillis();
                this.setFieldValByName("gmtCreate", currentTimeMillis, metaObject);
                this.setFieldValByName("gmtModified", currentTimeMillis, metaObject);
                this.setFieldValByName("isDeleted", 0, metaObject);
            }

            /**
             * 在update前自动设置值
             */
            @Override
            public void updateFill(MetaObject metaObject) {
                this.setFieldValByName("gmtModified", System.currentTimeMillis(), metaObject);
            }

        };
    }

    @Bean
    public MybatisPlusPropertiesCustomizer mybatisPlusPropertiesCustomizer() {
        return properties -> {
            GlobalConfig globalConfig = properties.getGlobalConfig();
            globalConfig.setBanner(false);
            MybatisConfiguration configuration = new MybatisConfiguration();
            configuration.setDefaultEnumTypeHandler(MybatisEnumTypeHandler.class);
            properties.setConfiguration(configuration);
        };
    }

    /**
     * mybatis-plus分页
     * @return
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(LimitHandler limitHandler) {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 添加数据权限插件
        DataPermissionInterceptor dataPermissionInterceptor = new DataPermissionInterceptor();
        // 添加自定义的数据权限处理器
        dataPermissionInterceptor.setDataPermissionHandler(limitHandler);
        List<InnerInterceptor> interceptors = Lists.newArrayList(interceptor.getInterceptors());
        interceptors.clear();
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor(DbType.MYSQL);
        // 数据权限插件
        interceptors.add(dataPermissionInterceptor);
        // 分页插件
        interceptors.add(paginationInnerInterceptor);
        // 乐观锁插件
        interceptors.add(new OptimisticLockerInnerInterceptor());

        interceptor.setInterceptors(interceptors);

        return interceptor;
    }
}
