package com.cos.blog.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//github에 잘 저장이 되는지 테스트 중
@RestController
//스프링이 com.cos.blog 패키지 이하를 스캔해서 모든 파일을 메모리에 new하는 것이 아니라
//특정 어노테이션이 붙어 있는 클래스 파일들을 new해서(loC) 스프링 컨테이너에 관리해줌
public class BlogControllerTest {

	@GetMapping("/test/hello")
	public String hello() {
		return "<h1>hello spring boot</h1>";
	}
}
