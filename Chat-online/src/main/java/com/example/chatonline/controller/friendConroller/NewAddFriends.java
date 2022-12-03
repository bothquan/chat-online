package com.example.chatonline.controller.friendConroller;

import com.example.chatonline.dao.UserIn;
import com.example.chatonline.entity.friend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

//加好友
@Controller
public class NewAddFriends {
    @Autowired
    UserIn dao;
    @PostMapping("/search_user")
    public String addFriends(String username,Map<String,Object> map, HttpServletRequest req){
//        保存好友
        HttpSession session = req.getSession();
        String user = (String)session.getAttribute("user");
//        List<friend> lists = dao.sf(user);
//        map.put("lists",lists);
        List<friend> friend = dao.select_gourp(user, "好友");
        List<friend> family = dao.select_gourp(user, "家人");
        List<friend> person = dao.select_gourp(user, "陌生人");
        map.put("friend",friend);
        map.put("family",family);
        map.put("person",person);
        /****/
        String exist = dao.getId(username);
        String friendIsExist = dao.searchisfriend(user,username);
        if(exist==null){
            map.put("search","查询用户不存在");
        }else if(friendIsExist==null){
            //用户存在，不是好友
            //添加状态，带目标确定好友关系，数据库属性friendstatus值加一
            map.put("search",1);
            map.put("ID",username);
        }else{//存在且是好友状态
            //查看status状态
            String status = dao.SelectFs(user,username);
            if(status.equals("1")){
                map.put("search","正在添加");
            }else if(status.equals("2")){
                map.put("search","用户已经为好友");
            }
        }
        return "friendManage";
    }

}
