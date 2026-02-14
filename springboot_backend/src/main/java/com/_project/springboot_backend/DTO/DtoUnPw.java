package com._project.springboot_backend.DTO;


//这个账号密码的数据库un_pw表对应的类
public class DtoUnPw {
    int id;
    String username;
    String password;

    //mybatis的需要的无参构造
    public DtoUnPw(){}

    public int getId(){
        return this.id;
    }
    public void setId(int new_id){
        this.id=new_id;
    }

    public String getUn(){
        return this.username;
    }
    public void SetUn(String new_un){
        this.username=new_un;
    }

    public String getPW(){
        return this.password;
    }
    public void SetPW(String new_pw){
        this.password=new_pw;
    }
}
