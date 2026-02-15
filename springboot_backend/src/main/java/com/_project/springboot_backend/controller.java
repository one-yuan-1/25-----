package com._project.springboot_backend;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

import com._project.springboot_backend.Service_class;
import com._project.springboot_backend.DTO.DtoRes;


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


}
