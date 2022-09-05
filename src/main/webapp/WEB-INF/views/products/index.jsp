<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/header.jsp" %>
<link rel="stylesheet" href="${contextPath}/resources/css/product.index.css">
<c:choose>
	<c:when test="${empty list}">
		<section class="container d-flex flex-column justify-content-center align-items-center h-100">
			<div class="alert alert-danger" role="alert">
				<h4 class="alert-heading m-0">일치하는 상품 정보가 존재하지 않습니다.</h4>
			</div>
			<sec:authorize access="hasAnyRole('ROLE_MASTER','ROLE_ADMIN')">
				<a id="newBtn" href="${contextPath}/products/new" class="btn btn-primary">상품 등록</a>
			</sec:authorize>
		</section>
	</c:when>
	<c:otherwise>
		<section class="container d-flex justify-content-center mt-5">
			<div class="row w-100">
				<main class="col-lg-8 offset-lg-2 col-md-10 offset-md-1">
					<%-- <h3 class="mb-3">${list[0].product_continent}</h3> --%>
					<div>
					<h3 class="mb-3 d-inline-block">${continent}</h3>
					<c:if test="${userInfo.member_id eq 'master'}">
						<a id="newBtn" href="${contextPath}/products/new" class="btn btn-sm btn-primary ms-3">상품 등록</a>
					</c:if>
					</div>
					<c:forEach var="country" items="${countrySet}">
						<div class="card mb-4">
							<div class="card-header">
								<h4 class="card-title m-0">${country}</h4>
							</div>
							<c:forEach var="product" items="${list}">
								<c:if test="${product.product_country eq country}">
									<div class="card-body border-bottom">
										<div class="row g-0">
											<div class="col-sm-10 col-md-9 col-lg-8 col-xl-7 d-flex flex-column justify-content-center">
												<p class="card-title">[${product.product_city}] ${product.product_name}</p>
												<p class="card-text">
													${product.product_airplane}
													<fmt:formatDate value="${product.product_departure}" pattern="yyyy-MM-dd" /> ~
													<fmt:formatDate value="${product.product_arrive}" pattern="yyyy-MM-dd" />
												</p>
											</div>
											<div class="col-sm-2 col-md-3 col-lg-4 col-xl-5 text-center text-sm-end">
												<p class="card-text">잔여좌석: ${product.product_seat}</p>
												<c:choose>
													<c:when test="${product.product_seat > 5}">
														<button class="btn btn-primary" data-num="${product.product_num}">상세보기</button>
													</c:when>
													<c:otherwise>
														<c:if test="${product.product_seat > 0}">
															<button type="button" class="btn btn-warning position-relative" data-num="${product.product_num}">상세보기
															<span class="position-absolute top-0 start-100 translate-middle badge rounded-pill text-bg-warning">마감 임박!</span>
														</button>
															</c:if>
															<c:if test="${product.product_seat eq 0}">
															<button type="button" class="btn btn-danger position-relative" data-num="${product.product_num}">상세보기
															<span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">마감!</span>
														</button>
														</c:if>
													</c:otherwise>
												</c:choose>
											</div>
										</div>
									</div>
								</c:if>
							</c:forEach>
						</div>
					</c:forEach>
				</main>
			</div>
			<div class="card mb-3 position-fixed translate-middle d-none d-lg-block">
				<div class="card-header ">
					<%-- <h3 class="fs-5 card-title text-center m-0">${list[0].product_continent}</h3> --%>
					<h3 class="fs-5 card-title text-center m-0">${continent}</h3>
				</div>
				<c:set var="countryNum" value="1" />
				<c:forEach var="country" items="${countrySet}">
					<div class="accordion" id="accordion">
						<div class="accordion-item">
							<h4 class="accordion-header" id="heading${countryNum}">
								<button class="accordion-button <c:if test="${countryNum ne 1}">collapsed</c:if>" type="button" data-bs-toggle="collapse" data-bs-target="#collapse${countryNum}" aria-expanded="true" aria-controls="collapse${countryNum}">
									${country}
								</button>
							</h4>

							<div id="collapse${countryNum}" class="accordion-collapse collapse <c:if test="${countryNum eq 1}">show</c:if>" aria-labelledby="heading${countryNum}">
								<c:forEach var="city" items="${cityMap.get(country)}">
									<div class="accordion-body" data-city="${city}">
										${city}
									</div>
								</c:forEach>
							</div>

						</div>
					</div>
					<c:set var="countryNum" value="${countryNum + 1}" />
				</c:forEach>
			</div>
		</section>
	</c:otherwise>
</c:choose>
<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
<script>
	const main = document.querySelector("main");

	document.querySelectorAll(".accordion-body").forEach(tag => tag.addEventListener("click", function () {
		axios.get("${contextPath}/products/city/" + this.getAttribute("data-city"))
			.then(function (response) {
				let html = makeHtml(response.data);
				main.innerHTML = html;
			})
			.catch(function (error) {
				console.log(error);
			})
	}));

	const makeHtml = array => {
		let html = '<h3 class="mb-3">' + array[0].product_country + '</h3>';
		html += '<div class="card mb-4">';
		html += '<div class="card-header">';
		html += '<h4 class="card-title m-0">' + array[0].product_city + '</h4>';
		html += '</div>';
		for (let product of array) {
			html += '<div class="card-body border-bottom">';
			html += '<div class="row g-0">';
			html += '<div class="col-sm-10 col-md-9 col-lg-8 col-xl-7 d-flex flex-column justify-content-center">';
			html += '<p class="card-title">[' + product.product_city + '] ' + product.product_name + '</p>';
			html += '<p class="card-text">';
			html += product.product_airplane + ' ';
			html += dateString(new Date(product.product_departure));
			html += ' ~ ';
			html += dateString(new Date(product.product_arrive));
			html += '</p>';
			html += '</div>';
			html += '<div class="col-sm-2 col-md-3 col-lg-4 col-xl-5 text-center text-sm-end">';
			html += '<p class="card-text">잔여좌석:' + product.product_seat + '</p>';
			const seat = product.product_seat;
			if (seat > 5) {
				html += '<button class="btn btn-primary" data-num="' + product.product_num + '">상세보기</button>';
			} else {
				html += '<button type="button" class="btn btn-danger position-relative" data-num="' + product.product_num + '">상세보기';
				html += '<span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">마감 임박!</span></button>';
			}
			html += '</div></div></div>';
		}
		return html;
	};

	const dateString = date => {
		const year = date.getFullYear();
		let month = date.getMonth() + 1;
		let day = date.getDate();
		month = (month >= 10) ? month : "0" + month;
		day = (day >= 10) ? day : "0" + day;
		return year + "-" + month + "-" + day;
	};

	main.addEventListener("click", function (event) {
		const target = event.target;
		if (target.tagName !== "BUTTON") return;
		location.href = "${contextPath}/products/" + target.getAttribute("data-num");
	});
</script>
<%@ include file="../common/footer.jsp" %>