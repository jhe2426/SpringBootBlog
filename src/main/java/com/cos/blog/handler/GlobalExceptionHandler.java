package com.cos.blog.handler;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.dto.ResponseDto;

@ControllerAdvice //모든 exception이 발생하면 이 클래스로 들어올 수 있도록 해주는 어노테이션
//@ControllerAdvice는 모든 @Controller 즉, 전역에서 발생할 수 있는 예외를 잡아 처리해주는 annotation이다.
@RestController
public class GlobalExceptionHandler {

	/*
	 * @ExceptionHandler(value = IllegalArgumentException.class) public String
	 * handleArgumentException(IllegalArgumentException e) { return
	 * "<h1>"+e.getMessage()+"</h1>"; }
	 */
	
	@ExceptionHandler(value = Exception.class)
	public ResponseDto<String> handleEmptyException(Exception e) {
		return new ResponseDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(),e.getMessage());
	}
}
