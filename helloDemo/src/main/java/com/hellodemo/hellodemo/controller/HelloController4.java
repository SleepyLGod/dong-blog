package com.hellodemo.hellodemo.controller;


import org.springframework.web.bind.annotation.*;


/**
 * Body
 * @RequestParam 将请求参数绑定到你控制器的方法参数上
 * http://localhost:8080/yourId2
 */

@RestController
public class HelloController4 {
    @RequestMapping(value = "/yourId2", method = RequestMethod.POST)
    // or: @PostMapping("/yourId2")
    public String yourId2(@RequestParam(value = "arr") String arr, @RequestParam(value = "id", required = false) int id ) {
        System.out.println(id);
        return "hello " + id;
    }
}
