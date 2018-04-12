package com.github.springboot.redis.controller;

import com.github.springboot.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaoping
 * @date 2018-04-12 16:52
 */
@RestController
public class HelloController {
    @Autowired
    RedisService redisService;

    @RequestMapping("/setString")
    public String setString(String key, String value) {
        redisService.setString(key, value, 60L);
        return "success";
    }

    @RequestMapping("/setList")
    public String setString(String key) {
        List<String> list = new ArrayList<>();
        list.add("x");
        list.add("p");
        redisService.setList(key, list, 60L);
        return "success";
    }

    @RequestMapping("/getKey")
    public String getKey(String key) {
        return redisService.getStringKey(key);
    }
}
