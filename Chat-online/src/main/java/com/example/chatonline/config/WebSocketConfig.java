package com.example.chatonline.config;


import com.example.chatonline.pojo.login.Onlinenum;
import com.example.chatonline.pojo.login.addfriend;

import com.example.chatonline.pojo.login.login_info;
import com.example.chatonline.pojo.login.responedadd1;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class WebSocketConfig {
    @Bean
    //注入Ser..bean对象,自动注册使用了@ServerEndpoint注解的bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }

    @Bean
    //登录判断
    public login_info login_info(){
        return new login_info();
    }
    @Bean
    public addfriend addfriend(){
        return new addfriend();
    }
    @Bean
    public responedadd1  responedadd1(){
        return new responedadd1();
    }
    @Bean
    public Onlinenum onlinenum(){
        return new Onlinenum();
    }
}
