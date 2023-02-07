package com.cos.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//사용자가 요청 -> 응답(HTML 파일)
//@Controller

//인터넷 브라우저 요청은 무조건 get요청 밖에 할 수 없다.
//다른 방식의 요청 또한 보기 위해서 postman을 사용하는 것
//사용자가 요청 -> 응답(Data를 응답)
@RestController
public class HttpControllerTest {

	@GetMapping("/http/lombok")
	public String lombokTest() {
		//Member m = new Member(1, "ssar", "1234", "ssar@nate.com");
		//Member 클래스에서 생성자 위에 @Builder 어노테이션을 붙이게 되면
		//Member 객체에 값을 집어넣을 때 오버로딩하지 않아도 
		//넣고 싶은 멤버 변수에만 값을 집어넣을 수 있게 해주는 어노테이션
		//생성자의 순서를 고려하지 않고 내가 원하는 순서대로 아래와 같이 작성하여 값을 집어 넣으면 됨
		//순서를 생각하지 않아됨
		//생성자를 이용하여 값을 집어넣으면 순서를 고려해야하는데
		//@Builder 어노테이션을 사용하게 되면 순서를 고려하지 않아도 됨
		Member m = Member.builder().username("ssar").password("1234").email("ssar@nate.com").build();
		System.out.println(TAG+"getter : "+m.getUsername());
		m.setUsername("cos");
		System.out.println(TAG+"setter : "+m.getUsername());
		return "lombok test 완료";
	}
	
	private static final String TAG ="HttpControllerTest :";
	//select
	@GetMapping("/http/get")
	public String getTest(Member m) {
		//?id=1&username=ssar&password=1234&email=ssar@nate.com
		//물음표 뒤에 있는 값을 스프링에서 알아서 해당 프로젝트에 있는 클래스의 멤버 변수들과 매핑을 해줌
		return "get 요청 : "+m.getId()+"username: "+m.getUsername()+"  "+m.getPassword()+"  "+m.getEmail();
	}
	//insert
	//http body데이터는 @RequestBody 어노테이션을 사용하여 데이터 값을 받을 수 있음
	//postman에서 raw데이터는 평문을 보냈다라는 의미(마인 타입이 text/plain)
	@PostMapping("/http/post")
	//MessageConverter(스프링부트)가 JSON형의 데이터 값 중 키 값이 같은 클래스 형으로 받으면 알아서 매핑을 해줌
	public String postTest(@RequestBody Member m) { 
		return "post 요청 : "+m.getId()+"username: "+m.getUsername()+"  "+m.getPassword()+"  "+m.getEmail();
	}
	//update
	@PutMapping("/http/put")
	public String putTest(@RequestBody Member m) {
		return "put 요청 : "+m.getId()+" "+m.getUsername()+"  "+m.getPassword()+"  "+m.getEmail();
	}
	//delete
	@DeleteMapping("/http/delete")
	public String deleteTest() {
		return "delete 요청  ";
	}
}
