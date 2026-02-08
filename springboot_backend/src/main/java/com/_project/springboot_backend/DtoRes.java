package com._project.springboot_backend;


//这个是返回给前端的统一的结构
public class DtoRes {
    //是否成功，0失败1成功
    int code;
    //出现什么问题的详细信息，如账号不存在，密码错误等
    String error_msg;
    //登录成功后返回给前端对应的文本和图片的地址
    String address_img;
    String address_json;
    

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
    public String getAddressImg() {
        return address_img;
    }
    public void setAddressImg(String address) {
        this.address_img = address;
    }
    public String getAddressJson() {
        return address_img;
    }
    public void setAddressJson(String address) {
        this.address_img = address;
    }    
}
