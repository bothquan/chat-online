package com.example.chatonline.controller.menu;

import com.example.chatonline.dao.UserIn;
import com.example.chatonline.entity.File.Listen_file;
import com.example.chatonline.entity.friend;
import com.example.chatonline.pojo.login.groupfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class newMenu {
    @Autowired
    UserIn dao;
    //聊天
    @GetMapping("/skip_chat1")
    public String chat(HttpServletRequest req, Map<String,Object> map){
        HttpSession session = req.getSession();
        String user = (String)session.getAttribute("user");
        dao.update_is_login("1",user);
            //保存好友列表
//            List<friend> lists = dao.sf(user);
        List<friend> onlineFrients = dao.getOnlineFrients(user);
            map.put("lists", onlineFrients);
            map.put("num",dao.num_login());
        return "chat1";
    }
    //好友管理
    @GetMapping("/skip_manage")
    public String skip_manage(HttpServletRequest req,Map<String,Object> map){
        HttpSession session = req.getSession();
        String user = (String)session.getAttribute("user");
        dao.update_is_login("1",user);
        //List<friend> lists = dao.sf(user);
        List<friend> friend = dao.select_gourp(user, "好友");
        List<friend> family = dao.select_gourp(user, "家人");
        List<friend> person = dao.select_gourp(user, "陌生人");
        map.put("friend",friend);
        map.put("family",family);
        map.put("person",person);
        return "friendManage";
    }
    //传输文件
    @GetMapping("/skip_file1")
    public String skip_file(HttpServletRequest req,Map<String,List<Listen_file>> map){
        HttpSession session = req.getSession();
        String id = (String)session.getAttribute("user");
        dao.update_is_login("1",id);
        List<Listen_file> listen_files = dao.Listen_name_is_null(id);
        map.put("fileistrue",listen_files);
        return "newFile";
    }
//   群文件菜单
    @GetMapping("/skip_groupfile")
    public String skip_groupfile(HttpServletRequest req){
        String userid = (String)req.getSession().getAttribute("user");
        dao.update_is_login("1",userid);
        List<String> getidingroup = dao.getidingroup(userid);
        List<groupfile> list = new ArrayList();
        for(String index:getidingroup){
            //index table name
            List<groupfile> getgroupfile = dao.getgroupfile(index, userid);
            getgroupfile.size();
            if(getgroupfile.size()>0){
                getgroupfile.get(0).setGroupname(index);
            list.add(getgroupfile.get(0));
            }
        }

        req.setAttribute("fileistrue",list);

        return "groupfile";
    }

}
