package com.cos.blog.test;



import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.constant.RoleType;
import com.cos.blog.domain.User;
import com.cos.blog.repository.UserRepository;






//자바 개념 정리
//인터페이스는 new를 해서 생성자 초기화를 할 수 없음
//그래서 인터페이스를 구현하는 클래스를 정의하거나
//인터페이스를 new하려면 익명클래스를 만들어 해당 인터페이스의 메서드를 오버라이딩해주면 new해서 사용이 가능함

//리턴이 html파일이 아니라 data를 리턴해주는 controller = RestController
@RestController
public class DummyControllerTest {

	@Autowired//의존성 주입(DI)
	private UserRepository userRepository;
	
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable Long id) {
		try {
			userRepository.deleteById(id);//아이디 값이 디비에 없는 값이 들어올 수 있으므로 예외처리가 꼭 필요함
		} catch (EmptyResultDataAccessException e) { 
			//예외의 부모인 Exception형으로 선언하면 모든 예외를 받을 수 있기 때문에 
			//하나 하나 무슨 예외가 발생하는지에 대한 예외형을 사용하지 않아도 된다.
			//정확하게 excption을 사용하면 해당 excption 때문이 아니라 다른 예외가 발생했다는 것을 알 수 있기 때문에 
			//정확하게 excption을 주면 이러한 장점이 있다.
			return "삭제에 실패하였습니다. 해당 id는 DB에 없습니다.";
		}
		
		return "삭제되었습니다. id: "+id;
	}		
	
																											
	
	//sava 함수는 id를 전달하지 않으면 insert를 해주고 
	//sava 함수는 id를 전달하면 해당 id에 대한 데이터가 있으면 update를 해주고
	//sava 함수는 id를 전달하면 해당 id에 대한 데이터가 없으면 insert를 해줌
	//email,password를 수정할 것이므로 매개변수로 받아야 함
	//@RequestBody User requestUser Json형으로 데이터 값을 받아 오는 방법 임
	//json 데이터를 요청 => 스프링이  Java Object(MessageConverter의 Jackson 라이브러리가 변환해서 받아 줌)
	@Transactional //함수 종료시에 자동 commit이 됨
	//commit이 될 때 영속화된 데이터의 값이 변경이 되면 이를 감지하고 데이터를 업데이트 하여 영속화 시키고 DB에 저장을 해줌
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable Long id, @RequestBody User requestUser) {
		System.out.println("id : " +id);
		System.out.println("password : " +requestUser.getPassword());
		System.out.println("email : " +requestUser.getEmail());
		
		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("수정에 실패하였습니다."); 
		});
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		//save()메서드를 이용해서 업데이트를 하려면
		//해당 PK값으로 해당 테이블 값들을(실제 데이터 값)  받아온 뒤 값을 변경하고 save()메서드에 값을 넣어야지 문제없이 업데이트가 가능하게 된다.
		//userRepository.save(user);
		//@Transation을 걸면 save 함수를 사용하지 않아도 sava가 됨 -> 더티 체킹이라고 함
		//더디 체킹(영속화된 데이터의 값이 변경이 되면 이를 감지하고 데이터를 업데이트 하여 영속화 시키고 DB에 저장을 해줌)
		return user;
	}
	
	
	//한페이지당 2건의 데이터를 리턴 받아 볼 예정(페이징)
	@GetMapping("/dummy/user")
	public List<User> pageList(@PageableDefault(size=2,sort = "id", direction = Direction.DESC) Pageable pageable){
		Page<User> PagingUser =  userRepository.findAll(pageable);
		//Page클래스가 isFirst()메서드 등이 정의되어 있어 분기문 처리할 때 유용함
		//그래서 Page형을 받아서 값만 리턴하고 싶을 때는 .getContent()메서드를 사용하여 데이터 값만 받아와서 리턴하면 됨
		
		List<User> users = PagingUser.getContent();//getContent() Page값 빼고 리스트 값만 받아올 수 있도록 하는 메서드
		return users;
	}
	
	//http;//localhost:8000/blog/dummy/user
	@GetMapping("/dummy/users")
	public List<User> list(){
		return userRepository.findAll();
	}
	
	
	//{id} 주수로 파라미터를 전달 받을 수 있음
	//http;//localhost:8000/blog/dummy/user/3
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable Long id) {
		//user/4을 찾으면 내가 데이터베이스에서 못 찾아오게 되면 user가 null이 될 것 아냐?
		//그럼 return될 때 null이 리턴 되므로 그럼 프로그램에 문제가 있지 않겠니
		//Optional로 너의 User객체를 감싸서 가져올테니 null인지 아닌지 판탄해서 return해
		//따라서 JpaRepository의 메서드 findBy컬럼명()은 리턴을 Optional형으로 하는 것임
		//findById(id).get()는  절때로 user에 null값이 들어갈 일이 없을 때 사용하는 것인데 get()은 절대로 사용할 일 없을 듯
		//항상 예외는 발생할 수 있는 아주 작은 가능성이 존재하므로
		
		//람다식
		/*
		 * User user = userRepository.findById(id).orElseThrow(()->{ return new
		 * IllegalArgumentException("해당 유저는 없습니다. id : " + id); });
		 */
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
			@Override
			public IllegalArgumentException get() {
				return new IllegalArgumentException("해당 유저는 없습니다.  ");
			}
		});
		//요청 : 웹브라우저
		//user 객체 = 자바 오브젝트
		//변환(웹브라우저가 이해할 수 있는 데이터) -> json
		//스프링 부트 = MessageConverter라는 애가 응답시에 자동으로 작동
		//만약에 자바 오브젝트를 리턴하게 되면 MessageConverter가 Jackson 라이브러리를 호출해서
		//user 오브젝트를 json으로 변환해서 브라우저에게 던져줌
		return user;//웹브라우저에게 user객체를 리턴을 해주는 것
								//웹브라우저는 user객체를 이해하지 못함
								//웹브라우저는 html.javascript등만 이해를 하지 java객체를 이해하지는 못함
								//웹브라우저는 자바 객체를 이해하지 못하므로 user 객체가 리턴이 될 때 변환을 해야함(웹브라우저가 이해할 수 있는 데이터) -> json
	}
	
	
	
	
	//@RequestParam("키이름") 생략 가능 저걸 사용하면 매개변수 이름을 다른 것으로 사용할 수 있는 장점이 있는 것이다.
	//http의 body에 username, password, email 데이터를 가지고 요청을 하게 되면
	//join메서드의 매개변수에 알아서 매핑이 됨
	@PostMapping("/dummy/join")
	public String join(String username, String password, String email) {//key=value 형태를 받아서 매핑이 되는 것(html input태그에 입려된 값들이 이런식으로 넘어오게 되어 매핑이 됨)
		System.out.println("username :"+username);
		System.out.println("password :"+password);
		System.out.println("email :"+email);
		return "회원가입이 완료되었습니다.";
	}
	
	@PostMapping("/dummy/join2")
	public String join2(User user) {//이 또한 스프링에서 오브젝트 멤버 변수 이름과 넘어오는 키의 이름이 일치하면 알아서 매핑을 해줌
		System.out.println("id :"+user.getId());
		System.out.println("username :"+user.getUsername());
		System.out.println("password :"+user.getPassword());
		System.out.println("email :"+user.getEmail());
		System.out.println("role :"+user.getRole());
		System.out.println("createDate :"+user.getCreateDate());
		
		user.setRole(RoleType.USER);
		userRepository.save(user);
		return "회원가입이 완료되었습니다.";
	}
}
