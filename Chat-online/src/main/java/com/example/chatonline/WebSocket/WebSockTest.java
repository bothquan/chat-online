package com.example.chatonline.WebSocket;


import com.example.chatonline.dao.UserIn;
import com.example.chatonline.entity.Message.Message;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @ServerEndPoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端，
 * 注解的值将被用于监听用户连接的终端访问URL地址，客户端可以通过这个URL连接到websocket服务器端
 */
@ServerEndpoint(value = "/websocket",configurator = GetHttpSessionConfigurator.class)
@Component
public class WebSockTest {
    //数据库

    private static UserIn  userIn;

    private static int onlineCount=0;
//    private static CopyOnWriteArrayList<WebSockTest> webSocketSet=new CopyOnWriteArrayList<WebSockTest>();
    //对应每个客户端对应的WebSockTest
    private static Map<String,WebSockTest> webSocketSet=new ConcurrentHashMap<>();//并发

    //Session对象,通过改对象可以发送消息给指定的用户
    private Session session;
    //声明HttpSession,在其中存储了用户名(这里不能直接的得到Session域)
    private HttpSession httpSession;

    //解决websocket多实例问题
    @Autowired
    public  void setUserIn(UserIn userIn) {
        WebSockTest.userIn = userIn;
    }

    @OnOpen
    public void onOpen(Session session,EndpointConfig config){
        //得httpSession，来获得用户id
        HttpSession httpSession=(HttpSession)config.getUserProperties().get(HttpSession.class.getName());
        this.httpSession = httpSession;
        this.session=session;
        String id = (String)httpSession.getAttribute("user");
        //当前对象存储到容器中
        if(!webSocketSet.containsKey(id)){
            webSocketSet.put(id,this);
            addOnlineCount();//在线加一
        }
        System.out.println("有新连接加入！当前在线人数为"+getOnlineCount());

    }

    @OnClose
    public void onClose(){
        //System.out.println("session+ "+httpSession.getId());
        //设置登录状态为为登录
        String id = (String)httpSession.getAttribute("user");
        webSocketSet.remove(id);
        subOnlineCount();
        userIn.update_exit_is_login("0",id);
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }


    @OnMessage
    //接收到客户端的数据时被调用
    public void onMessage(String message,Session session){
        //System.out.println("来自客户端的消息："+message);
        try {

            //将message转化为Message对象
            ObjectMapper mapper = new ObjectMapper();
            Message mess = mapper.readValue(message, Message.class);
            //获取要接收消息的用户
            String toName = mess.getToName();
            String data = mess.getMessage();
            String type = mess.getType();
            //获取当前登录的用户;
            String id = (String) httpSession.getAttribute("user");
            //获取给指定用户消息格式的数据
            //---
            //发送数据，要获得接收用户的webSocketTest对象
            System.out.println(type+data);
            if(type.equals("person")){
                String block = userIn.get_block(toName, id);
                if(block.equals("1")){//拉黑就不发送
                    return;
                }
                webSocketSet.get(toName).session.getBasicRemote().sendText(data);
            }else if(type.equals("group")){
                List<String> groupId = userIn.getGroupId(toName);
                //List<WebSockTest> list = null;
                for(String list2:groupId){
//                    System.out.println(list2);
//                    list.add(webSocketSet.get(list2));
                    if(!list2.equals(id)){
                        //System.out.println();
                    webSocketSet.get(list2).session.getBasicRemote().sendText(data);

                    }

                }
                //        群发消息
//                for (WebSockTest item:list){
//                    try {
//                        item.sendMessge(message);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                        continue;
//                    }
//                }
            }// if(toName.equals("group1"))
//            System.out.println(toName);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    //二进制
    @OnMessage
    public void onMessage(Session session,byte[] bytes){

    }
    @OnError
    public void onError(Session session,Throwable throwable){
        System.out.println("发生错误！");
        throwable.printStackTrace();
    }
    //   下面是自定义的一些方法
    public void sendMessge(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    public static synchronized int getOnlineCount(){
        return onlineCount;
    }
    public static synchronized void addOnlineCount(){

        WebSockTest.onlineCount++;
    }
    public static synchronized void subOnlineCount(){
        WebSockTest.onlineCount--;
    }
}

