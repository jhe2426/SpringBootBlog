package com.cos.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.service.BoardService;

@Controller
public class BoardController {

	
	@Autowired
	private BoardService boardService;
	
	//로그인을 하면 오는 페이지를 처리하는 컨트롤러가 여기인데 그럼 컨트롤러에서 세션을 어떻게 찾는지?
	//@AuthenticationPrincipal PrincipalDetail principal를 매개변수로 받는다
	//그럼 저게 뭔데?
	//@AuthenticationPrincipal 어노테이션은 현재 서버 세션에 저장되어져 있는 값들을 가져와주는 어노테이션이고
	//PrincipalDetail principal는 서버 세션에 저장되어져 있는 것들 중에 시큐리티가 저장을 해준 유저의 정보를 가져오는 것이다.
	@GetMapping({"","/"})
	public String index(Model model,@PageableDefault(size=3,sort = "id", direction = Direction.DESC) Pageable pageable) { 
		model.addAttribute("boards",boardService.boardList(pageable));
		// /WEB-INF/vies/index.jsp
		return "index"; 
		//RestController이 아닌 Controller은 리턴을 할 때 viewResolver가 작동을 하는데 쟤가 작동을 하면 해당 페이지로 Model의 정보를 들고 이동함
	}
	
	
	//USER의 권한이 필요
	@GetMapping("/board/saveForm")
	public String saveForm() {
		return "board/saveForm";
	}
	
	
	
	
	
	
	
	
	
}
