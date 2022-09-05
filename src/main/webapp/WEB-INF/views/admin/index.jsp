<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/header.jsp" %>
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
		<div class="col-md-10">
			<h2 class="mb-4">회원/관리자 목록</h2>
			<div class="mb-4">
				<div class="dropdown">
					<a class="btn btn-outline-secondary dropdown-toggle" href="#" role="button"
						data-bs-toggle="dropdown" aria-expanded="false">
						회원 유형
					</a>
					<ul class="dropdown-menu">
						<li><a class="dropdown-item" href="${contextPath}/member/adminPage">전체</a></li>
						<li><a class="dropdown-item" href="${contextPath}/member/adminPage?type=회원">회원</a></li>
						<li><a class="dropdown-item" href="${contextPath}/member/adminPage?type=관리자">관리자</a></li>
					</ul>
				</div>
			</div>
			<div class="table-responsive">
				<table class="table table-hover table-bordered text-center">
					<thead>

						<tr class="table-info">
							<th>아이디</th>
							<th>이름</th>
							<th>유형</th>
							<th>관리내역</th>

						</tr>
					</thead>
					<tbody>
						<c:forEach items="${list}" var="member">
							<tr>
								<td><a href="detailInfo?member_num=${member.member_num}">${member.member_id}</a></td>
								<td>${member.member_name}</td>
								<td><c:choose>
										<c:when test="${member.member_master eq 'Y'}">
										관리자
									</c:when>
										<c:otherwise>
										일반회원
									</c:otherwise>
									</c:choose></td>
										<td><c:forEach items="${rev}" var="R"><c:if
										test="${member.member_master eq 'N' and member.member_id == R.member_id}"><a
											href="${contextPath}/member/memberReserv/?rev_num=${R.rev_num}">[예약내역]</a>
									</c:if></c:forEach></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<nav aria-label="Page navigation mb-3">
				<ul class="pagination justify-content-center">

					<c:if test="${pm.prev}">
								<li class="page-item"><a class="page-link" href="${pm.startPage - 1}">&laquo;</a></li>
								<li class="page-item"><a class="page-link" href="1">1</a></li>
								<li class="page-item"><span>...</span></li>
							</c:if>
							<c:forEach begin="${pm.startPage}" end="${pm.endPage}" var="idx">
								<li class="page-item" ${pm.cri.page == idx ? ' class=active' : ''}>
									<a class="page-link" href="${idx}">${idx}</a>
								</li>
							</c:forEach>
							<c:if test="${pm.next}">
								<li class="page-item"><span>...</span></li>
								<li class="page-item"><a class="page-link" href="${pm.maxPage}">${pm.maxPage}</a></li>
								<li class="page-item"><a class="page-link" href="${pm.endPage + 1}">&raquo;</a></li>
							</c:if>

				</ul>
			</nav>
			<c:if test="${userInfo.member_id eq 'master'}">
			<div class="mb-4 text-end">
				<button class="btn btn-outline-secondary" id="addBtn">관리자 계정 추가</button>
			</div>
			</c:if>
		</div>
	</div>
</section>

	<form id="jobForm">
		<input type="hidden" name="page" value="${pm.cri.page}" /> 
		<input type="hidden" name="perPageNum" value="${pm.cri.perPageNum}" />
		<input id="inputCon" type="hidden" name="type" />
	</form>

<script src="${contextPath}/resources/js/popper.min.js"></script>
<script>
	document.querySelector("#addBtn").addEventListener("click", function () {
		location.href = "createAdmin";
	});
	
	$(".pagination li a").on("click",function(event){
		event.preventDefault();
		// page
		var targetPage = $(this).attr("href");
		
		var jobForm = $("#jobForm");
		
		jobForm.find("[name='page']").val(targetPage);
		jobForm.attr("action","adminPage").attr("method","GET");
		$("#inputCon").val("${param.type}");
		jobForm.submit();
	});
	
</script>
<%@ include file="../common/footer.jsp" %>