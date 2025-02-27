package com.example.demo.controller;



import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class HelloController {

    // 基础GET端点
    @GetMapping("/hello")
    public String simpleHello() {
        return "Spring Boo 服务已就绪";
    }

    // 带参数的复合请求处理
    @GetMapping("/hello/{name}")
    public Map<String, Object> personalizedHello(
            @PathVariable String name,
            @RequestParam(defaultValue = "2025") Integer version) {

        Map<String, Object> response = new HashMap<>();
        response.put("status", 200);
        response.put("message", "你张三好 " + name);
        response.put("systemVersion", "v" + version + ".2.27");
        return response;
    }
}

