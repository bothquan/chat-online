package com.example.chatonline.controller.ajax;

import com.example.chatonline.dao.UserIn;

import com.example.chatonline.entity.Users;
import com.example.chatonline.pojo.login.addfriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

//添加好友
@RestController
public class addfriend1 {
    @Autowired
    UserIn dao;
    @Autowired
    addfriend addfriend;
    //查询好友
    @GetMapping("/addfriend1")
    public addfriend addfriendtest(Users user, HttpServletRequest req){

        System.out.println(user.getUsername());
        //看是不是存在
        String exist = dao.getId(user.getUsername());
        if(exist==null){
           addfriend.setInfo("用户不存在");
           req.getSession().setAttribute("用户不存在",1);
        }else{
            addfriend.setInfo("用户存在");
        }
        return addfriend;
    }

}
