package com._project.springboot_backend;


import java.io.IOException;
import java.util.List;
import java.util.Objects;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;

import com._project.springboot_backend.DTO.DtoEach_card;
import com._project.springboot_backend.DTO.DtoRes;
import com._project.springboot_backend.DTO.DtoUnPw;
import com._project.springboot_backend.REPO.FileIO;
import com._project.springboot_backend.REPO.Repo;
@Service
public class Service_class {
    // 注入依赖(repo类mapper接口和文件io的接口)
    private final Repo repo;
    private final FileIO fileIO;
    public Service_class(Repo repo,FileIO fileIO){
        this.repo=repo;
        this.fileIO = fileIO;
    }
    // @Autowired
    // private Repo repo;
    // private FileIO fileIO;



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
                //先拿这个用户的id,因为文件操作是根据id来找目录的
                int un_id;
                try{
                    un_id=repo.find_id(un);
                }catch(PersistenceException e){
                    System.err.println(e);
                    dtoRes.setCode(0);
                    dtoRes.setError_msg("当前业务繁忙，请稍后再试");
                    return dtoRes;
                }
                //密码正确就返回对应的两个表的两个json数据
                //先查放全局信息和放每个卡片信息的数据库再去放进结果类里返回
                dtoRes.setCode(1);
                dtoRes.setLst_Each_cards(
                    repo.find_each_card(un)
                );
                dtoRes.setGlobal_text(
                    repo.find_global_text(un)
                );
                dtoRes.setFile_res(
                    //传入id(字符串格式)用于寻找对应目录
                    fileIO.read_image(String.valueOf(un_id))
                );
                
                //如果文件操作失败则返回错误
                if(dtoRes.getFile_res().getCode()==0){
                    dtoRes.setCode(0);
                    dtoRes.setError_msg(dtoRes.getFile_res().getError_msg());
                }
                return dtoRes;
            }
            
            else{
                //不正确
                dtoRes.setCode(0);
                dtoRes.setError_msg("密码错误");
                return dtoRes;
            }

    }






    //处理注册请求
    DtoRes register(String un,String pw){
        DtoRes dtoRes = new DtoRes();

        //先判断数据库里有没有这个账号，有就返回对应结果
        DtoUnPw dtoUnPw=null;
        try{
            dtoUnPw=repo.find_pw(un);
        }catch(PersistenceException e){
            System.err.println(e);            
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

    //处理图片上传请求
    DtoRes SaveImg(String type,String user,org.springframework.web.multipart.MultipartFile file){
        DtoRes dtoRes = new DtoRes();
        System.out.println("type:"+type+" user:"+user+" file:"+file.getOriginalFilename());
        //先检查文件是不是图片
        Tika tika = new Tika();
        try {
            String detectedType = tika.detect(file.getInputStream());
            if (!detectedType.startsWith("image/")) {
                dtoRes.setCode(0);
                dtoRes.setError_msg("上传的文件不是图片类型");
                return dtoRes;
            }
        } catch (IOException e) {
            System.err.println("文件检测异常: " + e.getMessage());
            dtoRes.setCode(0);
            dtoRes.setError_msg("业务繁忙,请稍后再试");
            return dtoRes;
        }
        //先拿这个用户的id,因为文件操作是根据id来找目录的
        int un_id=-1;
        try{
            un_id=repo.find_id(user);
        }catch(PersistenceException e){
            System.err.println("数据库查询操作异常,find_id()报错"+e);
            dtoRes.setCode(0);
            dtoRes.setError_msg("当前业务繁忙，请稍后再试");
            return dtoRes;
        }
        //保存图片
        try{
            byte[] fileBytes = file.getBytes();
            //更改后的图片也发回前端并显示
            dtoRes.setCode(1);
            dtoRes.setFile_res(
                 fileIO.save_img(String.valueOf(un_id),fileBytes,type)
            );
            //如果文件操作失败则返回错误
            if(dtoRes.getFile_res().getCode()==0){
                dtoRes.setCode(0);
                dtoRes.setError_msg(dtoRes.getFile_res().getError_msg());
            }
            return dtoRes;
        }catch(IOException e){
            System.err.println("保存图片异常: " + e.getMessage());
            dtoRes.setCode(0);
            dtoRes.setError_msg("业务繁忙,请稍后再试");
            return dtoRes;
        }

    }

    //添加卡片
    DtoRes add_card(String text,String title,String user,org.springframework.web.multipart.MultipartFile file){
        DtoRes dtoRes = new DtoRes();
        //先检查文件是不是图片
        Tika tika = new Tika();
        try {
            String detectedType = tika.detect(file.getInputStream());
            if (!detectedType.startsWith("image/")) {
                dtoRes.setCode(0);
                dtoRes.setError_msg("上传的文件不是图片类型");
                return dtoRes;
            }
        } catch (IOException e) {
            System.err.println("文件检测异常: " + e.getMessage());
            dtoRes.setCode(0);
            dtoRes.setError_msg("业务繁忙,请稍后再试");
            return dtoRes;
        }
        //先拿这个用户的id,因为文件操作是根据id来找目录的
        int un_id=-1;
        try{
            un_id=repo.find_id(user);
        }catch(PersistenceException e){
            System.err.println("数据库查询操作异常,find_id()报错"+e);
            dtoRes.setCode(0);
            dtoRes.setError_msg("当前业务繁忙，请稍后再试");
            return dtoRes;
        }
        //保存图片和文本信息(一个文件存一个数据库存)
        try{
            byte[] fileBytes = file.getBytes();
            //先获取到图片对应的id

            //用用户名查表看卡片最新的id,图片名就是id+1.png
            List<DtoEach_card> lst_cards = repo.findByUsername(user);
            String new_id = String.valueOf(Integer.parseInt(lst_cards.get(lst_cards.size()-1).getId()) + 1);
            //添加卡片要先保存图片，避免数据库有内容但没图片

            //保存图片
            try{
            dtoRes.setFile_res(
                fileIO.save_cards( user,String.valueOf(un_id),new_id, fileBytes)
            );
            }catch(PersistenceException e){
                System.err.println("添加卡片时保存图片报错"+e);
                dtoRes.setCode(0);
                dtoRes.setError_msg("当前业务繁忙，请稍后再试");
                return dtoRes;
            }
            //保存文本信息
            try{
                repo.insert_each_card(user, Integer.parseInt(new_id), title, text);
            }catch(PersistenceException e){
                System.err.println("数据库插入操作异常,insert_each_card()报错"+e);
                dtoRes.setCode(0);
                dtoRes.setError_msg("当前业务繁忙，请稍后再试");
                return dtoRes; 
            }

                    
            //此时图片文本操作都成功就返回新卡片

            dtoRes.setCode(1);
            return dtoRes;
        }catch(IOException e){
            System.err.println("保存图片异常: " + e);
            dtoRes.setCode(0);
            dtoRes.setError_msg("业务繁忙,请稍后再试");
            return dtoRes;

        }
    
    }

    //删除卡片
    DtoRes del_card(String id,String user){
        DtoRes dtoRes = new DtoRes();
        //先删除数据库里的文本信息,如果成功了再删除图片,如果文本都删不了就没必要删图片了
        try{
            repo.del_each_card(user, Integer.parseInt(id));
        }catch(PersistenceException e){
            System.err.println("数据库删除操作异常,del_each_card()报错"+e);
            dtoRes.setCode(0);
            dtoRes.setError_msg("当前业务繁忙，请稍后再试");
            return dtoRes; 
        }
        //删除图片
        try{
            //先拿这个用户的id,因为文件操作是根据id来找目录的
            int un_id=repo.find_id(user);
            dtoRes.setFile_res(
                fileIO.del_cards(String.valueOf(un_id), id)
            );
           dtoRes.setCode(1);
           return dtoRes;
        }catch(PersistenceException e){
            System.err.println("数据库查询操作异常,find_id()报错"+e);
            dtoRes.setCode(0);
            dtoRes.setError_msg("当前业务繁忙，请稍后再试");
            return dtoRes;
        }

        
    }
    //修改全局信息
public DtoRes editText(String username,String telephone,String remarks,String address){
    DtoRes dtoRes = new DtoRes();
    // DtoGlobal_text dtoGlobal_text = new DtoGlobal_text();
    try {
        repo.update_global_text(username, telephone, remarks, address);
        dtoRes.setCode(1);
        dtoRes.setGlobal_text(repo.find_global_text(username));
    } catch (Exception e) {
        System.out.println("更新信息出现问题");
    }
return dtoRes;
}








}
