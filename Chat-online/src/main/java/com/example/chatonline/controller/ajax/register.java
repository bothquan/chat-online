package com.example.chatonline.controller.ajax;

import com.example.chatonline.dao.UserIn;
import com.example.chatonline.entity.Users;
import com.example.chatonline.pojo.login.login_info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;

//注册
@RestController
public class register {
    @Autowired
    UserIn dao;
    @Autowired
    login_info info;
    @PostMapping("/register_check")
    public login_info register_check(Users user){
        //存在和不存在
        String s = dao.getId(user.getUsername());
        if(StringUtils.isEmpty(s)){//为null
            info.setIn_login("可注册当前用户");
        }
        else{
            info.setIn_login("当前名字已注册");
        }
        return info;
    }
}
