package com.cos.blog.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

@Service // Bean 등록
public class PrincipalDetailService  implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	//스프링이 로그인 요청을 가로챌 때, username, password 변수 2개를 가로채는데
	//password 부분 처리는 알아서 함
	//username이 DB에 있는지 확인해주면 됨
	//username확인을 loadUserByUsername여기 함수에서 처리함
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User principal = userRepository.findByUsername(username)
				.orElseThrow(()->{
					return new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다 : "+username);
				});
		//로그인 요청에 대한 가로채기를 한 뒤 username확인을 한 뒤 리턴을 할 때 우리가 등록해둔 confiqure 메서드를 통해서
		//사용자가 적은 패스워드를 다시 암호화를 해서 데이터베이스랑 비교를 한 뒤 정상인 거 확인을 한 뒤 시큐리티 세션 영역에 유저의 정보가
		//저장이 됨
		return new PrincipalDetail(principal) ;//시큐리티의 세션에 유저 정보가 저장 됨
	}

	
	
}
