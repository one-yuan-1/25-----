package com._project.springboot_backend;


//这个是返回给前端的统一的结构
public class DtoRes {
    //是否成功，0失败1成功
    int code;
    //出现什么问题的详细信息，如账号不存在，密码错误等
    String error_msg;
    //登录成功后返回给前端对应的文本和图片的地址
    String address;

}
