package com.cos.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cos.blog.domain.User;

//DAO
// 자동으로 bean등록이 된다
//@Repository 생략 가능 자동으로 bean등록이 되므로
public interface UserRepository extends JpaRepository<User, Long> {

	
	
}




//JPA Naming 쿼리 전략
//아래와 같은 메서드를 생성해주기만 하면 JPA가 알아서 아래와 같은 쿼리문을 날려줌
//SELECT * FROM user WHERE username = ? AND password = ?;
//아이디와 패스워드를 비교하는 쿼리 메서드
//1.
//리턴 데이터 형을 왜 User로 받냐면 로그인 후 해당 유저의 정보를 가지고 있어야지 유저가 어떠한 행동을 했을 때 그 정보를 어떤 
//유저가 했는지에 대한 정보를 저장할 수 있으므로 로그인 후 유저의 전체 정보를 꼭 가지고 와야하는 것이다.
//User findByUsernameAndPassword(String username, String password); 
//위의 쿼리를 사용해서 로그인 처리하는 것은 전통적이 방법이라서 사용안 할 것임 우리는 시큐리티 라이브러리를 사용할 예정
//2.
//@Query(value="SELECT * FROM user WHERE username = ? AND password = ?", nativeQuery = true)
//User login(String username, String password);