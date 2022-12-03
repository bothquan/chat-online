package com.example.chatonline.controller.ajax;

import com.example.chatonline.dao.UserIn;
import com.example.chatonline.entity.friend;
import com.example.chatonline.pojo.login.responedadd1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

//好友添加待确定
@Controller
public class responed_add {
    @Autowired
    UserIn dao;
    @Autowired
    responedadd1 re;
    @PostMapping("/responed_add")
    public String responedaddtext(Map<String,Object> map,HttpServletRequest req){

       HttpSession session = req.getSession();
        String user = (String)session.getAttribute("user");
        List<friend> Friend = dao.userRequest(user);
//        if(Friend.size()>0){
//        map.put("red_clock","ok");
//        }
        map.put("friend",Friend);
        return "friendManage::table_add";

    }

}
