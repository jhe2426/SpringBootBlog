package com.cos.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//인증이 안 된 사용자들이 출입할 수 있는 경로를 
// /auth/** 경로 허용  /auth경로가 붙어 있는 것들은 전부 인증을 할 필요가 없는 것들임(그렇게 설정을 할 예정)ㄴ
// 또 그냥 주소가 /이면 index.jsp만 허용을 하고
// static 이하에 있는 /js/**, /css/**, /image/** 허용할 거임


@Controller
public class UserController {
	
	@GetMapping("/auth/joinForm")
	public String joinForm() {
		return "user/joinForm";
	}
	
	@GetMapping("/auth/loginForm")
	public String loginForm() {
		return "user/loginForm";
	}
}
