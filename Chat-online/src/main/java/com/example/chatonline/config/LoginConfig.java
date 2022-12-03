package com.example.chatonline.config;


import com.example.chatonline.config.AdminInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class LoginConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(new AdminInterceptor());
        registration.addPathPatterns("/**");
        registration.excludePathPatterns("/",
                "/css/**",
                "/images/**",
                "/webjars/**",
                "/js/**",
                "/check_username",
                "/check_password",
                "/register_check",
                "/checkUser",
                "/register1",
                "/search_user"
                );//不拦截
    }
}
