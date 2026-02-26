package com._project.springboot_backend.REPO;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;


import com._project.springboot_backend.DTO.DtoRes;
import com._project.springboot_backend.DTO.FileRes;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
@Repository
//这个类是用来处理文件输入输出的
public class FileIO {
    //注入配置文件里的图片存储目录的地址
    @Value("${file.picture}")
    private String file_address;


    //根据用户名所对应的id读取对应目录下的所有图片并返回二进制数据列表
    public  FileRes read_image(String un_id){
        //根据用户名拼接出用户目录
        String userDir = file_address + "/" + un_id+ "/" ;
        List<byte[]> imageDataList = new ArrayList<>();
        //如果此时对应目录为空则说明这是个新创建的用户，此时返回默认图片并创建对应目录
        if(!Files.exists(Paths.get(userDir))){
            //创建用户目录
            try {
                Files.createDirectories(Paths.get(userDir));
            } catch (IOException e) {
                System.err.println("创建目录错误: " + e.getMessage());
                FileRes fileRes = new FileRes();
                fileRes.setCode(0);
                fileRes.setError_msg("业务繁忙,请稍后再试");
                return fileRes; 
            }
            //返回默认图片的二进制数据列表
            try {
                byte[] defaultHead = Files.readAllBytes(Paths.get(file_address + "/0/head.png"));
                byte[] defaultBack = Files.readAllBytes(Paths.get(file_address + "/0/back.png"));
                imageDataList.add(defaultBack);
                imageDataList.add(defaultHead);
                FileRes fileRes = new FileRes();
                fileRes.setCode(1);
                fileRes.setLst_bytes(imageDataList);
                return fileRes;
            } catch (IOException e) {
                System.err.println("读取默认图片错误: " + e.getMessage());
                FileRes fileRes = new FileRes();
                fileRes.setCode(0);
                fileRes.setError_msg("业务繁忙,请稍后再试");
                return fileRes;
            }
        }


        // 获取目录下所有图片并返回二进制数据
            try {
            // 使用 Files.walk() 遍历图片目录，获取所有图片文件的二进制数据
            List<Path> imagePaths = Files.walk(Paths.get(userDir))
                    .filter(Files::isRegularFile)  // 只保留文件
                    .collect(Collectors.toList());

            //读取每个图片文件的字节数据
            for (Path path : imagePaths) {
                byte[] imageBytes = Files.readAllBytes(path);
                imageDataList.add(imageBytes);
            }
            // 返回所有图片的二进制数据列表
            FileRes fileRes = new FileRes();
            fileRes.setCode(1);
            fileRes.setLst_bytes(imageDataList);
            return fileRes;
        } catch (IOException e) {
            System.err.println("读取文件错误: " + e);
            FileRes fileRes = new FileRes();
            fileRes.setCode(0);
            fileRes.setError_msg("业务繁忙,请稍后再试");
            //System.out.println(userDir);
            //System.out.println("当前工作目录：" + System.getProperty("user.dir"));
            return fileRes; // 返回错误状态的FileRes对象
        }
    }

    //根据用户名创建目录并保存图片(一次一张)
    //注意用用户的id来作为用户图片的文件夹名
    public FileRes save_img(String un_id,byte[] img_data,String type){
        FileRes fileRes = new FileRes();
        //根据用户名拼接出用户目录
        String userDir = file_address + "/" + un_id+ "/" ;
        try {
            //创建用户目录（如果不存在）
            Path userPath = Paths.get(userDir);
            if (!Files.exists(userPath)) {
                Files.createDirectories(userPath);
            }
            //根据type确定图片名称
            String img_name = "";
            if(type.equals("head")){
                img_name = "head.png";
            }else if(type.equals("back")){
                img_name = "back.png";
            }
            Path imgPath = userPath.resolve(img_name);
            Files.write(imgPath, img_data);
            fileRes.setCode(1);
            //保存成功后把图片传回去重新渲染
            List<byte[]> lst_bytes = new ArrayList<>();
            lst_bytes.add(img_data);
            fileRes.setLst_bytes(lst_bytes);
            return fileRes;
        } catch (IOException e) {
            System.err.println("保存文件错误: " + e.getMessage());
            fileRes.setCode(0);
            fileRes.setError_msg("业务繁忙,请稍后再试");
            return fileRes; // 返回错误状态的FileRes对象
        }

    }




}
