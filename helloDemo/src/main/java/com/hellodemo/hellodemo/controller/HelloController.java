package com.hellodemo.hellodemo.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * path info
 * @PathVaribale 获取url中的数据
 * http://localhost:8080/id=xxx
 */
@RestController
public class HelloController {
    @RequestMapping(value = "/id={id}",method = RequestMethod.GET)
    // or: @GetMapping("/id={id}")
    public String sayHello( @PathVariable("id") Integer id ) {
        System.out.println(id);
        return "hello " + id ;
    }
}
