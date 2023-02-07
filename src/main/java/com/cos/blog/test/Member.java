package com.cos.blog.test;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//final 변수를 가지고 생성자를 만들어주는 어노테이션
//final 변수를 가지지 않으면 그 변수는 생성자에 매겨변수로 들어가지 않음
//@RequiredArgsConstructor

//@Getter
//@Setter
@Data //@Getter,@Setter가 Data 어노테이션에 다 포함 되어 있음


@NoArgsConstructor //빈 생성자(디폴트 생성자(매개변수를 가지지 않는 생성자))

public class Member {
	//객체 지향 언어에서는 변수는 직접 접근하여 값을 변경하는 것이 아니라
	//public 메서드를 이용하여 해당 변수의 값이 변경되도록 설계를 하는 방법이
	//옳다 그래서 해당 변수들을 private로 선언하는 것
	//원래 이런 오브제들은 데이터베이스에 저장되어 있는 데이터들을 가져오기 때문에
	//이 데이터가 변경되지 않기 위해 final을 선언해주는 것
	//불변성을 유지하기 위해 데이터베이스에서 가져온 이 값들을 변경할 일이 없으므로
	//만약 변경할 일이 있다면 해당 변수에 final을 선언하면 안 됨
	private int id;
	private String username;
	private String password;
	private String email;
	
	@Builder
	public Member(int id, String username, String password, String email) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
	}

	
	
}
