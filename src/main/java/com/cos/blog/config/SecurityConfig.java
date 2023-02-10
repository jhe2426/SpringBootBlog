package com.cos.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.cos.blog.config.auth.PrincipalDetailService;

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

	@Autowired
	private PrincipalDetailService principalDetailService;
	
	
	//BCryptPasswordEncoder이 객체를 사용하여 어떤 문자를 암호화 시켜주는 오브젝트이다.
	@Bean//IoC가 됨(스프링이 관리함 리턴되는 new BCryptPasswordEncoder()가 스프링이 관리함)
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
	}
	
	
	//시큐리티가 대신 로그인을 해주는데 그때 password를 가로채를 하는데
	//해당 password가 뭘로 해쉬가 돼서 회원가입이 되었는지 알아야
	//같은 해쉬로 암호화해서 DB에 있는 해쉬랑 비교를 할 수 있게 됨
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
	}
	

	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		
		http
			.csrf().disable() //csrf토큰 비활성화 (테스트 시 걸어두는 게 좋음)
			//그래서 회원가입을 했을 시 form으로 회원가입 처리를 한 것이 아니라
			//자바스크립트 ajax로 회원가입을 처리를 했는데 csrf토큰이 존재하지 않았기 때문에 
			//즉, 사용자가 요청했을 시 csrf토큰을 가지고 있지 않았기 때문에
			//정상적으로 회원가입 처리가 되지 않았던 것
			//시큐리티가 기본적으로 csrf토큰이 없으면 막아줌
			.authorizeHttpRequests()//Request가 들어 오면
				//user.js를 호출을 못하면 회원가입 버튼을 눌렀을 시 user.js파일이 호출 되어야 하므로 허용을 해줘야 회원가입이 가능함
				.antMatchers("/","/auth/**","/js/**","/css/**","/image/**","/dummy/**")// /auth/**, / 여기 경로는 
				.permitAll() // 접근 허용이다
				.anyRequest()//antMatchers("/auth/**")이게 아닌 모든 요청은
				.authenticated()//인증이 되어야한다라는 메소드
			.and()
				//인증이 필요한 곳으로 요청이 오면 아래의 페이지로 이동이 된다.
				.formLogin()
				.loginPage("/auth/loginForm")
				.loginProcessingUrl("/auth/loginProc")//스프링 시큐리티가 해당 주소로 요청오는 로그인을 가로채서 대신 로그인 해준다.
				.defaultSuccessUrl("/");//정상적으로 로그인 요청이 완료가 되면 해당 페이지로 이동이 됨
				//시큐리티가 유저의 아이디와 비밀번호를 가로칠 수 있도록 하려면 UserDetails를 구현한 사용자 오브젝트가 무조건 필요함
				//로그인을 했을 시 시큐리티 세션에 로그인한 정보를 등록을 하게 되는데 개발자의 오브젝트는 해당 세션에 등록이  될 수가 없음
				//왜? -> 타입이 안 맞기 때문에 그래서 UserDetails를 구현한 오브젝트를 생성해야함
				//.failureForwardUrl("/auth/loginForm")//정상적으로 로그인 요청이 완료가 안 되면 해당 페이지로 이동이 되는 것
	
		
		return http.build();
	}
	
}
