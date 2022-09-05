<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/header.jsp"%>
<c:if test="${not empty cookie.id}">
	<c:set value="checked" var="checked" />
	<c:set value="${cookie.id.value}" var="id" />
</c:if>
<section class="container d-flex flex-column justify-content-center align-items-center h-100">
	<div class="row mb-3">
		<h3 class="mb-3">로그인</h3>
		<form action="login" method="post">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			<div class="row mb-3">
				<div class="col-md-9">
					<input type="text" class="form-control mb-3" name="member_id" placeholder="id" value="${id}" /> 
					<input type="password" class="form-control" name="member_pw" placeholder="password" />
				</div>
				<div class="col-md-3">
					<button id="loginBtn" class="btn btn btn-primary w-100 h-100">로그인</button>
				</div>
			</div>
			<div class="form-check">
				<input class="form-check-input" type="checkbox" name="remember-me" id="remember_id">
				<label class="form-check-label" for="remember_id">자동 로그인</label>
			</div>
		</form>
	</div>
	<div>
		<button id="joinBtn" type="button" class="btn btn-primary">회원가입</button>
		<button id="idFinder" type="button" class="btn btn-primary">아이디 찾기</button>
		<button id="pwFinder" type="button" class="btn btn-primary">비밀번호 찾기</button>
		<button id="rev_check" type="button" class="btn btn-primary">비회원 예약확인</button>
	</div>
</section>
<script>
	document.querySelector('#joinBtn').addEventListener('click', () => {
		location.href = 'join';
	});

	var msg = '${message}';
	if (msg != '') {
		alert(msg);
	}

	$("#idFinder").click(function () {
		location.href = 'id_find';
	});

	$("#pwFinder").click(function () {
		location.href = 'pw_find';
	});

	$("#rev_check").click(function () {
		location.href = 'rev_check';
	})
	;
</script>
<%@ include file="../common/footer.jsp"%>