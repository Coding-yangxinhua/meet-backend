package com.nsu.stu.meet.common.configuration;

import com.nsu.stu.meet.common.filter.CorsFilter;
import com.nsu.stu.meet.common.filter.SsoFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collections;

@Configuration
public class SsoConfiguration {

    @Autowired
    SsoFilter ssoFilter;

    @Autowired
    CorsFilter corsFilter;


    @Bean
    public FilterRegistrationBean<SsoFilter> loginFilter() {
        FilterRegistrationBean<SsoFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(ssoFilter);
        registration.setUrlPatterns(new ArrayList<>(Collections.singletonList("/*")));
        registration.setName("ssoFilter");
        registration.setOrder(5);
        return registration;
    }

    @Bean
    public FilterRegistrationBean<CorsFilter> requestFilter() {
        FilterRegistrationBean<CorsFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(corsFilter);
        registration.setUrlPatterns(new ArrayList<>(Collections.singletonList("/*")));
        registration.setName("corsFilter");
        registration.setOrder(1);
        return registration;
    }

}
