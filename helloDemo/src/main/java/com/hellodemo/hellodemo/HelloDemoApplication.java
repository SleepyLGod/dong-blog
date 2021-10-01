package com.hellodemo.hellodemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class HelloDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(HelloDemoApplication.class, args);
    }
}
