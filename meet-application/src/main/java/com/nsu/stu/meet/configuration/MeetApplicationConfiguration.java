package com.nsu.stu.meet.configuration;

import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.nacos.spring.context.annotation.config.EnableNacosConfig;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.nsu.stu.meet.common.enums.DeletedEnums;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 *
 *应用配置
 */
@Configuration
@EnableNacosConfig
@EnableConfigurationProperties
@MapperScan("com.**.dao")
@ComponentScan(basePackages = { "com.nsu.stu.meet" })
public class MeetApplicationConfiguration {

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
				this.strictInsertFill(metaObject, "gmtModified", Long.class, currentTimeMillis);
				this.strictInsertFill(metaObject, "gmtCreate", Long.class, currentTimeMillis);
				this.strictInsertFill(metaObject, "isDeleted", Integer.class, DeletedEnums.DELETED_NO.code);
			}

			/**
			 * 在update前自动设置值
			 */
			@Override
			public void updateFill(MetaObject metaObject) {
				this.strictInsertFill(metaObject, "gmtModified", Long.class, System.currentTimeMillis());
			}

		};
		return metaObjectHandler;
	}

	/**
	 *
	 * knife4j配置
	 */
	@Bean
	@ConditionalOnMissingBean
	public Docket defaultApi2() {
		Docket docket = new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(new ApiInfoBuilder().description("Meet Application APIs")
						.termsOfServiceUrl("http://localhost:8080/meet").version("1.0").build())
				.groupName("Ver 1.0").select().apis(RequestHandlerSelectors.basePackage("com.nsu.stu"))
				.paths(PathSelectors.any()).build();
		return docket;
	}

//	@Bean
//	@ConditionalOnMissingBean
//	public DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource) {
//		DataSourceTransactionManager manager = new DataSourceTransactionManager();
//		manager.setDataSource(dataSource);
//		return manager;
//	}

//	/**
//	 * druid监控
//	 */
//	@Bean
//	@ConditionalOnMissingBean
//	public ServletRegistrationBean statViewServlet() {
//		ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
//		Map<String, String> initParams = new HashMap<>();
//		initParams.put("loginApplicationname", "admin");
//		initParams.put("loginPassword", "admin");
//		initParams.put("allow", "");
//		bean.setInitParameters(initParams);
//		return bean;
//	}

	@Bean
	@ConditionalOnMissingBean
	public FilterRegistrationBean webStatFilter() {
		FilterRegistrationBean bean = new FilterRegistrationBean();
		bean.setFilter(new WebStatFilter());




		
		Map<String, String> initParams = new HashMap<>();
		initParams.put("exclusions", "*.js,*.css,/druid/*");
		bean.setInitParameters(initParams);
		bean.setUrlPatterns(Arrays.asList("/*"));
		return bean;
	}
}
