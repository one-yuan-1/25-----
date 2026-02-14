package com._project.springboot_backend.DTO;
import java.util.List;       
import java.util.ArrayList; 
import com._project.springboot_backend.DTO.DtoEach_card;
import com._project.springboot_backend.DTO.DtoGlobal_text;
//这个是返回给前端的统一的结构
public class DtoRes {


//mybatis需要的无参构造函数
public DtoRes(){}



    //是否成功，0失败1成功
    int code;
    //出现什么问题的详细信息，如账号不存在，密码错误等
    String error_msg;
    //登录成功后返回给前端对应的全局信息和每一段图片和文本的信息
    DtoGlobal_text global_text;
    DtoEach_card each_card;
    //列表，放每张图片
    List<DtoEach_card> lst_Each_cards;

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

//注意get set方法首字母大写，是get/set + 首字母大写的字段名

    public DtoGlobal_text getGlobal_text() {
        return global_text;
    }
    public void setGlobal_text(DtoGlobal_text gt) {
        this.global_text=gt;
    }   

    public DtoEach_card getEach_card() {
        return each_card;
    }
    public void setEach_card(DtoEach_card new_c){
        this.each_card=new_c;
    }

    public List<DtoEach_card> getLst_Each_cards() {
        return lst_Each_cards;
    }
    public void setLst_Each_cards(List<DtoEach_card> lst) {
        this.lst_Each_cards=lst;
    }   
}

