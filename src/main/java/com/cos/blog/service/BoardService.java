package com.cos.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.dto.ReplySaveRequestDto;
import com.cos.blog.model.Board;
import com.cos.blog.model.Reply;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.ReplyRepository;
import com.cos.blog.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor //생성자를 만들 때 초기화가 꼭 되어야하는 얘들을 생성자파라미터에 넣어서 꼭 초기화해라라는 어노테이션
//final변수는 무조건 초기화(즉, 값이 할당이 되어야한다.)
public class BoardService {
	
	private final BoardRepository boardRepository;
	private final ReplyRepository replyRepository;
	
	// 글쓰기
	@Transactional
	public void write(Board board, User user) { // title, content
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
	}

	// 모든 게시판 목록 조회하기
	// 리턴값을 Page형으로 받아야지 해당 페이지가 몇 페이지인지를 알 수 있는 정보를 가지고 있기 때문이다.
	@Transactional(readOnly = true)
	public Page<Board> boardList(Pageable pageable) {
		return boardRepository.findAll(pageable);
	}

	
	//게시판 글 상세보기
	@Transactional(readOnly = true)
	public Board boardDetails(Long id) {
		return boardRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("글 상세보기 실패:아이디를 찾을 수 없습니다.");
		});
	}
	
	//게시글 삭제하기
	@Transactional
	public void boardDelete(Long id) {
			System.out.println("글 삭제하기 : "+id);
			boardRepository.deleteById(id);
	}
	
	
	//게시글 수정하기
	@Transactional
	public void boardUpdate(Long id, Board requestBoard) {
		Board board =	boardRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("글 찾기 실패 : 아이디를 찾을 수 없습니다.");
		});//영속화 완료
		
		board.setTitle(requestBoard.getTitle());
		board.setContent(requestBoard.getContent());
		//해당 함수로 종료 시(Service가 종료될 때) 트랜잭션이 종료됩니다. 이때 더티체킹이 일어나면서 자동 업데이트가 일어남 (db에 flush) 
	}
	
	//댓글 작성하기
	@Transactional
	public void writeReply(ReplySaveRequestDto replySaveRequestDto) {	
		int result = replyRepository.mSave(replySaveRequestDto.getUserId(),replySaveRequestDto.getBoardId(),replySaveRequestDto.getContent());
		System.out.println(result);
	}

	//댓글 삭제
	@Transactional
	public void replyDelete(Long replyId) {
		replyRepository.deleteById(replyId);
	}
	
	
}
/*	
//댓글 작성하기
@Transactional
public void writeReply(ReplySaveRequestDto replySaveRequestDto) {	
	
	User user = userRepository.findById(replySaveRequestDto.getUserId()).orElseThrow(()->{
		return new IllegalArgumentException("댓글 쓰기 실패 : 유저 id를 찾을 수 없습니다.");
	});//영속화 완료
	
	
	Board board = boardRepository.findById(replySaveRequestDto.getBoardId()).orElseThrow(()->{
		return new IllegalArgumentException("댓글 쓰기 실패 : 게시글 id를 찾을 수 없습니다.");
	});//영속화 완료
	
	Reply reply = new Reply();
	reply.update(user, board, replySaveRequestDto.getContent());
	replyRepository.save(reply);
	
}
*/