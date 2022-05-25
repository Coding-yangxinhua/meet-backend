package com.nsu.stu.meet.configuration;

import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.nacos.spring.context.annotation.config.EnableNacosConfig;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
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
@EnableCaching
@MapperScan("com.**.dao")
@ComponentScan(basePackages = { "com.nsu.stu.meet" })
public class MeetApplicationConfiguration {



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

	/**
	 * Long 转 String 防止雪花id精度丢失
	 * @param builder
	 * @return
	 */
	@Bean
	@Primary
	@ConditionalOnMissingBean(ObjectMapper.class)
	public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder)
	{
		ObjectMapper objectMapper = builder.createXmlMapper(false).build();

		// 全局配置序列化返回 JSON 处理
		SimpleModule simpleModule = new SimpleModule();
		//JSON Long ==> String
		simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
		objectMapper.registerModule(simpleModule);
		return objectMapper;
	}

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
