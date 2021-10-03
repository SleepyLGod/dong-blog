package com.lblog.blogbackend.Controller;

import com.lblog.blogbackend.service.ArticleService;
import com.lblog.blogbackend.service.CommentService;
import com.lblog.blogbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {

    // 自动注入
    @Autowired
    private UserService userService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CommentService commentService;
}
