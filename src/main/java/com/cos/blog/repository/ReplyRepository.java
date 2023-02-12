package com.cos.blog.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.cos.blog.dto.ReplySaveRequestDto;
import com.cos.blog.model.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

	@Modifying
	//인터페이스이면 public 생략이 가능함
	@Query(value = "INSERT INTO reply(userId, boardId, content, createDate) VALUES(?, ?, ?, now())", nativeQuery = true )
	//VALUES의 순서는 ReplySaveRequestDto의 멤버 변수 순서와 일치해야지 알아서 잘 매핑이 된다.!!
	int mSave(Long userId, Long boardId, String content); //업데이트된 행의 갯수를 리턴해줌 update,delete,insert는 int만 리턴해줌
}
