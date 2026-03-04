package com._project.springboot_backend.REPO;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com._project.springboot_backend.DTO.DtoEach_card;
import com._project.springboot_backend.DTO.DtoGlobal_text;
import com._project.springboot_backend.DTO.DtoUnPw;

@Repository
//mapper接口
@Mapper
public interface Repo {

    //根据用户名查询密码
    @Select("SELECT * FROM un_pw WHERE username = #{username}")
    DtoUnPw find_pw(String username);

    //根据用户名查id
    @Select("SELECT id FROM un_pw WHERE username = #{username}")
    int find_id(String username);

    //根据用户名查询某用户的每张卡片并返回[{},{}]
    @Select("SELECT * FROM each_card WHERE username = #{username}")
    List<DtoEach_card> find_each_card(String username);

    //根据用户名查询某用户的全局信息
    @Select("SELECT * FROM global_text WHERE username = #{Username}")
    DtoGlobal_text find_global_text(String Username);
    //gengxin信息，global表内容
    @Update("UPDATE global_text SET username = #{username},telephone = #{telephone},remarks = #{remarks},address = #{address} WHERE username = #{username}")
    void update_global_text(String username,String telephone,String remarks,String address);

    //插入账号密码，unpw表内容
    @Insert("INSERT INTO un_pw (username,password) VALUES ( #{username}, #{password})")
    void insert_un_pw(String username,String password);

    //插入默认，global表内容
    @Insert("INSERT INTO global_text (Username,Telephone,Remarks,Address,totals,back_img,head_img) VALUES ( #{Username},'待填入','待填入','待填入','待填入','/default/back_img.png','/default/head_img.png')")
    void insert_default_global(String Username);   

    //查询each_card表里某用户的所有卡片,@Param()在只有一个参数时可以省略
    @Select("SELECT * FROM each_card WHERE username = #{un}")
    List<DtoEach_card> findByUsername(@Param("un") String un);

    //插入每张卡片，each_card表内容
    @Insert("INSERT INTO each_card (username,id,title,text) VALUES (#{un},#{id},#{title},#{text})")
    void insert_each_card(@Param("un") String username,@Param("id") int id,@Param("title") String title,@Param("text") String text);

    //删除每张卡片，each_card表内容
    @Delete("DELETE FROM each_card WHERE username = #{un} AND id = #{id}")
    void del_each_card(@Param("un") String username,@Param("id") int id);
}
