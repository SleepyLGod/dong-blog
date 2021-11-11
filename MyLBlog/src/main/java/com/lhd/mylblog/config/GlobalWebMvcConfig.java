package com.lhd.mylblog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 全局跨域配置
 */
@Configuration
public class GlobalWebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //设置允许跨域的路径
        registry.addMapping("/**")
                //设置允许跨域请求的域名
                .allowedOrigins("*")
                //设置允许的方法
                .allowedMethods("GET", "POST", "PUT", "HEAD", "PATCH", "DELETE", "OPTIONS", "TRACE")
                //是否允许证书 不再默认开启
                .allowCredentials(true)
                //跨域允许时间
                .maxAge(3600)
                .allowedHeaders("*");
    }

    /**
     * 允许跨域调用的过滤器
     */
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        //允许所有域名进行跨域调用
        corsConfiguration.addAllowedOrigin("*");
        //允许跨越发送cookie
        corsConfiguration.setAllowCredentials(true);
        //放行全部原始头信息
        corsConfiguration.addAllowedHeader("*");
        //允许所有请求方法跨域调用
        corsConfiguration.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new org.springframework.web.filter.CorsFilter(source);
    }
}
