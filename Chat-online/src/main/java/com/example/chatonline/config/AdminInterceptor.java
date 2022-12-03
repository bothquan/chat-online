package com.example.chatonline.config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String user = (String)request.getSession().getAttribute("user");
        if(user!=null){//没注册
            return true;
        }
        response.sendRedirect(request.getContextPath()+"/");
        return false;
    }
}
