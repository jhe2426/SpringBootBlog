package com.cos.blog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.dto.ReplySaveRequestDto;
import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.Board;
import com.cos.blog.model.Reply;
import com.cos.blog.service.BoardService;

@RestController
public class BoardApiController {

	@Autowired
	private BoardService boardService;

	// 게시판 글쓰기 처리
	@PostMapping("/api/board")
	public ResponseDto<Integer> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principal) {
		boardService.write(board, principal.getUser());
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}

	// 게시글 삭제
	@DeleteMapping("/api/board/{id}")
	public ResponseDto<Integer> deleteById(@PathVariable Long id) {
		boardService.boardDelete(id);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}

	// 게실글 수정
	@PutMapping("/api/board/{id}")
	public ResponseDto<Integer> update(@PathVariable Long id, @RequestBody Board board) {
		boardService.boardUpdate(id, board);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}

	// 댓글 작성
	// 데이터를 받을 때 컨트롤러에서 dto를 만들어서 받는게 좋다
	@PostMapping("/api/board/{boardId}/reply")
	public ResponseDto<Integer> replySave(@RequestBody ReplySaveRequestDto replySaveRequestDto) {
		boardService.writeReply(replySaveRequestDto);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}

	
	//댓글 삭제
	//{boardId}는 주소를 만들기 위해 넣은 것이지 다른 의미는 없음
	@DeleteMapping("api/board/{boardId}/reply/{replyId}")
	 public ResponseDto<Integer> replyDelete(@PathVariable Long replyId){
		boardService.replyDelete(replyId);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	
	// 댓글 작성
	// 데이터를 받을 때 컨트롤러에서 dto를 만들어서 받는게 좋다
//	@PostMapping("/api/board/{boardId}/reply")
//	public ResponseDto<Integer> replySave(@PathVariable Long boardId,@RequestBody Reply reply, @AuthenticationPrincipal PrincipalDetail principal) {
//		boardService.writeReply(principal.getUser(),id, reply);
//		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
//	}

}
