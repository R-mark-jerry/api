package com.api.admin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试控制器
 *
 * @author API
 */
@RestController
@RequestMapping("/test")
public class TestController {
    
    /**
     * 测试接口
     */
    @GetMapping("/hello")
    public String hello() {
        return "Hello, API Admin Platform!";
    }
}