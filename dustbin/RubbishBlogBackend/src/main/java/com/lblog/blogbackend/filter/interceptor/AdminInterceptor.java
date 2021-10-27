package com.lblog.blogbackend.filter.interceptor;
import com.lblog.blogbackend.constant.enums.UserRoleEnums;
import com.lblog.blogbackend.model.entity.UserEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class AdminInterceptor extends HandlerInterceptorAdapter{
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object o) throws IOException {
        // 根据session的用户来判断角色的权限，根据权限来转发不同的页面
        UserEntity user = (UserEntity) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect("/login");
            return false;
        }
        if (!Objects.equals(user.getUserRole(), UserRoleEnums.ADMIN.getValue())) {
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) {
    }

}
