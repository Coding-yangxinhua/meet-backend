package com.nsu.stu.meet.common.configuration;

import com.nsu.stu.meet.common.filter.CorsFilter;
import com.nsu.stu.meet.common.filter.SsoFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Xinhua X Yang
 */
@Configuration
public class SsoConfiguration {

    @Bean
    public FilterRegistrationBean<SsoFilter> loginFilter(SsoFilter ssoFilter) {
        FilterRegistrationBean<SsoFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(ssoFilter);
        registration.setUrlPatterns(new ArrayList<>(Collections.singletonList("/*")));
        registration.setName("ssoFilter");
        registration.setOrder(5);
        return registration;
    }

    @Bean
    public FilterRegistrationBean<CorsFilter> requestFilter(CorsFilter corsFilter) {
        FilterRegistrationBean<CorsFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(corsFilter);
        registration.setUrlPatterns(new ArrayList<>(Collections.singletonList("/*")));
        registration.setName("corsFilter");
        registration.setOrder(1);
        return registration;
    }

}
