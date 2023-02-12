package com.cos.blog.test;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.Board;
import com.cos.blog.model.Reply;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.ReplyRepository;

@RestController
public class ReplyControllerTest {
	
	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private ReplyRepository replyRepository;
	
	@GetMapping("/test/board/{id}")
	public Board getBoard(@PathVariable Long id) {
		//무한 반복 참조가 일어난다
		//왜냐하면 보드안에 replys가 있는데 이 replys에 들어가면 또 board에 대해 참조하고 있고 그래서
		//무한 참조가 발생하게 된다.
		return boardRepository.findById(id).get(); //jackson라이브러리 발동(오브젝트를 json으로 리턴) 그때 모델의 getter를 호출함
	}
	
	
	@GetMapping("/test/reply")
	public List<Reply> getReply() {
		return replyRepository.findAll();//jackson라이브러리 발동(오브젝트를 json으로 리턴) 그때 모델의 getter를 호출함
	}
}
