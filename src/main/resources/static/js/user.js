let index = {
	//여기위치의 this 값과
	init: function() {
		//on메소드는 첫번째 파라미터에는 이벤트를 입력하과 두번째 파라미터에는 무엇을 할 것인지 작성하면 됨
		$("#btn-save").on("click", () => { // function(){}을 사용하지 않고, ()=>{}를 사용한 이유는 this를 바인딩하기 위해서 사용한 것
			this.save();
			//여기 위치의 this 값은 같은 값인데
			//()=>{}를 사용하지 않고 function(){}을 선언하여 안에다가 this를 사용하여 위의 this값과 달라지므로 ()=>{}를 사용한 것
			//function(){}을 선언하여 안에다가 this를 사용하여 위의 this값과 달라지는 이유는
			//자바스크립트의 부모는 window인데 함수를 정의라하고 this를 호출하게 되면 this에는 아무 값도 없으므로 찾다 찾다 부모를 찾아가게 되므로 
			//function(){}안의 this는 window 객체가 되는 것
		});

		$("#btn-update").on("click", () => {
			this.update();
		});	
		
		//	$("#btn-login").on("click", () => {
		//this.login();
		//	});
	},

	save: function() {
		//alert('user의 save함수 호출 됨');
		let data = {
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
			//여기서 data는 자바스크립트 오브젝이므로 자바로 던지면 해석을 할 수 없으므로 ajax통식을  할 때 json 문자열로 변환을 하고 자바로 던짐
		};

		//console.log(data);

		//ajax호출 시 default가 비동기 호출
		//ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청 할 것임
		$.ajax({
			//회원가입 수행 요청
			type: "POST",
			//url을 /api/user/join으로 작성하지 않는 이유는 전송 방식이 POST이므로 user을 insert를 하는 것인지 알기 때문에 굳이 적지 않는 것
			url: "/auth/joinProc",
			//http body 데이터 http body 데이터를 날리려면 마인 타입을 꼭 알려줘야함
			data: JSON.stringify(data),
			//body데이터가 어떤 타입(MIME)인지 알려주는 코드
			//마인 타입(MIME) 설정하는 부분 json 문자열과 마인 타입 설정 코드는 세트임(항상 설정해줘야하는 거)
			contentType: "application/json; charset=utf-8",
			//처음에 회원가입이 안 됐음 이유가
			//크롬에서 바디의 타입이 json이 아니였음 왜? contenType이라고 오타를 냈으므로 해당 바디 타입을 설정하지 못해서 일어난 해프닝
			// 요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열인데(생긴게 json이라면)
			//dataType에 json이라고 알려 주면 응답의 결과를 javascript오브젝트로 변경해주는 설정 코드임 	
			//근데 버전이 올라가면 서 ajax가 통신을 성공하고 나서 서버가 json을 리턴해주면 	자동으로 자바 오브젝트로 변환을 해줌 		
			//그래서 dataType:"json" 서버에서 리턴 값이 json이라고 안 알려줘도 자동으로 javcscript오브젝트로 변환이 되지만 그래도 그냥 작성하는 것
			dataType: "json"
		}).done(function(resp) { //회원가입 수행이 정상적으로 실행이 되면 done이 실행이 됨
			alert("회원가입이 완료되었습니다.");
			console.log(resp);
			//회원가입이 완료되면 "/"로 가야하는데 시큐리티에 허용을 설정해주지 않으면 회원가입이 완료 후 메인 페이지로 갈 수가 없게 됨
			location.href = "/";
		}).fail(function(error) {//회원가입 수행이 정상적으로 실행이 되지 않으면 fail이 실행이 됨
			alert(JSON.stringify(error));
		});
	},
	
	update: function() {
		let data = {
			id: $("#id").val(),
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		};

		$.ajax({
			type: "PUT",
			url: "/user",
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			dataType: "json"
		}).done(function(resp) { 
			alert("회원수정이 완료되었습니다.");
			console.log(resp);
			location.href = "/";
		}).fail(function(error) {
			alert(JSON.stringify(error));
		});
		
	}


}

index.init();
	//	login: function() {
	//alert('user의 save함수 호출 됨');
	//		let data = {
	//			username: $("#username").val(),
	//			password: $("#password").val()
	//			//여기서 data는 자바스크립트 오브젝이므로 자바로 던지면 해석을 할 수 없으므로 ajax통식을  할 때 json 문자열로 변환을 하고 자바로 던짐
	//		};

	//console.log(data);

	//ajax호출 시 default가 비동기 호출
	//ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청 할 것임
	//		$.ajax({
	//			//GET방식으로 로그인 요청을 하면 주소에 아이디와 패스워드 정보가 남기 때문에 무조건 중요한 정보를 전달할 때는 POST방식 사용할 것
	//			type: "POST",
	//			//url을 /api/user/join으로 작성하지 않는 이유는 전송 방식이 POST이므로 user을 insert를 하는 것인지 알기 때문에 굳이 적지 않는 것
	//			url: "/api/user/login",
	//			//http body 데이터 http body 데이터를 날리려면 마인 타입을 꼭 알려줘야함
	//			data: JSON.stringify(data),
	//			//body데이터가 어떤 타입(MIME)인지 알려주는 코드
	//			//마인 타입(MIME) 설정하는 부분 json 문자열과 마인 타입 설정 코드는 세트임(항상 설정해줘야하는 거)
	//			contentType: "application/json; charset=utf-8",
	//			dataType: "json"
	//		}).done(function(resp) { //로그인 수행이 정상적으로 실행이 되면 done이 실행이 됨
	//			alert("로그인이 완료되었습니다.");
	//			location.href = "/";
	//		}).fail(function(error) {//로그인 수행이 정상적으로 실행이 되지 않으면 fail이 실행이 됨
	//			alert(JSON.stringify(error));
	//		});
	//	}