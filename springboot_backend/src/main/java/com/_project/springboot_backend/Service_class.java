package com._project.springboot_backend;


import java.sql.Connection;
import java.sql.Statement;

import org.apache.ibatis.exceptions.PersistenceException;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.springframework.beans.factory.annotation.Autowired;

import com._project.springboot_backend.Repo;
import com._project.springboot_backend.DTO.DtoRes;
import com._project.springboot_backend.DTO.DtoUnPw;
import java.util.List;
import java.util.Objects;  

@Service
public class Service_class {
    //注入依赖(repo接口)
    private final Repo repo;
    public Service_class(Repo repo){
        this.repo=repo;
    }

    //处理登录请求
    DtoRes login(String un,String pw){
        DtoRes dtoRes = new DtoRes();
        //先判断数据库里有没有这个账号，没有就返回对应结果
        DtoUnPw dtoUnPw;
        try{
        dtoUnPw=repo.find_pw(un);
        }catch(PersistenceException e){
            System.err.println(e);
            dtoRes.setCode(0);
            dtoRes.setError_msg("当前业务繁忙，请稍后再试");
            return dtoRes;
        }
        //外部记录该账户是否存在以及密码
        boolean is_exist=false;
        String real_pw="";
        //存在该账户
        //Objects.equals(dtoUnPw.getUn(), un)内部处理null了更安全,但对象本身仍可能为null
        if(dtoUnPw != null &&Objects.equals(dtoUnPw.getUn(), un)){
            //存一下
            is_exist=true;
            real_pw=dtoUnPw.getPW();
        }

            

            //没有这个账号，返回对应结果
            if(!is_exist){
                dtoRes.setCode(0);
                dtoRes.setError_msg("该账号不存在");
                return dtoRes;
            }


            //有这个账号，继续判断密码是否正确

            
            if(pw.equals(real_pw)){
                //密码正确就返回对应的两个表的两个json数据
                //先查放全局信息和放每个卡片信息的数据库再去放进结果类里返回
                dtoRes.setCode(1);
                dtoRes.setLst_Each_cards(
                    repo.find_each_card(un)
                );
                dtoRes.setGlobal_text(
                    repo.find_global_text(un)
                );
            }else{
                //不正确
                dtoRes.setCode(0);
                dtoRes.setError_msg("密码错误");
                return dtoRes;
            }
                return dtoRes;

    }



    //处理注册请求
    DtoRes register(String un,String pw){
        DtoRes dtoRes = new DtoRes();

        //先判断数据库里有没有这个账号，有就返回对应结果
        DtoUnPw dtoUnPw;
        try{
        dtoUnPw=repo.find_pw(un);
        }catch(PersistenceException e){
            System.err.println(e);
            dtoRes.setCode(0);
            dtoRes.setError_msg("当前业务繁忙，请稍后再试");
            return dtoRes;
        }
        //外部记录该账户是否存在
        boolean is_exist=false;


            //存在该账户
            if(dtoUnPw != null &&Objects.equals(dtoUnPw.getUn(), un)){
                //存一下
                is_exist=true;
            }

        
      
            if(is_exist){
                //有这个账号，返回对应结果
                dtoRes.setCode(0);
                dtoRes.setError_msg("该账号已经存在");
                return dtoRes;
            }

        
        //没有就插入数据库，返回对应结果
        try{
        repo.insert_un_pw(un, pw);
        repo.insert_default_global(un);
        }catch(PersistenceException e){
            System.err.println(e);
            dtoRes.setCode(0);
            dtoRes.setError_msg("当前业务繁忙，请稍后再试");
            return dtoRes; 
        }

        dtoRes.setCode(1);



        return dtoRes;
    }


}
