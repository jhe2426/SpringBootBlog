package com.cos.blog.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import com.cos.blog.constant.RoleType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//ORM은 Java(모든 다른 언어들) Object을 테이블로 매핑해주는 기술
@Entity //User 클래스가 MySQL에 테이블이 생성이 된다.
//@DynamicInsert //insert값이 null인 필드는 제외하고 insert하는 어노테이션 role에 값을 입력하지 않으면 null값이 들어가 default값이 들어가지 않게 되므로
//위의 어노데이션을 사용하는 것
//@DynamicInsert //insert시에 null인 필드를 제외시켜 준다.
//너무 많은 어노테이션을 사용하게 되면 별로 좋지 않으므로 저 어노테이션을 사용하지않고 entity 클래스에 default어노테이션을 지우고
//컨트롤에서 직접 role의 값을 집어 넣어주는 방향을 가는 것이 조금 더 올바르다.
public class User {

	@Id//Primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY)//프로젝트에서 연결된 DB의 넘버링 전략을 따라간다
	//오라클을 사용하면 IDENTITY가 아닌 시퀀스를 사용해야하고 만약 무슨 DB를 연결할 지 모르면 AUTO라고 설정하면 알아서 해당 DB의 넘버링 전략을
	//따라가게 됨
	private Long id; //시퀀스, auto_increment
	
	@Column(nullable = false, length = 30, unique = true)
	private String username; // 아이디
	
	@Column(nullable = false, length = 100) // 비밀번호를 해쉬로 변경하여 비밀번호를 암호화하기 위해 길이의 제한을 넉넉하게 설정한 것
	private String password; // 비밀번호
	
	@Column(nullable = false, length = 50)
	private String email ; // 이메일
	
	//@ColumnDefault("USER")
	@Enumerated(EnumType.STRING)
	private RoleType role;
	
	@CreationTimestamp//시간이 자동으로 입력이 됨
	private Timestamp createDate; //회원가입 날짜


}
