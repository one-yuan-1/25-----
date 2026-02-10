package com._project.springboot_backend;


//这个是返回给前端的统一的结构
public class DtoRes {
    //是否成功，0失败1成功
    int code;
    //出现什么问题的详细信息，如账号不存在，密码错误等
    String error_msg;
    //登录成功后返回给前端对应的全局信息和每一段图片和文本的信息
    String address_each_text;
    String address_global_json;
    

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
    public String getaddress_each_text() {
        return address_each_text;
    }
    public void setaddress_each_text(String address) {
        this.address_each_text = address;
    }
    public String getaddress_global_json() {
        return address_global_json;
    }
    public void setaddress_global_json(String address) {
        this.address_global_json = address;
    }    
}
