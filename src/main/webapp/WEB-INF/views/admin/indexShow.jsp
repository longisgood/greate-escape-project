<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/header.jsp" %>
<section class="container">
		<div class="col-md-2">
			<div class="card mb-3">
				<div class="card-header">
					관리자 페이지
				</div>
				<div class="list-group list-group-flush">
					<a href="${contextPath}/member/adminPage" class="list-group-item  active">회원/관리자 목록</a>
					<a href="${contextPath}/member/sales" class="list-group-item">매출 관리</a>
					<a href="${contextPath}/member/control" class="list-group-item">상품 관리</a>
				</div>
			</div>
		</div>
		<div class="col-md-10 row mb-5">
			<h2 class="mb-5">${clickMember.member_name}회원 정보</h2>
			<div class="table-responsive col-11">
				<table class="table">
					<tbody>
						<tr>
							<th class="table-info">아이디</th>
							<td>${clickMember.member_id}</td>
						</tr>
						<tr>
							<th class="table-info">이름</th>
							<td>${clickMember.member_name}</td>
						</tr>
						<tr>
							<th class="table-info">성별</th>
							<td>${clickMember.member_gender}</td>
						</tr>
						<tr>
							<th class="table-info">생년월일</th>
							<td>${clickMember.member_birth}</td>
						</tr>
						<tr>
							<th class="table-info">핸드폰</th>
							<td>${clickMember.member_phone}</td>
						</tr>
						<tr>
							<th class="table-info" rowspan="2">주소</th>
							<td>${clickMember.member_address}</td>
						</tr>
						<tr>
							<td>${clickMember.member_address_detail}</td>
						</tr>
						<tr>
							<th class="table-info">이메일</th>
							<td>${clickMember.member_email}</td>
						</tr>
					</tbody>
				</table>
			<div class="mb-4 text-end">
				<button id="back" class="btn btn-outline-secondary">뒤로가기</button>
			</div>
			</div>
			<div class="col-11">
		</div>
	</div>
</section>
<script>
	$("#back").click(function(){
		location.href = 'adminPage';
	});
</script>
<%@ include file="../common/footer.jsp" %>