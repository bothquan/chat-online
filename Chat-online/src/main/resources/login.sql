-- 创建数据库
create database if not exists chat;

use chat;
-- 创建表
create table if not exists users(
    id varchar(20),
    password varchar(20),
    is_exist varchar(1),
    is_login varchar(1),
    `group`  varchar(10)
)
-- 创建群与id表
create table if not exists idWithGroup(
    id varchar(20),
    groupid varchar(20)
)

-- 登录异常归位
update users set `is_login`='0';