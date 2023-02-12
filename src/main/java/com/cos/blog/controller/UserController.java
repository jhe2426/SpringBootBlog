package com.cos.blog.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.cos.blog.model.KakaoProfile;
import com.cos.blog.model.OAuthToken;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//인증이 안 된 사용자들이 출입할 수 있는 경로를 
// /auth/** 경로 허용  /auth경로가 붙어 있는 것들은 전부 인증을 할 필요가 없는 것들임(그렇게 설정을 할 예정)ㄴ
// 또 그냥 주소가 /이면 index.jsp만 허용을 하고
// static 이하에 있는 /js/**, /css/**, /image/** 허용할 거임


@Controller
public class UserController {
	
	@Value("${cos.key}")
	private String cosKey;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	//회원가입 페이지
	@GetMapping("/auth/joinForm")
	public String joinForm() {
		return "user/joinForm";
	}
	
	//로그인 페이지
	@GetMapping("/auth/loginForm")
	public String loginForm() {
		return "user/loginForm";
	}
	
	//회원수정 페이지
	@GetMapping("/user/updateForm")
	public String updateForm() {
		return "user/updateForm";
	}
	
	
	//카카오 API 로그인
	@GetMapping("/auth/kakao/callback")
	//@ResponseBody어노테이션을 사용하면 Data를 뷰에게 리턴 해주는 컨트롤러 함수이다.
	public String kakaoCallback(String code) {
		
		//POST방식으로 key=value 데이터를 요청해야함(카카오쪽으로)
		//Retrofit2
		//OkHttp
		//RestTemplate
		
		
		RestTemplate rt = new RestTemplate();
		
		//HttpHeader 오브젝트 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		//HttpBody 오브젝트 생성
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", "2b5767577d10f5d3868fc5fcfd25a414");
		params.add("redirect_uri", "http://localhost:8000/auth/kakao/callback");
		params.add("code", code);
		
		//HttpHeader와 HttpBody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
				new HttpEntity<>(params,headers);
	
		//Http 요청하기 - Post방식으로 - 그리고 response 변수의 응답을 받음
		ResponseEntity<String> response = rt.exchange(
					"https://kauth.kakao.com/oauth/token",
					HttpMethod.POST,
					kakaoTokenRequest,
					String.class	
				);
		
		//Json 타입을 자바 오브젝트로 담게 해주는 라이브러리
		//Gson, Json, Simple, ObjectMapper
		ObjectMapper objectMapper = new ObjectMapper();
		OAuthToken oauthToken = null;
		try {
			oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println("카카오 엑세스 토큰: "+ oauthToken.getAccess_token());
		
		RestTemplate rt2 = new RestTemplate();
		
		//HttpHeader 오브젝트 생성
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization", "Bearer "+oauthToken.getAccess_token());
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		//HttpHeader와 HttpBody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest =
				new HttpEntity<>(headers2);
	
		//Http 요청하기 - Post방식으로 - 그리고 response 변수의 응답을 받음
		ResponseEntity<String> response2 = rt2.exchange(
					"https://kapi.kakao.com/v2/user/me",
					HttpMethod.POST,
					kakaoProfileRequest,
					String.class	
				);
		
		
		ObjectMapper objectMapper2 = new ObjectMapper();
		KakaoProfile kakaoProfile = null;
		try {
			kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		//User 오브젝트 : username, password,email
		System.out.println("카카오 아이디(번호) : "+ kakaoProfile.getId());
		System.out.println("카카오 이메일 : "+ kakaoProfile.getKakao_account().getEmail());
		
		System.out.println("블로그 서버  유저 네임 ; "+kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId());
		System.out.println("블로그 서버  이메일 ; "+kakaoProfile.getKakao_account().getEmail());
		//비밀번호를 안 넣을 시 로그인할 때 유저 네임만 입력해도 로그인이 되는 대참사가 발생하지 않도록 하기위해 아무 비밀번호를 넣는 것
		//UUID란 -> 중복되지 않는 어떤 특정 값을 만들어내는 알고리즘
		//로그인할 때마다 바뀌어 있는 패스워드를 확인을 못하기 때문에 로그인 진행을 할 수 없기때문에 사용을 하지 않는 이유임
		//기존 회원이 아니라면 UUID가 새로 만들어져도 되지만 기존 회원이라면 UUID가 새로 만들어 지면 자동 로그인 처리에서 비밀번호가 달라지므로
		//당연히 자동 로그인이 안 되므로 사용하지 않는 것
		//UUID garbagePassword = UUID.randomUUID(); 
		//System.out.println("블로그 서버 패스워드"+garbagePassword);
		System.out.println("블로그 서버 패스워드"+cosKey);
		User kakaoUser = User.builder()
				.username(kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId())
				.password(cosKey)
				.email(kakaoProfile.getKakao_account().getEmail())
				.oauth("kakao")
				.build();
		
		//가입자 혹은 비가입자 체크를 해서 회원가입 처리를 시켜야함
		User originUser = userService.findUser(kakaoUser.getUsername());
		
		//유저 네임을 통해 기존 회원이 없다면 회원가입 처리를 하는 분기문
		if(originUser.getUsername() == null) {
			System.out.println("기존 회원이 아닙니다._____________________________!!");
			userService.signUp(kakaoUser);
		}
		
		//가입자가 회원가입을  요청을 하면 로그인 처리 로직이 실행
		//자동 로그인 처리(세션 등록)
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(),cosKey));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return "redirect:/";
	}
	
}
