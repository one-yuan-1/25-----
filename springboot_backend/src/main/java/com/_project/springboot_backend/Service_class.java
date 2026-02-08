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
                    dtoRes.setError_msg("改账号不存在");
                    return dtoRes;
                }

                //有这个账号，继续判断密码是否正确
                String dbpw = rs.getString("password");
                if(pw.equals(dbpw)){
                    dtoRes.setCode(1);
                    dtoRes.setAddressImg(rs.getString("address_img"));
                    dtoRes.setAddressJson(rs.getString("address_json"));
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
        String url = "jdbc:mysql://localhost:3306/springboot_db?useSSL=false&serverTimezone=UTC";
        try(
            Connection conn = DriverManager.getConnection(url,"root","root");
            Statement st = conn.createStatement();
        ){ 
            //先判断数据库里有没有这个账号，有就返回对应结果
            String sql_check="";
            String sql_insert="";
            try(java.sql.ResultSet rs = st.executeQuery(sql_check);){
                if(rs.next()){
                    //有这个账号，返回对应结果
                    dtoRes.code=0;
                    dtoRes.error_msg="账号已存在";
                    return dtoRes;
                }
            }
            catch(SQLException e){
                System.err.println("查询失败");

            }

            //没有就插入数据库，返回对应结果
            try (PreparedStatement ps = conn.prepareStatement(sql_insert)){
                ps.setString(1, un);
                ps.setString(2, pw);
                ps.setString(3, "default_address");
                ps.executeUpdate();
                dtoRes.code=1;
                dtoRes.error_msg="注册成功";

            } catch (Exception e) {
                System.err.println("插入失败");
            }

        }catch(SQLException e){
            System.err.println("数据库连接失败");
        }       

        return dtoRes;
    }


}
