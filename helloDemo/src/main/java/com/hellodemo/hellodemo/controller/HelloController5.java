package com.hellodemo.hellodemo.controller;

import org.springframework.web.bind.annotation.*;

/**
 * Body & Headers
 * @RequestBody 传过来的参数自动封装成实体类对象
 *  @RequestHeader 接收键名，形参为值；
 * http://localhost:8080/yourId1
 */

@RestController
public class HelloController5 {
    @RequestMapping(value = "/yourId3", method = RequestMethod.POST)
    // or: @PostMapping("/yourId3")
    public String yourId1( @RequestBody YourId yourid, @RequestHeader(value = "id") Integer id ) {
        System.out.println(yourid.getId());
        System.out.println("id: " + yourid.getId());
        return "hello " + yourid.getId();
    }
}
