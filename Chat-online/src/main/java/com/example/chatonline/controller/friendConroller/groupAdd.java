package com.example.chatonline.controller.friendConroller;

import com.example.chatonline.dao.UserIn;
import com.example.chatonline.pojo.login.IdAndGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
//创建群
@Controller
public class groupAdd {
    @Autowired
    UserIn dao;
    @PostMapping("/group_add")
    public String group_add(String groupname, String adduser, HttpServletRequest req){
        //dao.Update(groupname,adduser);
        //看是不是已经有了
        IdAndGroup isture = dao.getListOfGroupAndId(adduser, groupname);
        if(isture == null){
            //如果表中没有就插入
         dao.setTableIdWithGroup(adduser,groupname);
        }
        dao.CreateGroup(groupname);
        //判断用户在不在群中，如果在就不添加

        dao.setGroup(groupname,adduser);
        return "redirect:/skip_manage";
    }
}
