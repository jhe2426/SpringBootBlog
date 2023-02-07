package com.cos.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TempControllerTest {

	
	@GetMapping("/temp/home")
	public String tempHome() {
		System.out.println("tempHome()");
		//파일 리턴 기본 경로 : src/main/resources/statichome.html
		//리턴명을 /home.html이 되어야지
		//풀 경로 : src/main/resources/static/home.html이렇게 됨
		return "/home.html";
	}
	
	@GetMapping("/temp/img")
	public String tempImg() {
		return "/photo1.jpg";
	}
	
	@GetMapping("/temp/jsp")
	public String tempJsp() {
		//prefix : /WEB-INF/views/
		//suffix : .jsp
		//return "/test.jsp"; 이렇게 설정을하면 풀네임이
		//풀네임 : /WEB-INF/views//test.jsp.jsp 이렇게 됨
		return "test";
	}
	
	
}



