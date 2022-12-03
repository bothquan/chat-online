package com.example.chatonline.pojo.login;

public class groupfile {
    private String groupname;
    private String typename;
    private String type;


    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "groupfile{" +
                "groupname='" + groupname + '\'' +
                ", typename='" + typename + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
