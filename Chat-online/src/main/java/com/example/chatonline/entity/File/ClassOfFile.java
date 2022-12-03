package com.example.chatonline.entity.File;

public class ClassOfFile {
    String id;//好友
    String filename;//文件名字
//    byte[] file;
    String filetype;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

//    public byte[] getFile() {
//        return file;
//    }

//    public void setFile(byte[] file) {
//        this.file = file;
//    }

    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }
}
