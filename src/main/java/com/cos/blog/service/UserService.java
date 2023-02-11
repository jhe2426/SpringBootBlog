package com.cos.blog.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.constant.RoleType;
import com.cos.blog.domain.User;
import com.cos.blog.repository.UserRepository;


//서비스가 필요한 이유 : 
//1. 트랜잭션 관리
//트랜잭션이란 : 일이 처리 되기 위한 가장 작은 단위 
//트랜잭션은 일반적으로 데이터의 변경이 있을 때만 사용함
//오라클 DB의 기본 READ commit전략은 commit된 것만 READ를 함 
//오라클의 READ commit 전략은 적합성이 깨짐
//그래서 해결하기 위해서는 repeatable read 전략을 사용하면 됨
//2. 서비스 의미 때문 : 서비스란 : 한 메서드에 1개이상의 데이터 베이스 로직들이 정의 되어져 있는 곳
//각 각의 데이터 베이스 로직은 하나의 트랜잭션인데 한 메서드에 존재하는 1개 이상의 데이터 베이스 로직
//즉, 각 각의 트랜잭션을 서비스에서는 하나의 트랜잭션으로 관리할 수 있다.
//스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해줌(=Ioc를 해준다, 메모리에 대신 띄워준다.)
@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder encoder;
	
	
	//회원가입
	@Transactional
	public void signUp(User user) {
		String rawPassword = user.getPassword(); //비밀번호 원문
		String encPassword = encoder.encode(rawPassword);//해쉬
		user.setPassword(encPassword);
		user.setRole(RoleType.USER);
		userRepository.save(user);
	}
	
	
	//회원정보 수정
	@Transactional
	public void userUpdate(User user) {
		//수정시에는 영속성 컨텍스트에 User 오브젝트를 영속화시키고, 영속화된 User 오브젝트를 수정하는것이 제일 좋음
		//select를 해서 User오브젝트를 DB로 부터 가져오는 이유는 영속화를 하기 위해서하는 것
		//영속화된 오브젝트를 변경하면 자동으로 더티체킹을 해서 DB에 update문을 날려주기 때문에
		//DB에 있는 User를 가져와 영속성 컨텍스트에 User정보를 담는 것
		User persistance = userRepository.findById(user.getId()).orElseThrow(()->{
			return new IllegalArgumentException("회원 찾기 실패");
		});
		
		String rawPassword = user.getPassword();//암호화 되기 전 상태
		String encPassword = encoder.encode(rawPassword);//암호화 된 상태
		persistance.setPassword(encPassword);//영속성 컨텍스트에 있는 user에 대한 정보를 바꾸는 것
		persistance.setEmail(user.getEmail());
		//회원수정 함수 종료시 = 서비스 종료 시 = 트랜잭션 종료 = DB commit이 자동으로 됨
		//commit이 자동으로 된다는 건 영속화된 persistance 객체의 변화가 감지 되면 더티체킹이 되어 update문을 db에 날려줌
		
	}
	


}

//로그인
//Select할 때 트랜잭션 시작 그리고 해당 서비스가 종료될 때 트랜잭션이 종료 되는데
//이때 readOnly=true를 설정하게 더티 체크는 신경쓰지 않고  정합성을 지킴
//더디 체크는 영속성에 들어가 있는 값이 변경이 되었는지를 계속 체크하고 있는 것
//select만 할 메소드이기 때문에 더디 체크를 하면 성능만 안좋아지므로 readOnly를 사용하는 것
//전통 로그인 방식임(요즘은 JWT토큰을 사용하거나 스프링 부트 시큐리티를 사용함)
//	@Transactional(readOnly = true) 
//	public User login(User user) {
//		return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
//	}
