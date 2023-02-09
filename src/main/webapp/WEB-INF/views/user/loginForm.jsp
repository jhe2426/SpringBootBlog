<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>

<div class="container">

	<form action="#" method="post">
		<div class="form-group">
			<label for="username">Username</label> 
			<input type="text" name="username" class="form-control" placeholder="Enter Username" id="username">
		</div>

		<div class="form-group">
			<label for="password">Password</label> 
			<input type="password" name="password"  class="form-control" placeholder="Enter password" id="password">
		</div>
		
		<div class="form-group form-check">
			<label class="form-check-label"> 
			<input name="remeber"  class="form-check-input"     type="checkbox"> Remember me
			</label>
		</div>
		<!-- 폼에서 처리 되록 할 것임(시큐리티 라이브러리를 사용하므로) -->
		<button id="btn-login" class="btn btn-primary">로그인</button>
	</form>
	

	<!-- 	<form action="/action_page.php" class="was-validated">
		<div class="form-group">
			<label for="uname">Username:</label> <input type="text" class="form-control" id="uname" placeholder="Enter username" name="uname" required>
			<div class="valid-feedback">Valid.</div>
			<div class="invalid-feedback">Please fill out this field.</div>
		</div>
		<div class="form-group">
			<label for="pwd">Password:</label> <input type="password" class="form-control" id="pwd" placeholder="Enter password" name="pswd" required>
			<div class="valid-feedback">Valid.</div>
			<div class="invalid-feedback">Please fill out this field.</div>
		</div>
		<div class="form-group form-check">
			<label class="form-check-label"> <input class="form-check-input" type="checkbox" name="remember" required> I agree on blabla.
				<div class="valid-feedback">Valid.</div>
				<div class="invalid-feedback">Check this checkbox to continue.</div>
			</label>
		</div>
		<button type="submit" class="btn btn-primary">Submit</button>
	</form> -->

</div>
<!-- 시큐리티 라이브러리를 사용하기위해 자바스크립트는 사용하지 않아도 됨 -->
<script src="/js/user.js"></script>

<%@ include file="../layout/footer.jsp"%>