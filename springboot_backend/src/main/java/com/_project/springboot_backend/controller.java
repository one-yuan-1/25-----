package com._project.springboot_backend;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;


import com._project.springboot_backend.DtoRes;
import com._project.springboot_backend.Service_class;
@RestController
public class Controller {
	Service_class service = new Service_class();
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
