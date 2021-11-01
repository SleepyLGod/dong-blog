package com.lhd.mylblog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = { org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class })
@MapperScan("com.lhd.mylblog.modules.admin.mapper")
public class MyLBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyLBlogApplication.class, args);
    }

}
