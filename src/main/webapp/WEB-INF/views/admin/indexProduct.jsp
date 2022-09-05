<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/header.jsp" %>
<fmt:formatDate value="${product.product_departure}" var="departure" pattern="yyyy-MM-dd"/>
<fmt:formatDate value="${product.product_arrive}" var="arrive" pattern="yyyy-MM-dd"/>
<fmt:formatNumber value="${reservation.rev_adult*product.product_adult}" var="totalAdult" maxFractionDigits="3" />
<fmt:formatNumber value="${reservation.rev_minor*product.product_minor}" var="totalMinor" maxFractionDigits="3" />
<section class="container">
	<div class="row">
		<div class="col-md-2">
			<div class="card mb-3">
				<div class="card-header">
					관리자 페이지
				</div>
				<div class="list-group list-group-flush">
					<a href="${contextPath}/member/adminPage" class="list-group-item active">회원/관리자 목록</a>
					<a href="${contextPath}/member/sales" class="list-group-item">매출 관리</a>
					<a href="${contextPath}/member/control" class="list-group-item">상품 관리</a>
				</div>
			</div>
		</div>
		<div class="col-md-10 mb-5">
			<h2 class="mb-5">${member.member_name}회원의 상품 정보</h2>
			<div class="card mb-4">
				<div class="card-header">
					<h3 class="card-title">상품 이름 : ${product.product_name}</h3>
					<p class="text-end card-text">여행 기간 : ${departure} ~ ${arrive}</p>
				</div>
				<div class="card-body">
					<ul class="list-group">
						<li class="list-group-item"> 예약자 명 : ${member.member_name}</li>
						<li class="list-group-item"> 연락처 : ${member.member_phone}</li>
						<li class="list-group-item"> 이메일 : ${member.member_email}</li>
					</ul>
				</div>
				<div class="card-body">
					<p class="card-text">항공편 - ${product.product_airplane}</p>
					<p class="card-text">성인 : ${reservation.rev_adult}명 / ${totalAdult}원</p>
					<p class="card-text">소인 : ${reservation.rev_minor}명 / ${totalMinor}원</p>
					<p class="card-text">${product.product_plan - 1}박 ${product.product_plan}일</p>
					<a href="${embassy}" class="card-link" target="_blank">*${product.product_country} 대사관</a>
					<p class="card-text">${tripInfo}</p>
				</div>
			</div>
		</div>
	</div>
</section>
<%@ include file="../common/footer.jsp" %>