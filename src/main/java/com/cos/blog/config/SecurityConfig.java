package com.cos.blog.config;

import javax.annotation.security.PermitAll;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

//빈 등록 : 스프링 컨테이너에서 객체를 관리할 수 있게 하는 것

//빈이 등록 됨
@Configuration
@EnableWebSecurity//시큐리티라는 필터 추가 = 스프링 시큐리티가 활성화가 되어 있는데 (시큐리티 라이브러리를 설치하면 알아서 활성화 됨)
																			//어떤 설정을 해당 파일에서 하겠다는 
																			//시큐리티 필터가 등록이 된다.
									//컨트롤러 보다 먼저 실행이 되어야지 어떤 경로에 대해서 허용을 할 것인지 아니지를 결정할 수 있게 되므로
									//꼭 필터를 추가해야하는 것
@EnableGlobalMethodSecurity(prePostEnabled = true)//특정 주소로 접근을 하면 권한 및 인증을 미리 체크하겠다는 어노테이션임(어떤 요청이 들어오면 요청이 수행하고 나서 권한 및 인증 시큐리티가 동작한는 것이 아니라)
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		
		http
			.authorizeHttpRequests()//Request가 들어 오면
				.antMatchers("/auth/**")// /auth/**, / 여기 경로는
				.permitAll() // 접근 허용이다
				.anyRequest()//antMatchers("/auth/**")이게 아닌 모든 요청은
				.authenticated()//인증이 되어야한다라는 메소드
				.and()
					//인증이 필요한 곳으로 요청이 오면 아래의 페이지로 이동이 된다.
					.formLogin()
					.loginPage("/auth/loginForm");
		
		
		
		return http.build();
	}
	
}
