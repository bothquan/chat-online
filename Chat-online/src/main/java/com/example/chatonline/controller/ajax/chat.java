package com.example.chatonline.controller.ajax;

import com.example.chatonline.dao.UserIn;
import com.example.chatonline.entity.friend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class chat {
    @Autowired
    UserIn dao;
    @GetMapping("/refreashfri")
    public String chatreFreash(HttpServletRequest req, Map<String,Object> map){
        HttpSession session = req.getSession();
        String user = (String)session.getAttribute("user");
        //保存好友列表
        List<friend> onlineFrients = dao.getOnlineFrients(user);
        map.put("lists", onlineFrients);
        map.put("num",dao.num_login());
        return "chat1::frined_id";
    }
}
