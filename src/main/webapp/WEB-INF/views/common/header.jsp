<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>최고의 여행 - 대탈출</title>
	<link rel="stylesheet" href="${contextPath}/resources/bootstrap-5.2.0-dist/css/bootstrap.min.css">
	<link rel="stylesheet" href="${contextPath}/resources/css/btn-primary.css">
	<link rel="stylesheet" href="${contextPath}/resources/css/style.css">
	<script src="${contextPath}/resources/js/jquery.js"></script>
</head>

<body class="d-flex flex-column vh-100">
	<nav id="mainNavbar" class="navbar fixed-top navbar-expand-lg">
		<div class="container">
			<a class="navbar-brand" href="${contextPath}/"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-airplane-fill" viewBox="0 0 16 16">
					<path d="M6.428 1.151C6.708.591 7.213 0 8 0s1.292.592 1.572 1.151C9.861 1.73 10 2.431 10 3v3.691l5.17 2.585a1.5 1.5 0 0 1 .83 1.342V12a.5.5 0 0 1-.582.493l-5.507-.918-.375 2.253 1.318 1.318A.5.5 0 0 1 10.5 16h-5a.5.5 0 0 1-.354-.854l1.319-1.318-.376-2.253-5.507.918A.5.5 0 0 1 0 12v-1.382a1.5 1.5 0 0 1 .83-1.342L6 6.691V3c0-.568.14-1.271.428-1.849Z" />
				</svg>
				대탈출
			</a>
			<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>

			<div class="collapse navbar-collapse" id="navbarSupportedContent">
				<ul class="navbar-nav me-auto mb-2 mb-lg-0">
					<li class="nav-item"><a class="nav-link active" aria-current="page" href="${contextPath}/products/continent/아시아">아시아</a></li>
					<li class="nav-item"><a class="nav-link active" aria-current="page" href="${contextPath}/products/continent/유럽">유럽</a></li>
					<li class="nav-item"><a class="nav-link active" aria-current="page" href="${contextPath}/products/continent/아메리카">아메리카</a></li>
					<li class="nav-item"><a class="nav-link active" aria-current="page" href="${contextPath}/products/continent/오세아니아">오세아니아</a></li>
				</ul>
				<ul class="navbar-nav ml-auto mb-2 mb-lg-0">
					<sec:authorize access="authenticated" >
						<li class="nav-item">
							<form action="${contextPath}/member/logOut" method="POST">
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
								<button class="border-0 bg-transparent nav-link active" aria-current="page">로그아웃</button>
							</form>
						</li>
						<sec:authorize access="hasAnyRole('ROLE_MASTER','ROLE_ADMIN')">
							<li class="nav-item"><a class="nav-link active" aria-current="page" href="${contextPath}/member/adminPage">관리자페이지</a></li>
						</sec:authorize>
						<sec:authorize access="hasRole('ROLE_MEMBER')">
						<li class="nav-item"><a class="nav-link active" aria-current="page" href="${contextPath}/member/myPage">마이페이지</a></li>
						</sec:authorize>
					</sec:authorize>
					<sec:authorize access="anonymous">
						<li class="nav-item"><a class="nav-link active" aria-current="page" href="${contextPath}/member/login">로그인</a></li>
						<li class="nav-item"><a class="nav-link active" aria-current="page" href="${contextPath}/member/rev_check">예약확인</a></li>
					</sec:authorize>
					<li class="nav-item"><a class="nav-link active" aria-current="page" href="${contextPath}/board/faq">고객센터</a></li>
				</ul>
			</div>
		</div>
	</nav>
	<c:if test="${!empty flashMessage}">
		<div class="container">
			<div class="row">
				<div class="col-6 offset-3">
					<div class="alert alert-primary alert-dismissible fade show" role="alert">
						${flashMessage}
						<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
					</div>
				</div>
			</div>
		</div>
	</c:if>
