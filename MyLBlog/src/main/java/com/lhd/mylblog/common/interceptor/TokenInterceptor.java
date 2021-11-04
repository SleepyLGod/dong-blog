package com.lhd.mylblog.common.interceptor;

import com.auth0.jwt.interfaces.Claim;
import com.lhd.mylblog.modules.admin.mapper.UserMapper;
import com.lhd.mylblog.modules.admin.model.User;
import com.lhd.mylblog.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    private static final ThreadLocal<User> currentuser = new ThreadLocal<>();

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtUtils jwtUtils;
        public TokenInterceptor(JwtUtils jwtUtils, UserMapper userMapper) {
        this.jwtUtils = jwtUtils;
        this.userMapper = userMapper;
    }

    public static User getCurrentUser() {
        return currentuser.get();
    }

    /**
     * 三个方法分别实现预处理、后处理（调用了Service并返回ModelAndView，但未进行页面渲染）、返回处理（已经渲染了页面）:
     *
     * 在preHandle中，可以进行编码、安全控制等处理
     * 在postHandle中，有机会修改ModelAndView
     * 在afterCompletion中，可以根据ex是否为null判断是否发生了异常，进行日志记录
     *
     */

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if("/login".equals(request.getRequestURI())){
            return true;
        }
        String header = request.getHeader("Authorization");
        if(header != null || !"".equals(header)) {
            if(header.startsWith("Author ")) {
                try {
                    Claims claims =
                }
            }
        }
    }


     /**
     *
     *
     * @Component
     * public class TokenInterceptor implements HandlerInterceptor {
     *     @Autowired
     *     private JwtUtil jwtUtil;
     *
     *     @Override
     *     public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
     *         //获取请求头（如果有此请求头，表示token已经签发）
     *         String header = request.getHeader("tokenHeader");
     *         if (header != null || !"".equals(header)) {
     *             //解析请求头（防止伪造token，token内容以"token "作为开头）
     *             if (header.startsWith("token ")) {
     *                 try {
     *                     Claims claims = jwtUtil.parseJWT(header.substring(6));
     *                     String role = (String) claims.get("role");
     *                     //为具有相关权限的用户添加权限到request域中
     *                     if ("admin".equals(role)) {
     *                         //拿到"admin_token"头信息，表示当前角色是admin
     *                         request.setAttribute("admin_token", header.substring(6));
     *                     }
     *                     if ("user".equals(role)) {
     *                         //拿到"user_token"头信息，表示当前角色是user
     *                         request.setAttribute("user_token", header.substring(6));
     *                     }
     *                 } catch (Exception e) {
     *                     throw new RuntimeException("令牌不正确");
     *                 }
     *             }
     *         }
     *         //所有请求都通过，具体权限在service层判断
     *         return true;
     *     }
     * }
     *
     *
     * 注册拦截器
     *
     * @Configuration
     * public class InterceptorConfig extends WebMvcConfigurationSupport {
     *     @Autowired
     *     private TokenInterceptor tokenInterceptor;
     *
     *     @Override
     *     protected void addInterceptors(InterceptorRegistry registry) {
     *         registry.addInterceptor(tokenInterceptor)
     *                 .addPathPatterns("/**")
     *                 .excludePathPatterns("/login/**");
     *     }
     * }
     *
     *
     * service层验证
     *
     *     public void deleteById(String id) {
     *         String admin_token = (String) request.getAttribute("admin_token");
     *         if(admin_token == null || "".equals(admin_token)){
     *             throw new RuntimeException("权限不足");
     *         }
     *         adminDao.deleteById(id);
     *     }
     */
}
