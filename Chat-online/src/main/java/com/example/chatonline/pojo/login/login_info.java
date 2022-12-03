package com.example.chatonline.pojo.login;

public class login_info {
    private String in_login;

    public String getIn_login() {
        return in_login;
    }

    public void setIn_login(String in_login) {
        this.in_login = in_login;
    }

    @Override
    public String toString() {
        return "login_info{" +
                "in_login='" + in_login + '\'' +
                '}';
    }
}
