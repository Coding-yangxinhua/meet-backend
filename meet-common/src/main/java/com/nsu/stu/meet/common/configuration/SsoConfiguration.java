package com.nsu.stu.meet.common.configuration;

import com.nsu.stu.meet.common.config.SsoConfig;
import com.nsu.stu.meet.common.filter.SsoFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@Configuration
public class SsoConfiguration {

    @Autowired
    SsoFilter ssoFilter;


    @Bean
    public FilterRegistrationBean<SsoFilter> loginFilter() {
        FilterRegistrationBean<SsoFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(ssoFilter);
        registration.setUrlPatterns(new ArrayList<>(Collections.singletonList("/*")));
        registration.setName("ssoFilter");
        return registration;
    }

}
