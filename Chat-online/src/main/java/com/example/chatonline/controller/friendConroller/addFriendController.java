package com.example.chatonline.controller.friendConroller;


import com.example.chatonline.dao.UserIn;
import com.example.chatonline.entity.friend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class addFriendController {
    @Autowired
    UserIn userIn;
    /**
     * 添加好友
     */

    //查询好友
    @PostMapping("/searchFrined")
    public String searchFrined(String id,Map<String,Object>map,HttpServletRequest req){
        HttpSession session = req.getSession();
        //当前登录的用户
        String user = (String)session.getAttribute("user");
        //得到用户请求,确认或者待对方确认
        List<friend> Friend = userIn.userRequest(user);
        map.put("friend",Friend);

        String exist = userIn.getId(id);
        String friendIsExist = userIn.searchisfriend(user,id);
        if(exist==null){//如果查询用户不存在
            map.put("search","查询用户不存在");
        }else if(friendIsExist==null){
            //用户存在，不是好友
            //添加状态，带目标确定好友关系，数据库属性friendstatus值加一
            map.put("search",1);
            map.put("ID",id);
        }else{//存在且是好友状态
            //查看status状态
            String status = userIn.SelectFs(user,id);
            if(status.equals("1")){
                map.put("search","正在添加");
            }else if(status.equals("2")){
                map.put("search","用户已经为好友");
            }
        }
        return "addfriend";
    }
    //点击添加好友
    @PostMapping("/clickadd/{id}")
    public String clickadd(@PathVariable("id") String friend_id,@RequestParam("group") String group ,HttpServletRequest req, Map<String,Object> map){

        HttpSession session = req.getSession();
        String user = (String)session.getAttribute("user");
//        String group = req.getParameter("group");

        //非好友
        if(userIn.SelectFs(user,friend_id)==null){
            //当前用户添加是1
            userIn.intsertFriend(user,friend_id,"1",group);
            //待添加是0
            userIn.intsertFriend(friend_id,user,"0",group);
        }else if(userIn.SelectFs(user,friend_id).equals("0")){//待用户确定
            //称为好友状体码为2
            userIn.updateStatus(user,friend_id);
            userIn.updateStatus(friend_id,user);
            userIn.SelectGroup(user,friend_id,group);
            //初始化拉黑默认值为0
            userIn.set_block(user,friend_id,"0");
            userIn.set_block(friend_id,user,"0");
        }

        return "redirect:/skip_manage";
    }
    //拒绝添加好友
    @PostMapping("/clickdelete/{id}")
    public String clickdelete(@PathVariable("id") String friend_id,HttpServletRequest req,Map<String,Object> map){
        HttpSession session = req.getSession();
        String user = (String)session.getAttribute("user");
        userIn.deleteFriend(user,friend_id);
        userIn.deleteFriend(friend_id,user);
        return "redirect:/skip_add";
    }
    //好友删除
    @DeleteMapping("/delete_friends/{id}")
    public String deleteFriend(@PathVariable("id") String id,HttpServletRequest req){

        HttpSession session = req.getSession();
        String user = (String)session.getAttribute("user");
        userIn.deleteFriend(user,id);
        userIn.deleteFriend(id,user);

        return "redirect:/skip_manage";
    }

    /**
     *好友拉黑
     */
    @PostMapping("/block/{id}")
    public String blockFriend(@PathVariable("id") String id,HttpServletRequest req){
        HttpSession session = req.getSession();
        String user = (String)session.getAttribute("user");
        //得到拉黑状态
        String block_status = userIn.get_block(user, id);
        //如果是1置0，0置1
        if(block_status.equals("0")){
            userIn.set_block(user,id,"1");
        }else{
        userIn.set_block(user,id,"0");}
        return "redirect:/skip_manage";

    }

}
