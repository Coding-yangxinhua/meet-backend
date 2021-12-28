package com.nsu.stu.meet.common.filter;

import com.alibaba.fastjson.JSON;
import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.common.config.SsoConfig;
import com.nsu.stu.meet.common.enums.ResultStatus;
import com.nsu.stu.meet.common.util.SsoUtil;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class SsoFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(SsoFilter.class);
    private String[] excludePaths;

    @Autowired
    private SsoConfig ssoConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 初始化免登录地址
        this.excludePaths = ssoConfig.getExcludePathArray();
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        // 设置跨域
        this.setCros(response);
        /**
         * 1. 判断是否使用内置账号
         */
        if (!ssoConfig.getEnable()) {
            chain.doFilter(req, res);
            return;
        }
        /**
         * 2. 判断是否为免登录地址
         */
        // 获取请求地址
        String requestUrl = request.getRequestURI();
        // 获取相对地址
        String contextPath = request.getContextPath();
        // 替换
        requestUrl = requestUrl.replace(contextPath, "");
        // 正则循环匹配判断是否为免登录地址
        for (String path:
        this.excludePaths) {
            Pattern pattern = Pattern.compile(path);
            Matcher matcher = pattern.matcher(requestUrl);
            boolean isFind = matcher.find();
            // 匹配则放行
            if (requestUrl.equals(path) || isFind) {
                chain.doFilter(req, res);
                return;
            }
        }
        /**
         * 3.非免登录地址 判断是否登录
         */
        // 获得用户token
        String auth = request.getHeader("auth");
        // 判断是否登录
        boolean login = SsoUtil.isLogin(auth);
        if (login) {
            chain.doFilter(req, res);
            return;
        }
        String json = JSON.toJSONString(ResponseEntity.builder().status(ResultStatus.FORBIDDEN).build());
        PrintWriter writer = response.getWriter();
        writer.append(json);
    }
    private void setCros(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT, GET");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
    }


}
