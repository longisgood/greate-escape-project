<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/header.jsp" %>
<fmt:formatNumber value="${asiaSales}" var="asiaMoney" maxFractionDigits="3" />
<fmt:formatNumber value="${americaSales}" var="americaMoney" maxFractionDigits="3" />
<fmt:formatNumber value="${oseaniaSales}" var="oseaniaMoney" maxFractionDigits="3" />
<fmt:formatNumber value="${europeSales}" var="europeMoney" maxFractionDigits="3" />
<fmt:formatNumber value="${totalSales}" var="totalMoney" maxFractionDigits="3" />
<section class="container">
	<div class="row">
		<div class="col-md-2">
			<div class="card mb-3">
				<div class="card-header">
					관리자 페이지
				</div>
				<div class="list-group list-group-flush">
					<a href="${contextPath}/member/adminPage" class="list-group-item">회원/관리자 목록</a>
					<a href="${contextPath}/member/sales" class="list-group-item active">매출 관리</a>
					<a href="${contextPath}/member/control" class="list-group-item">상품 관리</a>
				</div>
			</div>
		</div>
		<div class="col-md-10 mb-5">
			<h2 class="mb-5">매출 내역</h2>
			<div class="mb-4">
			</div>
			<div class="table-responsive">
				<table class="table table-bordered text-center">
					<thead>
						<tr class="table-info">
							<th>대륙명</th>
							<th>판매 상품수</th>
							<th>매출합계</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>아시아</td>
							<td>${countAsia} 개</td>
							<td>${asiaMoney} 원</td>
						</tr>
						<tr>
							<td>유럽</td>
							<td>${countEurope} 개</td>
							<td>${europeMoney} 원</td>
						</tr>
						<tr>
							<td>아메리카</td>
							<td>${countAmerica} 개</td>
							<td>${americaMoney} 원</td>
						</tr>
						<tr>
							<td>오세아니아</td>
							<td>${countOseania} 개</td>
							<td>${oseaniaMoney} 원</td>
						</tr>
						<tr>
							<td colspan="2">총 판매액</td>
							<td>${totalMoney} 원</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</section>
<%@ include file="../common/footer.jsp" %>