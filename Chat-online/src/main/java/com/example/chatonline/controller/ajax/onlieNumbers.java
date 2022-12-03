package com.example.chatonline.controller.ajax;

import com.example.chatonline.dao.UserIn;
import com.example.chatonline.pojo.login.Onlinenum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

//好友在线
@RestController
public class onlieNumbers {
    @Autowired
    UserIn dao;

    @Autowired
    Onlinenum num;

    @GetMapping("/auto")
    @ResponseBody
    public Onlinenum test(HttpServletRequest req){
        String user = (String)req.getSession().getAttribute("user");
        num.setNum(dao.getOnlinenum(user));
        return num;
    }
}
