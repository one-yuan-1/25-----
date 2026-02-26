package com._project.springboot_backend;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

import com._project.springboot_backend.Service_class;
import com._project.springboot_backend.DTO.DtoRes;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
public class Controller {

	//注入依赖
	private final Service_class service;
    public Controller(Service_class service){
		this.service=service;
    }

	//处理post登录请求
	@PostMapping("/api/login")
	public DtoRes getMethodName(
		@RequestParam String username, 
		@RequestParam String password
	) {
		return service.login(username,password);
	}
	//处理post注册请求
	@PostMapping("/api/register")
	public DtoRes register(
		@RequestParam String username, 
		@RequestParam String password
	) {
		return service.register(username,password);
	}
	//处理添加卡片请求

	//处理删除卡片请求

	//处理后端传来的图片,根据type的值来确定是头像还是背景图,根据user确定用户,file是图片内容
	@PostMapping("/api/upload")
	public DtoRes EditImage(
	@RequestParam("type") String type,
	@RequestParam("user") String user,
	@RequestParam("file") MultipartFile file
	) {
		return service.SaveImg(type,user,file);
	}
	

	//处理修改全局信息请求

}
