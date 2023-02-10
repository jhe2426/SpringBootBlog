package com.cos.blog.domain;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.cos.blog.constant.RoleType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)//auto_increment
	private Long id;
	
	@Column(nullable = false, length = 100)
	private String title;
	
	@Lob//대용량 데이터
	private String content;//섬머노트 라이브러리를 사용할 예정(디자인이 <html>태그가 섞여서 디자인이 되어 글자의 용량이 굉장히 커지게 됨)

	//@ColumnDefault("0")//int는 디폴트 값 넣을 때 ' ' 넣어주지 않아도 됨
	private int count;//조회수
	
	// Many = Board, One = User 한명의 유저는 여러개의 게시물을 쓸 수 있다. //여러 개의 게시글은 한 명의 유저에 의해서 작성되어질 수 있다.
	@ManyToOne
	@JoinColumn(name="userId")
	private User user; //DB는 오브젝트를 저장할 수 없다 그래서 FK를 사용해서 연관관계를 설정하지만
									//자바는 오브젝트를 저장할 수 있음
	
	//mappedBy가 적혀있으면 연관관계의 주인이 아니다 (나는 FK가 아니다) 그니깐 DB에 컬럼을 만들지 마세요
	//즉, mappedBy가 적혀 있으면 데이터베이스에 저장되지 않음(데이터베이스에 들어가져 있지 않음)
	//나중에 select하기 위해서 필요한 코드임
	//Reply 오브젝트에 있는  board가 FK이다
	//나는 그냥 Board를 select할 때 join문을 통해서 값을 얻기위해서 필요한 것
	//연관관계의 주인이 아니면 fetch전략의 디폴트가 LAZY로 설정이 되어 있다.
	 @OneToMany(mappedBy="board", fetch = FetchType.EAGER)//하나의 게시글을 여러 개의 답변을 가질 수 있기 때문에 리스트형으로 받아야 한다.
	private  List<Reply> reply; //replyId의 컬럼(즉 외래키가 설정이 되면) 하나의 게시글에 여려 개의 replyId를 가지게 되는데 이것은 
	 												//데이터베이스의 제1정규화를 위반하는 것이므로 Board에서 Reply의 연관관계를 할 때에는 외래키가 필요가 없다.
	 
	@CreationTimestamp
	private Timestamp createDate;
}
