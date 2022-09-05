<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/header.jsp" %>
<section class="container d-flex flex-column justify-content-center align-items-center h-100">
	<form id="noForm" action="${contextPath}/member/norev" method="post" class="mb-3 row w-100">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		<div class="col-md-8 offset-md-2 col-xl-6 offset-xl-3">
			<h2 class="mb-4">비회원 예약확인</h2>
			<input value="${rev_name}" type="text" name="rev_name" id="" class="mb-3 form-control" placeholder="이름">
			<input value="${rev_email}" type="text" name="rev_email" id="" class="mb-3 form-control" placeholder="이메일">
			<input value="${rev_phone}" type="text" name="rev_phone" id="" class="mb-3 form-control" placeholder="핸드폰">
			<div class="text-center">
				<button id="detailBtn" class="btn btn-primary" type="button">확인</button>
			</div>
		</div>
	</form>
</section>
<script>
	
	$("#detailBtn").click(function() {
		$("#noForm").submit();
	});

	var msg = '${message}';
	if (msg != '') {
		alert(msg);
	}
</script>
<%@ include file="../common/footer.jsp" %>