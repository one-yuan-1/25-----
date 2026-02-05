package com._project.springboot_backend;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;


import com._project.springboot_backend.DtoRes;
import com._project.springboot_backend.Service;
@RestController
public class Controller {
	Service service = new Service();
	//处理post登录请求
	@PostMapping("")
	public DtoRes getMethodName(@RequestParam String param) {
		return service.login();
	}
	//处理post注册请求
	@PostMapping("")
	public DtoRes register(
		@RequestParam String username, 
		@RequestParam String password
	) {
		return service.register(username,password);
	}


}
