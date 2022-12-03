package com.example.chatonline.controller.ajax;

import com.example.chatonline.dao.UserIn;
import com.example.chatonline.entity.Users;
import com.example.chatonline.pojo.login.login_info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;

//登录
@RestController
public class login {
    @Autowired
    UserIn dao;
    @Autowired
    login_info info;
    @PostMapping("/check_username")
    public login_info check_username(Users user){
        String s = dao.return_is_login(user.getUsername());
        if(StringUtils.isEmpty(s)){//为null
            info.setIn_login("当前用户不存在");
        }
        else if(!StringUtils.isEmpty(s)&&s.equals("1")){
            info.setIn_login("当前用户已经登录");
        }else{
            info.setIn_login("存在");
        }
        return info;
    }
    @PostMapping("/check_password")
    public login_info check_password(Users user){
        //System.out.println(123123);
        String password = dao.getPassword(user.getUsername());
        if(password.equals(user.getPassword())){
            //账号正确
            info.setIn_login("ture");
        }else{
            info.setIn_login("false");
        }

        return info;
    }
}
