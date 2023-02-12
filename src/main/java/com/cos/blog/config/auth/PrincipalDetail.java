package com.cos.blog.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.blog.model.User;

import lombok.Data;

//스프링 시큐리티가 로그인 요청을 가로채서 로그인을 진행하고 완료가 되면 UserDetails 타입의 오브젝트를
//스프링 시큐리티의 고유한 세션 저장소에 저장을 함
//스프링 시큐리티의 고유한 세션 저장소에 PrincipalDetail가 저장이 되니 여기 클래스에 디비에 유저의 정보가 저장된 것들에 대한 정보도
//필요하므로 private User user;가 필요 한 것
@Data
public class PrincipalDetail implements UserDetails{
	private User user; // 콤포지션 객체를 품고 있는 것을 말함

	public PrincipalDetail(User user) {
		this.user = user; 
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUsername();
	}

	//계정이 만료되지 않았는지를 리턴한다.(true:만료 안 됨, false : 만료 됨)
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	//계정이 잠겨있는지 안 잠겨있는지에 대한 상태를 리턴한다(true: 계정이 안 잠겨져 있는 상태, false:계정이 잠겨져 있는 상태)
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}


	//비밀번호가 만료되지 않았는지 리턴한다.(true:만료 안 됨)
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	//계정이 활성호(사용가능)인지 리턴한다. (true:활성화)
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	//계정이 갖고 있는 권한 목록을 리턴한다. (권한이 여러 개 있을 수 있어서 루프를 돌려야 하는데 우리는 한개만 사용할 것이라 루프를 안 돌리고 add함)
	@Override //GrantedAuthority는 인터페이스라서 익명클래스로 구현하고 있음
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		Collection<GrantedAuthority> collectors = new ArrayList<>();
		//	Collection<GrantedAuthority> collectors = new ArrayList<>(); 제네릭 타입이 GrantedAuthority형이고
		//그러니 		collectors.add() -<메서드의 인자값 타입은 GrantedAuthority형이고 
		//GrantedAuthority형은 인터페이스인데 안에 구현 되어져야할 메서드는 한 가지이 뿐이니
		//람다식을 사용하여 return 값만 넣어주게 되면 GrantedAuthority인터페이스를 구현하게 되는 것이다.
		collectors.add(()->{
			return "ROLE_"+user.getRole();//ROLE_USER이런식으로 리턴이 되어야지 확인이 됨(롤을 식별할 수 있게 됨 ROLE_포함 중요!!)
		});
		return collectors;
	}
}

//계정을 권한을 리턴하는 메서드 부분에서 익명클래스로 인터페이스를 구현하는 코드임 
/*
 * collectors.add(new GrantedAuthority() {
 * 
 * //스프링 시큐리티가 롤을 받을 때 꼭 ROLE_를 포함시켜 넣어줘야함 규칙임
 * 
 * @Override public String getAuthority() { return
 * "ROLE_"+user.getRole();//ROLE_USER이런식으로 리턴이 되어야지 확인이 됨(롤을 식별할 수 있게 됨 ROLE_포함
 * 중요!!) } });
 */


