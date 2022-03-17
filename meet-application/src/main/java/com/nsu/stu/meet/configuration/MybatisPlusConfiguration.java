package com.nsu.stu.meet.configuration;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusPropertiesCustomizer;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.handlers.MybatisEnumTypeHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisPlusConfiguration {

    /**
     * mybatis-plus分页
     * @return
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    /**
     * mybatis-plus 自动完成insert和update时的值设置
     */
    @Bean
    public MetaObjectHandler mybatisPlusColumnSetterInterceptor() {
        MetaObjectHandler metaObjectHandler = new MetaObjectHandler() {
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
        return metaObjectHandler;
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
}
