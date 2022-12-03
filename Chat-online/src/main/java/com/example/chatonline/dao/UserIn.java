package com.example.chatonline.dao;


import com.example.chatonline.entity.File.Listen_file;
import com.example.chatonline.entity.File.downfilemessge;
import com.example.chatonline.entity.friend;

import com.example.chatonline.pojo.login.IdAndGroup;
import com.example.chatonline.pojo.login.downgroup;
import com.example.chatonline.pojo.login.groupfile;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface UserIn {
    //检查用户是否登录
    @Select("select is_login from users where id = #{id}")
    public String return_is_login(String id);

    //登录时，用户状态修改为登录
    @Update("update users set is_login = #{is_login} where id = #{id}")
    public void update_is_login(String is_login,String id);

    //退出时，用户登录状态为退出
    @Update("update users set is_login = #{is_login} where id = #{id}")
    public void update_exit_is_login(String is_login,String id);

    //检查注册用户是否存在
    @Select("select is_exist from users where id = #{id}")
    public String getId(String id);
    @Select("select password from users where id = #{id}")
    public String getPassword(String id);
//    注册用户创建初始条件，is_login是是否登录
    @Insert("insert into Users(id,password,is_exist,is_login) values(#{id},#{password},#{is_exist},#{is_login})")
    public void setUser(String id,String password,String is_exist,String is_login);
    //查询好友列表

    public List<friend> sf(@Param("id") String id);

    @Select("SELECT id,is_block from `${id}` where friendStatus = '2' and id in (\n" +
            "select id from users where  is_login='1' \n" +
            ")   order by id")
    public List<friend> getOnlineFrients(@Param("id") String id);
//    动态创建表
    public void creatTable(@Param("id")String id );



    /**
     * 好友操作
     */
    //统计人数
    @Select("select count(id) from users where is_login = '1'")
    public int num_login();
    //添加好友
    public void intsertFriend(@Param("id") String id,@Param("friend_id") String friend_id,String friendStatus,String group);
    //检查是不是好友
    //@Select("select id from `${id}` where id = #{fid}")
    public String searchisfriend(@Param("id") String id,@Param("friend_id") String friend_id);
    //查询FriendStatus状态
    @Select("select friendStatus from `${user}` where id = #{id}")
    public String SelectFs(String user,String id);


    //删除好友
    @Delete("delete from `${user}` where id = #{id}")
    public void deleteFriend(String user,String id);

    //得到用户请求添加或者同意
    public List<friend> userRequest(String id);

    //更新status
    @Update("update `${user}` set friendStatus = '2' where id = #{fid}")
    public void updateStatus(String user,String fid);

    @Update("update `${user}` set `group` = #{group} where id = #{fid}")
    public void SelectGroup(String user,String fid,String group);
    //好友列表拉黑，0为不拉黑，1为拉黑
    @Update("update `${user}` set is_block=#{is_block} where id = #{id}")
    public void set_block(String user,String id,String is_block);
    //查询block的status
    @Select("select is_block from `${user}` where id=#{id}")
    public String get_block(String user,String id);

    /**
     * 分组查询
     */

    public List<friend> select_gourp(String user,String group);

    /**
     * 文件
     */
    public void insertfile(String user,String filename,String type,String id);
    @Update("update `${id}` set filename='${filename}',filetype = '${type}' where id in(select id from users where `group` = '${group}')")
    public void insertfile_group(String group,String filename,String type,String id);


    public void insertpic(byte[] bytes);

    //当前用户数据库是否有文件
    @Select("select id,filename,filetype from `${user}` where filename is not null")
    public List<Listen_file> Listen_name_is_null(String user);

    //下载并清零
    @Select("select id,filename,filetype from `${user}` where id = ${friend}")
    public downfilemessge downfile(String user, String friend);
    @Select("select typename,type from `${groupid}` where id = ${userid}")
    public downgroup downfileingroup(String groupid, String userid);

    //文件清零
    @Update("update `${user}` set filename = null ,filetype=null where id = ${friend}")
    public void updatefile(String user, String friend);
    @Update("update `${user}` set filename = null ,filetype=null where id = ${friend}")
    public void updatefilegroup(String groupid,String user);
    @Select("select count(id) from `${user}` where id in (select id from users where is_login ='1')")
    public int getOnlinenum(String user);

    /**
    * 群添加
    */
    //创建群
    public void CreateGroup(String group);//String id);

//<!--    将id与传到群中-->
    @Insert("Insert into `${GroupName}`(id) values(#{id})")
    public void insertIdToGroup(String id ,String GroupName);
//   将文件插入group
    @Update("update `${GroupName}` set typename = '${filename}',`type`='${type}'")
    public void insertFileToGroup(String GroupName,String filename,String type);
////    将文件id和groupid插入到idwithgroup
//    @Insert("insert into `idwithgroup`(id,groupid) values(#{id},#{groupid})")
//    public void insertinfointoIdWithGroup(String groupid,String id);
//  将用户插入到对应的group中
    @Insert("insert into `${groupName}`(id) values(#{id})")
    public void setGroup(String groupName,String id);

//    查看用户和群的信息看是不是
    public IdAndGroup getListOfGroupAndId(String id,String Groupid);

    //如果没有就插入到idwithgroup
    @Insert("insert into idwithgroup(id,groupid) values(#{id},#{groupid})")
    public void setTableIdWithGroup(String id,String groupid);

    @Update("update users set `group` = '${group}' where id = '${id}'")
    public void Update(String group,String id);

    @Select("select `group` from users where id = ${id}")
    public String getGroup(String id);

//    查询某个用户的群
    @Select("select `groupid` from `idwithgroup` where id = #{id}")
    public List<String> getGroup1(String id);

//    群聊得到某个群
    @Select("select `id` from `${group}`")
    public List<String> getGroupId(String group);

    //群查询
    @Select("select `groupid` from `idwithgroup` where id = #{userid}")
    public List<String> getidingroup(String userid);

    //查询群文件
    @Select("select #{groupid},typename,type from `${groupid}` where id = #{userid} and typename is not null")
    public List<groupfile> getgroupfile(String groupid, String userid);
}