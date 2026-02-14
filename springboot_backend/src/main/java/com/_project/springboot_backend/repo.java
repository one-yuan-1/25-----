package com._project.springboot_backend;
import java.sql.Connection;
import java.sql.Statement;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com._project.springboot_backend.DTO.DtoRes;
import com._project.springboot_backend.DTO.DtoUnPw;
import com._project.springboot_backend.DTO.DtoEach_card;
import com._project.springboot_backend.DTO.DtoGlobal_text;


import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;       



//mapper接口
@Mapper
public interface Repo {

    //根据用户名查询密码
    @Select("SELECT * FROM un_pw WHERE username = #{username}")
    DtoUnPw find_pw(String username);   

    //根据用户名查询某用户的每张卡片并返回[{},{}]
    @Select("SELECT * FROM each_card WHERE username = #{username}")
    List<DtoEach_card> find_each_card(String username);

    //根据用户名查询某用户的全局信息
    @Select("SELECT * FROM global_text WHERE username = #{Username}")
    DtoGlobal_text find_global_text(String Username);

    //插入账号密码，unpw表内容
    @Insert("INSERT INTO un_pw (username,password) VALUES ( #{username}, #{password})")
    void insert_un_pw(String username,String password);
    //插入默认，global表内容
    @Insert("INSERT INTO global_text (Username,Telephone,Remarks,Address,totals,back_img,head_img) VALUES ( #{Username},'待填入','待填入','待填入','待填入','/default/back_img.png','/default/head_img.png')")
    void insert_default_global(String Username);   

}
