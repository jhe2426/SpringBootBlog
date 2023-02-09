package com.cos.blog.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.constant.RoleType;
import com.cos.blog.domain.User;
import com.cos.blog.dto.ResponseDto;
import com.cos.blog.service.UserService;

@RestController
public class UserApiController {

	
	@Autowired
	private UserService userService;
	
	
	//회원가입 처리
	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody User user) {//username, paswword, email
		System.out.println("UserApiController : save 호출 됨");
		user.setRole(RoleType.USER);
		userService.signUp(user);
		//실제로 DB에 insert를 하고 아레에서 return이 되면 됨 
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);//자바오브젝트를 JSON으로 변환해서 리턴(Jackson)
	} 
	
	
	//전통적인 로그인 방식임
	//요즘은 JWT토큰을 이용하거나, 스프링 시큐리티를 이용해서 로그인을 함
	/*
	 * @PostMapping("/api/user/login") public ResponseDto<Integer>
	 * login(@RequestBody User user, HttpSession session) {
	 * System.out.println("UserApiController : login 호출 됨"); User principal =
	 * userService.login(user); //principal(접근 주체)
	 * 
	 * if(principal != null) { session.setAttribute("principal", principal); }
	 * return new ResponseDto<Integer>(HttpStatus.OK.value(),1); }
	 */
	
}
