package com._project.springboot_backend;
import java.util.List;       
import java.util.ArrayList; 

//这个是返回给前端的统一的结构
public class DtoRes {

//全局的类
public class Global_text{
    String un;
    String telephone;
    String remarks;
    String address;
    String totals;
    String back_img;
    String head_img;
}
//每个卡片的类
public class Each_card {
    String id;
    String un;
    String title;
    String text;
    String img;
    
}


    //是否成功，0失败1成功
    int code;
    //出现什么问题的详细信息，如账号不存在，密码错误等
    String error_msg;
    //登录成功后返回给前端对应的全局信息和每一段图片和文本的信息
    Global_text global_text;
    Each_card each_card;
    //列表，放每张图片
    List<Each_card> lst_Each_cards;

    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }


    public String getError_msg() {
        return error_msg;
    }
    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }


    public Each_card getaddress_each_text() {
        return each_card;
    }
    public void setaddress_each_text(List<Each_card> lst)
     {
        this.lst_Each_cards=lst;
    }


    public Global_text getaddress_global_json() {
        return global_text;
    }
    public void setaddress_global_json(Global_text gt) {
        this.global_text=gt;
    }    
}

