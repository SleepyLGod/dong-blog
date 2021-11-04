package com.lhd.mylblog.modules.admin.controller;


import com.baomidou.mybatisplus.core.toolkit.EncryptUtils;
import com.baomidou.mybatisplus.extension.api.R;
import com.lhd.mylblog.common.api.CommonResult;
import com.lhd.mylblog.common.api.ErrorCode;
import com.lhd.mylblog.common.api.ResultCode;
import com.lhd.mylblog.modules.admin.dto.UserLoginDto;
import com.lhd.mylblog.modules.admin.model.Article;
import com.lhd.mylblog.modules.admin.model.User;
import com.lhd.mylblog.modules.admin.service.*;
import com.lhd.mylblog.utils.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author generator
 * @since 2021-10-28
 */
@RestController
@Api(value = "user-controller",tags = {"user"})
@RequestMapping("/admin/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

    @Autowired
    JwtUtils jwtUtils;

    /**
     * loginVerify
     *
     * @param userLoginDto
     * @return
     */
    @ApiOperation(value = "用户登陆",tags = {"user"}, notes = "用户登录接口必须是post请求")
    @PostMapping("/login")
    public CommonResult loginVerify(@ApiParam(name = "userLoginDto", value = "登录dto，必传", required = true) @RequestBody UserLoginDto userLoginDto) {
        String userName = userLoginDto.getUsername();
        String userPassword = EncryptUtils.md5Base64(userLoginDto.getPassword());
        String userEmail = userLoginDto.getEmail();
        String userTele = userLoginDto.getTele();
        // find:
        User user = new User();
        if(userName != null) {
        user = userService.getUserByName(userName);
        } else if(userEmail != null) {
            user = userService.getUserByEmail(userEmail);
        } else if(userTele != null) {
            user = userService.getUserByTele(userTele);
        }
        // check:
        if(user == null) {
            return CommonResult.failed(ResultCode.USER_NOT_EXISTS);
        } else if(!user.getPassword().equals(userPassword)) {
            return CommonResult.failed(ResultCode.CODE_INVALID);
        } else if(user.getStatus() == 0) {
            return CommonResult.failed(ResultCode.USER_INVALID);
        } else {
            return CommonResult.success().with("jwt",jwtUtils.generateJwtToken(user.getId(),user.getPassword()));
        }
    }

    /**
     * registerVerify
     *
     * @param user
     * @return
     */
    @PostMapping("/register")
    public CommonResult registerVerify(@RequestBody User user) {
        // check:
        String userName = user.getUserName();
        String userNickname = user.getNickname();
        String userEmail = user.getEmail();
        String userTele = user.getTele();
        String userPassword = user.getPassword();
        User check = new User();
        check = userService.getUserByName(userName);
        if(check != null) {
            return CommonResult.failed(ResultCode.USER_ALREADY_EXISTS);
        }
        check = userService.getUserByEmail(userEmail);
        if(check != null) {
            return CommonResult.failed(ResultCode.EMAIL_ALREADY_EXISTS);
        }
        check = userService.getUserByTele(userTele);
        if(check != null) {
            return CommonResult.failed(ResultCode.TELE_ALREADY_EXISTS);
        }
        // create user:
        // 更新用户登录时间
        user.setRegisterTime(new Date());
        // 默认删除时间
        SimpleDateFormat sdf = new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
        ParsePosition pos = new ParsePosition(0);
        Date date = sdf.parse( " 2021-10-01 00:00:00 " ,pos);
        user.setUserDeletedTime(date);
        // 密码加密
        user.setPassword(EncryptUtils.md5Base64(userPassword));
        // insert
        if(userService.insertUser(user) == null) {
            return CommonResult.failed(ResultCode.FAILED);
        } else  {
            return CommonResult.success(ResultCode.SUCCESS);
        }
    }




}

