<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../common/header.jsp" %>
<link rel="stylesheet" href="${contextPath}/resources/css/detail.css">
<link rel="stylesheet" href="${contextPath}/resources/css/tiny.css">
<section class="container mb-3">
	<div class="row mb-3 pt-1">
		<div class="aWrap ps-3">
			<a href="${contextPath}">
				<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-house" viewBox="0 0 16 16">
					<path fill-rule="evenodd" d="M2 13.5V7h1v6.5a.5.5 0 0 0 .5.5h9a.5.5 0 0 0 .5-.5V7h1v6.5a1.5 1.5 0 0 1-1.5 1.5h-9A1.5 1.5 0 0 1 2 13.5zm11-11V6l-2-2V2.5a.5.5 0 0 1 .5-.5h1a.5.5 0 0 1 .5.5z" />
					<path fill-rule="evenodd" d="M7.293 1.5a1 1 0 0 1 1.414 0l6.647 6.646a.5.5 0 0 1-.708.708L8 2.207 1.354 8.854a.5.5 0 1 1-.708-.708L7.293 1.5z" />
				</svg>
			</a>
			<span>&nbsp;&gt;&nbsp;</span>
			<a href="${contextPath}/products/continent/${product.product_continent}">${product.product_continent}</a>
			<span>&nbsp;&gt;&nbsp;</span>
			<a href="${contextPath}/products/search?country=${product.product_country}">${product.product_country}</a>
			<span>&nbsp;&gt;&nbsp;</span>
			<a href="${contextPath}/products/search?city=${product.product_city}">${product.product_city}</a>
		</div>
	</div>
	<div class="row">
		<div class="col-lg-6">
			<div class="card mb-3">
				<img src="${contextPath}/attach${product.detail_title_image.replace('s_','')}" class="card-img-top" alt="...">
			</div>
		</div>
		<div class="col-lg-6">
			<div class="card mb-3">
				<div class="card-header">
					<h2 class="fs-5 card-title m-0 p-1">[${product.product_country}/${product.product_city}] ${product.product_name}</h2>
				</div>
				<div class="card-body">
					<div class="row mb-3">
						<div class="col">
							<span class="fs-6 badge text-bg-primary me-2 p-2">항공편</span>
							<span class="card-text d-inline-block">${product.product_airplane}</span>
						</div>
						<div class="col">
							<span class="fs-6 badge text-bg-primary me-2 p-2">잔여좌석</span>
							<span class="card-text d-inline-block">${product.product_seat}</span>
							<c:if test="${product.product_seat <= 5}">
								<span class="ms-2 badge text-bg-danger">마감 임박</span>
							</c:if>
						</div>
					</div>
					<div class="row mb-3">
						<div class="col">
							<span class="fs-6 badge text-bg-primary me-2 p-2">성인요금</span>
							<span class="card-text d-inline-block">
								<fmt:formatNumber type="number" maxFractionDigits="3" value="${product.product_adult}" />원
							</span>
						</div>
						<div class="col">
							<span class="fs-6 badge text-bg-primary me-2 p-2">소인요금</span>
							<span class="card-text d-inline-block">
								<fmt:formatNumber type="number" maxFractionDigits="3" value="${product.product_minor}" />원
							</span>
						</div>
					</div>
					<div class="row">
						<div class="col">
							<span class="fs-6 badge text-bg-primary me-2 p-2">출발일정</span>
							<span class="card-text d-inline-block">
								<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${product.product_departure}" />
							</span>
						</div>
						<div class="col">
							<span class="fs-6 badge text-bg-primary me-2 p-2">도착일정</span>
							<span class="card-text d-inline-block">
								<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${product.product_arrive}" />
							</span>
						</div>
					</div>
				</div>
			</div>
			<div class="row mb-3">
				<div class="col d-flex justify-content-around">
					<sec:authorize access="anonymous">
						<a href="${contextPath}/products/${product.product_num}/reservation" class="btn btn-primary" id="reserveBtn">예약하기</a>
						<a href="${contextPath}/board/faq" class="btn btn-primary" id="qnaBtn">문의하기</a>
					</sec:authorize>
					<sec:authorize access="hasRole('ROLE_MEMBER')">
						<a href="${contextPath}/products/${product.product_num}/reservation" class="btn btn-primary" id="reserveBtn">예약하기</a>
						<a href="${contextPath}/board/faq" class="btn btn-primary" id="qnaBtn">문의하기</a>
					</sec:authorize>
					<sec:authorize access="hasAnyRole('ROLE_MASTER','ROLE_ADMIN')">
						<a href="${contextPath}/products/${product.product_num}/update" class="btn btn-primary" id="reserveBtn">상품 수정</a>
						<a id="deleteBtn" href="${contextPath}/products/${product.product_num}/delete" class="btn btn-primary" id="reserveBtn">상품 삭제</a>
						<form id="deleteForm" class="d-none">
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
						</form>
					</sec:authorize>
				</div>
			</div>
		</div>
	</div>
	<div class="row px-3">
		<div class="card mb-3">
			<div class="card-body border-bottom tiny-p">
				<h3 class="card-title m-0 fs-5 mb-3">상품 설명</h3>
				${product.detail_info}
			</div>
		</div>
		<div class="card mb-3">
			<div class="card-body tiny-p">
				<h3 class="card-title m-0 fs-5 mb-3">상세 일정</h3>
				${product.detail_schedule}
			</div>
		</div>
	</div>
</section>
<sec:authorize access="hasAnyRole('ROLE_MASTER','ROLE_ADMIN')">
	<script>
		$("#deleteBtn").click(function (e) {
			e.preventDefault();
			if (window.confirm("정말로 삭제하시겠습니까?")) {
				const form = $("#deleteForm");
				form.attr("action", this.href);
				form.attr("method", "POST");
				form.submit();
			}
		});
	</script>
</sec:authorize>
<%@ include file="../common/footer.jsp" %>