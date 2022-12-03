package com.example.chatonline.controller.menu;

import com.example.chatonline.dao.UserIn;
import com.example.chatonline.entity.File.Listen_file;
import com.example.chatonline.entity.friend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class Menu {
    /**
     * 菜单
     */
    @Autowired
    UserIn userIn;
    //跳转聊天页面
    @GetMapping("/skip_chat")
    public String skip_chat(HttpServletRequest request,Map map){
        HttpSession session = request.getSession();
        String id = (String)session.getAttribute("user");
        //保存好友列表
        List<friend> lists = userIn.sf(id);
        map.put("lists", lists);
        userIn.update_is_login("1",id);
        return "chat";

    }
    //好友页面
    @GetMapping("/user_friends")
    public String user_friends(HttpServletRequest request, Map map){
        HttpSession session = request.getSession();
        String id = (String)session.getAttribute("user");
        //保存好友列表
        List<friend> lists = userIn.sf(id);
        userIn.update_is_login("1",id);
        map.put("num",userIn.num_login());
        map.put("lists", lists);


        return "friends_controller";
    }

    //跳转添加页面
    @GetMapping("/skip_add")
    public String skip_add(HttpServletRequest req, Map<String,Object> map){
        HttpSession session = req.getSession();
        String id = (String)session.getAttribute("user");
        //得到用户申请好友请求
        List<friend> Friend = userIn.userRequest(id);
        map.put("friend",Friend);
        userIn.update_is_login("1",id);
        return "addfriend";
    }
    //传输文件
//    @GetMapping("/skip_file")
//    public String skip_file(HttpServletRequest req,Map<String,List<Listen_file>> map){
//        HttpSession session = req.getSession();
//        String id = (String)session.getAttribute("user");
//        userIn.update_is_login("1",id);
//        List<Listen_file> listen_files = userIn.Listen_name_is_null(id);
////        req.setAttribute("file_is_true",listen_files);
//        map.put("fileistrue",listen_files);
//        return "file";
//    }

    /**
     * 分组
     * @param req
     * @param map
     * @return
     */
    @GetMapping("/skip_group")
    public String skip_group(HttpServletRequest req,Map<String,Object> map){
        String user = (String)req.getSession().getAttribute("user");
//        List<friend> friend = userIn.select_gourp(user, "好友");
//        List<friend> family = userIn.select_gourp(user, "家人");
//        List<friend> person = userIn.select_gourp(user, "陌生人");
//        System.out.println(friend);
//        for(com.example.chatonline.entity.friend i:friend){
//            System.out.println(i.getId());
//        }
//        map.put("fri",friend);
//        map.put("fam",family);
//        map.put("per",person);
        userIn.update_is_login("1",user);
        //String group = userIn.getGroup(user);
        List<String> list = userIn.getGroup1(user);
        map.put("lists",list);
        return"group";
    }



}
