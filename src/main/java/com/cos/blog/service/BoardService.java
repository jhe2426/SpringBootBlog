package com.cos.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.domain.Board;
import com.cos.blog.domain.User;
import com.cos.blog.repository.BoardRepository;


@Service
public class BoardService {
	
	@Autowired
	private BoardRepository boardRepository;

	//글쓰기
	@Transactional
	public void write(Board board, User user) { //title, content
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
	}
	
	
	//모든 게시판 목록 조회하기
	//리턴값을 Page형으로 받아야지 해당 페이지가 몇 페이지인지를 알 수 있는 정보를 가지고 있기 때문이다.
	public Page<Board> boardList(Pageable pageable){
	return boardRepository.findAll(pageable);
	}
 
}
