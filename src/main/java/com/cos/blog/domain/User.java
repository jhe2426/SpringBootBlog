package com.cos.blog.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.cos.blog.constant.Role;


//ORM은 Java(모든 다른 언어들) Object을 테이블로 매핑해주는 기술
@Entity //User 클래스가 MySQL에 테이블이 생성이 된다.
public class User {

	@Id//Primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY)//프로젝트에서 연결된 DB의 넘버링 전략을 따라간다
	//오라클을 사용하면 IDENTITY가 아닌 시퀀스를 사용해야하고 만약 무슨 DB를 연결할 지 모르면 AUTO라고 설정하면 알아서 해당 DB의 넘버링 전략을
	//따라가게 됨
	private Long id; //시퀀스, auto_increment
	
	@Column(nullable = false, length = 30)
	private String username; // 아이디
	
	@Column(nullable = false, length = 100) // 비밀번호를 해쉬로 변경하여 비밀번호를 암호화하기 위해 길이의 제한을 넉넉하게 설정한 것
	private String password; // 비밀번호
	
	@Column(nullable = false, length = 50)
	private String email ; // 이메일
	
	@Enumerated(EnumType.STRING)
	@ColumnDefault("'USER'")
	private Role role;
	
	@CreationTimestamp//시간이 자동으로 입력이 됨
	private Timestamp createDate; //회원가입 날짜
}
