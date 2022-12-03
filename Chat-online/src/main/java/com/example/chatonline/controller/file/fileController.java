package com.example.chatonline.controller.file;

import com.example.chatonline.dao.UserIn;
import com.example.chatonline.entity.File.ClassOfFile;
import com.example.chatonline.entity.File.downfilemessge;

import com.example.chatonline.pojo.login.downgroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
public class fileController {
    @Autowired
    UserIn userIn;
    //相会传文件
    @PostMapping("/fileTransmit")
    public String file(@RequestParam("file") MultipartFile file, HttpServletRequest req) throws Exception {
        String filename = file.getOriginalFilename();//包括了后缀
        String[] str = filename.split("\\.");//分隔开来
        ClassOfFile classOfFile = new ClassOfFile();
        //将文件name和文件类型分开
        classOfFile.setFilename(str[0]);
        if(str.length>=2){
            classOfFile.setFiletype(str[1]);
        }
        //得到用户
        String user = (String)req.getSession().getAttribute("user");
        classOfFile.setId(user);
        //文件处理
        File file1 = new File("D:/filebuffer/"+filename);
        file.transferTo(file1);
        //在数据库中存储数据
        String id = req.getParameter("id");
        userIn.insertfile(id,classOfFile.getFilename(),classOfFile.getFiletype(),classOfFile.getId());
        return "redirect:/skip_file1";
    }
    //群发文件
    @PostMapping("/fileTransmit_group")
    public String file1(@RequestParam("file") MultipartFile file, HttpServletRequest req) throws Exception {
        String filename = file.getOriginalFilename();//包括了后缀
        String[] str = filename.split("\\.");//分隔开来
        ClassOfFile classOfFile = new ClassOfFile();
        //将文件name和文件类型分开
        classOfFile.setFilename(str[0]);
        if(str.length>=2){
            classOfFile.setFiletype(str[1]);
        }
        //得到用户
        String user = (String)req.getSession().getAttribute("user");
        classOfFile.setId(user);
        //文件处理
        File file1 = new File("D:/filebuffer/"+filename);
        file.transferTo(file1);
        //在数据库中存储数据
        String groupid = req.getParameter("id");

        //设置群每个用户文件
        userIn.insertFileToGroup(groupid, classOfFile.getFilename(), classOfFile.getFiletype());

        return "redirect:/skip_file1";
    }


//    下载文件
    @GetMapping("/download_file/{friendid}")
    public void download(@PathVariable("friendid") String friendid, HttpServletResponse response,HttpServletRequest req) throws IOException {
        String user = (String)req.getSession().getAttribute("user");//获取用户
        downfilemessge fileinfo = userIn.downfile(user, friendid);
        String filename = fileinfo.getFilename()+"."+fileinfo.getFiletype();
        String filepath = "D:/filebuffer/" + filename;
        File file = new File(filepath);
        userIn.updatefile(user,friendid);
        response.setContentType("application/octet-stream");//
        response.setHeader("content-type", "application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;fileName=" + filename);// 设置文件名
        byte[] buffer = new byte[1024*1024*100];
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        try {
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            OutputStream os = response.getOutputStream();
            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }
            System.out.println("success");


        } catch (Exception e) {
            e.printStackTrace();
        }finally {

            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            file.delete();

            //数据库更新
        }
        //return "redirect:/skip_file";

    }
    @GetMapping("/download_file1/{groupid}")
    public void download1(@PathVariable("groupid") String groupid, HttpServletResponse response,HttpServletRequest req) throws IOException {
        String user = (String)req.getSession().getAttribute("user");//获取用户
        System.out.println(groupid);
        downgroup fileinfo = userIn.downfileingroup(groupid,user);
        String filename = fileinfo.getTypename()+"."+fileinfo.getType();
        String filepath = "D:/filebuffer/" + filename;
        File file = new File(filepath);
        //userIn.updatefilegroup(groupid,user);
        response.setContentType("application/octet-stream");//
        response.setHeader("content-type", "application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;fileName=" + filename);// 设置文件名
        byte[] buffer = new byte[1024*1024*100];
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        try {
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            OutputStream os = response.getOutputStream();
            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }
            System.out.println("success");


        } catch (Exception e) {
            e.printStackTrace();
        }finally {

            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //file.delete();

            //数据库更新
        }
        //return "redirect:/skip_file";

    }

}
