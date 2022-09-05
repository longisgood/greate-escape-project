<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/header.jsp" %>
<section class="container">
	<div class="row">
		<div class="col-md-2">
			<div class="card mb-3">
				<div class="card-header">
					마이 페이지
				</div>
				<div class="list-group list-group-flush">
					<a href="myPage" class="list-group-item">예약내역 확인</a>
					<a href="edit_check" class="list-group-item active">정보수정/탈퇴</a>
				</div>
			</div>
		</div>
		<div class="col-md-10 mb-5">
			<h2 class="mb-5">정보수정/탈퇴</h2>
			<div class="row">
				<div class="mt-5 col-8 offset-2">
					<c:choose>
						<c:when test="${!empty withdraw}">
							<form action="delete" method="post" class="mb-4 text-center">
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
								<p>탈퇴를 위해 비밀번호를 입력해 주세요.</p>
								<input class="form-control mb-3" type="password"
									name="member_pw" />
								<button class="btn btn-success mb-3">탈퇴하기</button>
							</form>
						</c:when>
						<c:otherwise>
							<form action="insertPass" method="post" class="mb-4 text-center">
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
								<p>개인정보 확인을 위해 비밀번호를 입력해 주세요.</p>
								<input class="form-control mb-3" type="password"
									name="member_pw" />
								<button class="btn btn-success mb-3">확인</button>
							</form>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>
	</div>
</section>
<script>
	var msg = '${message}';
	if (msg != '') {
		alert(msg);
	}
</script>
<%@ include file="../common/footer.jsp" %>