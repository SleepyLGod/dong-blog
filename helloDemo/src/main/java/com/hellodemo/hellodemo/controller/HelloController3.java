package com.hellodemo.hellodemo.controller;

import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Body
 * @RequestBody 传过来的参数自动封装成实体类对象
 * http://localhost:8080/yourId1
 */

@RestController
public class HelloController3 {
    @RequestMapping(value = "/yourId1", method = RequestMethod.POST)
    // or: @PostMapping("/yourId1")
    public String yourId1( @RequestBody YourId yourid /*, @RequestHeader(value = "id") Integer id */ ) {
        System.out.println(yourid.getId());
        System.out.println("id: " + yourid.getId());
        return "hello " + yourid.getId();
     }
}
