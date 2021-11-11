package com.lhd.mylblog.config;

import com.lhd.mylblog.common.interceptor.TokenInterceptor;
import com.lhd.mylblog.modules.admin.mapper.*;
import com.lhd.mylblog.utils.JwtUtils;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * token拦截器配置
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserMapper userMapper;
    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    TagMapper tagMapper;
    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    ArticleCategoryAssMapper articleCategoryAssMapper;
    @Autowired
    ArticleTagAssMapper articleTagAssMapper;
    @Autowired
    CommentMapper commentMapper;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 可以添加多个拦截器，一般只添加一个
        // addPathPatterns("/**") 表示对所有请求都拦截
        // .excludePathPatterns("/base/index") 表示排除对/base/index请求的拦截
        // 多个拦截器可以设置order顺序，值越小，preHandle越先执行，postHandle和afterCompletion越后执行
        // order默认的值是0，如果只添加一个拦截器，可以不显示设置order的值
        registry.addInterceptor(
                new TokenInterceptor(jwtUtils, userMapper, articleMapper, commentMapper,
                        categoryMapper, tagMapper, articleTagAssMapper, articleCategoryAssMapper))
                .addPathPatterns("/**")
                .excludePathPatterns("/admin/user/login")
                .excludePathPatterns("/admin/user/register")
                .order(0);
    }
}
