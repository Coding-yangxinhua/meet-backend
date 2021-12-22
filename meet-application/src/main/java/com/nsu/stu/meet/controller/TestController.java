package com.nsu.stu.meet.controller;

import com.nsu.stu.meet.common.util.SnowflakeUtil;
import com.nsu.stu.meet.util.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("test")
public class TestController {

    @RequestMapping(value = "testSnowflake", method = RequestMethod.GET)
    public String testSnowflake() {
        return "snowflake: " + SnowflakeUtil.nextId();
    }
    @RequestMapping(value = "testApplication", method = RequestMethod.GET)
    public String testApplication() {
        return "snowflake: " + SpringContextUtil.containsBean("snowflake");
    }

}
