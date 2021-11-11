package com.lhd.mylblog.common.annotation;

import com.lhd.mylblog.common.enums.UserRole;

import java.lang.annotation.*;

/**
 * 登录鉴权注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginRequired {
    UserRole needRole() default UserRole.USER;
}
