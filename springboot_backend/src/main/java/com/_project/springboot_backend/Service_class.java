package com._project.springboot_backend;


import java.sql.Connection;
import java.sql.Statement;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import com._project.springboot_backend.DtoRes;

@Service
public class Service_class {


    //处理登录请求
    DtoRes login(String un,String pw){
        DtoRes dtoRes = new DtoRes();
        //先连接数据库
        String url = "jdbc:mysql://localhost:3306/25sql?useSSL=false&serverTimezone=UTC";
        try(
            Connection conn = DriverManager.getConnection(url,"root","root");
            Statement st = conn.createStatement();
        ){ 

            //先判断数据库里有没有这个账号，没有就返回对应结果
            String sql_check="SELECT * FROM un_pw WHERE username = ?";
            try(
                PreparedStatement ps = conn.prepareStatement(sql_check);
            ){
                ps.setString(1, un);
                try(java.sql.ResultSet rs = ps.executeQuery();){
                if(!rs.next()){
                    //没有这个账号，返回对应结果
                    dtoRes.setCode(0);
                    dtoRes.setError_msg("该账号不存在");
                    return dtoRes;
                }

                //有这个账号，继续判断密码是否正确
                String dbpw = rs.getString("password");
                if(pw.equals(dbpw)){
                    dtoRes.setCode(1);
                    dtoRes.setaddress_each_text(rs.getString("address_each_text"));
                    dtoRes.setaddress_global_json(rs.getString("address_global_json"));
                }
                else{
                    dtoRes.setCode(0);
                    dtoRes.setError_msg("密码错误");
                }
                return dtoRes;

                } catch (Exception e) {
                    System.err.println("查询失败，出现异常："+e.getMessage());
                }




            }
            catch(SQLException e){
                System.err.println("查询失败，出现异常："+e.getMessage());

            }

        return dtoRes;
        }catch(Exception e){
            System.err.println("数据库连接失败，出现异常："+e.getMessage());
            return dtoRes;
        }
    }



    //处理注册请求
    DtoRes register(String un,String pw){
        DtoRes dtoRes = new DtoRes();
        //先连接数据库
        String url = "jdbc:mysql://localhost:3306/25sql?useSSL=false&serverTimezone=UTC";
        String sql_check="SELECT * FROM un_pw WHERE username = ?";
        String sql_insert="INSERT INTO un_pw (username,password,address_img,address_json) VALUES (?,?,?,?)";
        try(
            Connection conn = DriverManager.getConnection(url,"root","root");
            Statement st = conn.createStatement();
            PreparedStatement ps_check = conn.prepareStatement(sql_check);
            PreparedStatement ps_insert = conn.prepareStatement(sql_insert)
        ){ 
            //先判断数据库里有没有这个账号，有就返回对应结果
            ps_check.setString(1, un);
            try(
                java.sql.ResultSet rs = ps_check.executeQuery();                
            ){
                if(rs.next()){
                    //有这个账号，返回对应结果
                    dtoRes.setCode(0);
                    dtoRes.setError_msg("该账号已存在");
                    return dtoRes;
                }
                //没有就插入数据库，返回对应结果
                ps_insert.setString(1, un);
                ps_insert.setString(2, pw);
                ps_insert.setString(3, "default_address");
                ps_insert.setString(4, "default_address");
                ps_insert.executeUpdate();
                dtoRes.setCode(1);
            } catch (Exception e) {
                System.err.println("插入失败"+e.getMessage());
            }

            }
            catch(SQLException e){
                System.err.println("查询失败"+e.getMessage());

            }

       

        return dtoRes;
    }


}
