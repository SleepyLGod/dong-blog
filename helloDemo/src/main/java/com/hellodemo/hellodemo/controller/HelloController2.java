package com.hellodemo.hellodemo.controller;

import org.springframework.web.bind.annotation.*;

/**
 * URL Query String
 * @RequestParam 获取请求参数的值
 * http://localhost:8080/?id=xxx
 */

@RestController
public class HelloController2 {
    @RequestMapping(value = "",method = RequestMethod.GET)
    // or : @GetMapping(""）
    public String sayHello2(@RequestParam Integer id) {
        System.out.println(id);
        return "hello "+id;
    }
}
