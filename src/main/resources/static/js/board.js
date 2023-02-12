let index = {
	init: function() {
		$("#btn-board-save").on("click", () => {
			this.save();
		});

		$("#btn-delete").on("click", () => {
			this.deleteById();
		});

		$("#btn-update").on("click", () => {
			this.update();
		});
		
		$("#btn-reply-save").on("click", () => {
			this.replySave();
		});
	},

	save: function() {

		let data = {
			title: $("#title").val(),
			content: $("#content").val(),

		};


		$.ajax({
			type: "POST",
			url: "/api/board",
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			dataType: "json"
		}).done(function(resp) {
			alert("글쓰기가 완료되었습니다.");
			location.href = "/";
		}).fail(function(error) {
			alert(JSON.stringify(error));
		});
	},

	deleteById: function() {
		//텍스트 값을 가져와야지 value값을 가져오려고 하니깐 보드 아이디 값이 존재하지 않아 그래서 삭제가 안 됐음
		let id = $("#id").text();
		$.ajax({
			type: "DELETE",
			url: "/api/board/" + id,
			dataType: "json"
		}).done(function(resp) {
			alert("삭제가 완료되었습니다.");
			location.href = "/";
		}).fail(function(error) {
			alert(JSON.stringify(error));
		});

	},

	update: function() {
		let id = $("#id").val();

		let data = {
			title: $("#title").val(),
			content: $("#content").val()
		};


		$.ajax({
			type: "PUT",
			url: "/api/board/"   + id,
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			dataType: "json"
		}).done(function(resp) {
			alert("글수정이 완료되었습니다.");
			location.href = "/";
		}).fail(function(error) {
			alert(JSON.stringify(error));
		});
	},
	
	replySave:function(){
		//alert('user의 save함수 호출됨');
		let data = {
			userId: $("#userId").val(),
			boardId: $("#boardId").val(),
			content: $("#reply-content").val()		
		};
		
		$.ajax({
			type: "POST",
			data:JSON.stringify(data),  //http body 데이터
			url: `/api/board/${data.boardId}/reply`,	
			contentType:"application/json; charset=utf-8", 
			dataType: "json" 
		}).done(function(resp){
			alert("댓글작성이 완료되었습니다.");
			location.href = `/board/${data.boardId}`;
		}).fail(function(error){
			alert(JSON.stringify(error));
		}); 
	},
	
	replyDelete:function(boardId, replyId){
			
		$.ajax({
			type: "DELETE",
			url: `/api/board/${boardId}/reply/${replyId}`,	
			dataType: "json" 
		}).done(function(resp){
			alert("댓글삭세 성공");
			location.href = `/board/${boardId}`;
		}).fail(function(error){
			alert(JSON.stringify(error));
		}); 
		
	}

}

index.init();