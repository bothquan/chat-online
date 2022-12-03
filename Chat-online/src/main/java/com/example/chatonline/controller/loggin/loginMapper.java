package com.example.chatonline.controller.loggin;

import com.example.chatonline.dao.UserIn;

import com.example.chatonline.entity.Users;
import com.example.chatonline.entity.friend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.*;
import java.util.List;
import java.util.Map;

@Controller
public class loginMapper {
    @Autowired
    UserIn userIn;

    //初始登录页面
    @RequestMapping("/")
    public String tologgin(){
        return "index";
    }

    /**
     * 账号登录
     * @param id
     * @param password
     * @return
     */
    @PostMapping("/checkUser")
    public String to(@RequestParam("username") String id,
                     @RequestParam("password") String password,
                     Map<String,Object> map,
                     HttpServletRequest request){

        if(!StringUtils.isEmpty(userIn.return_is_login(id))&&userIn.return_is_login(id).equals("1")){

            map.put("password_msg","账号不能重复登录");
            return "index";
        }
        if(password.equals(userIn.getPassword(id))){//正确
            //保存用户登录的id，聊天得到本id
            HttpSession httpSession = request.getSession();
            httpSession.setAttribute("user", id);//如果成功保存id,主要为socket准备
            //修改登录状态,为登录
            userIn.update_is_login("1",id);

//            return "friends_controller";
                return "redirect:/skip_chat1";
        }else{//密码错误
            map.put("password_msg","输入错误");
            return "index";
        }
    }


    /**
     * 注册设置
     * @return
     */
    //返回登录
    @GetMapping("/return_login")
    public String return_login(){
        return "login";
    }
    //跳转注册页面
    @GetMapping("/trunregister")
    public String trun_register(){
        return "register";
    }
    //注册
    @PostMapping("/register1")
    public String register(Users user,Map<String,Object> map){
        String id = user.getUsername();
        String password = user.getPassword();
        //查询账号是否存在，
        String is_exist = userIn.getId(id);

        if(!StringUtils.isEmpty(is_exist)){//存在
            //request 消息保存
            map.put("register_msg","账号已注册");
            return "index";
        }else { //不存在
            //检查账号密码是不是为空
            if (StringUtils.isEmpty(id) || StringUtils.isEmpty(password)) {//其中有个为空
                map.put("register_msg", "账号或者密码不能为空哦!");
                return "index";
            } else {//不为空

                userIn.setUser(id, password,"1","0");
                userIn.creatTable(id);//创建该用户的好友列表（数据库）
                map.put("register_msg", "注册成功");
                return "index";
            }
        }
    }


}
