package com.cos.blog.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.constant.RoleType;
import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;

@RestController
public class UserApiController {

	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	
	//회원가입 처리
	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody User user) {//username, paswword, email
		System.out.println("UserApiController : save 호출 됨");
		userService.signUp(user);
		//실제로 DB에 insert를 하고 아래에서 return이 되면 됨 
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);//자바오브젝트를 JSON으로 변환해서 리턴(Jackson)
	} 
	
	//회원수정 처리
	@PutMapping("/user")
	//@RequestBody를 작성하지 않으면 JSON타입을 받는 것이 아니라 key=value형태의 데이터 값으로 받게 됨 x-www-form-urlencoded형태의 
	public ResponseDto<Integer> update(@RequestBody User user){
		userService.userUpdate(user);
		//여기서는 트랜잭션이 종료되기 때문에 DB에 값은 병경이 되었지만
		//세션 값은 변경되지 않은 상태이기 때문에 뷰페이지의 값들이 변경이 안 되고 있는 거(로그아웃하고 다시 로그인을 해야지만 값이 변경이 됨)
		//그래서 직접 세션 값을 변경해 줄 것임
		//강제로 세션 값 바꾸는 거
		//세션 등록
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
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
