<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="layout/header.jsp"%>

<div class="container">
	<!-- 
		boards에는 페이지 정보와 보드 정보가 같이 들어가 있기 때문에 보드 정보만 가져오고 싶으면 .content를 입력해야지 해당 값을 가져올 수 있다
		안 그러면 오류가 발생된다.
 	-->
	<c:forEach var="board" items="${boards.content}">
		<div class="card m-2">
			<div class="card-body">
				<h4 class="card-title">${board.title}</h4>
				<a href="/board/${board.id}" class="btn btn-primary">상세보기</a>
			</div>
		</div>
	</c:forEach>

	<!-- class 이름이 justify-content-center은 부트스트랩에서 flex로 정렬할 떄 문법 임 -->
	<!-- justify-content-end <- 제일 끝으로 보내는 거 -->
	<!-- justify-content-start <- 시작으로 보내는 거(맨 앞으로 보내는 거)  -->
	<!-- page클래스의 number값이 현재 페이지를 의미함 -->
	<ul class="pagination justify-content-center">
	
		<c:choose>
			<c:when test="${boards.first}">
				<li class="page-item disabled"><a class="page-link" href="?page=${boards.number-1}">이전</a></li>
			</c:when>
			<c:otherwise>
				<li class="page-item"><a class="page-link" href="?page=${boards.number-1}">이전</a></li>
			</c:otherwise>
		</c:choose>
		
				<c:choose>
			<c:when test="${boards.last}">
				<li class="page-item disabled"><a class="page-link" href="?page=${boards.number+1}">다음</a></li>
			</c:when>
			<c:otherwise>
				<li class="page-item"><a class="page-link" href="?page=${boards.number+1}">다음</a></li>
			</c:otherwise>
		</c:choose>
		
	</ul>
</div>

<%@ include file="layout/footer.jsp"%>
