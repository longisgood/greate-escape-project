<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../common/header.jsp"%>

<section class="container">
	<div class="row">
		<div class="col-md-2">
			<div class="card mb-3">
				<div class="card-header">고객센터</div>
				<div class="list-group list-group-flush">
					<a href="${contextPath}/board/faq" class="list-group-item active" id="faqBoard">자주 묻는 질문</a> 
					<a href="${contextPath}/board/qna" class="list-group-item" id="qnaBoard">질문과 답변</a> 
					<a href="${contextPath}/board/notice" class="list-group-item" id="noticeBoard">공지사항</a>
				</div>
			</div>
		</div>
		<div class="col-md-10 mb-5">
			<h2 class="mb-4">자주 묻는 질문</h2>
			<div class="row mb-4">
				<div class="col-8 offset-2">
					<form action="">
						<div class="input-group mb-3">
							<span class="input-group-text"><svg
									xmlns="http://www.w3.org/2000/svg" width="16" height="16"
									fill="currentColor" class="bi bi-search" viewBox="0 0 16 16">
									<path
										d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z" />
								</svg> </span> <input type="text" name="keyword" id="keyword" class="form-control" 
								aria-label="Amount (to the nearest dollar)" value="${param.keyword}" placeholder="검색어를 입력하세요">
							<button class="btn btn-dark" type="button" id="btnSearch">검색</button>
						</div>
					</form>
				</div>
			</div>
			<!-- 메뉴버튼 -->
			<div class="row mb-4">
				<div class="btn-group" role="group">
					<button value="전체" type="button" class="btn btn-outline-dark" id="all">전체보기</button>
					<button value="해외여행" type="button" class="btn btn-outline-dark" id="trip">해외여행</button>
					<button value="항공" type="button" class="btn btn-outline-dark" id="airline">항공</button>
					<button value="예약/결제" type="button" class="btn btn-outline-dark" id="reservation">예약/결제</button>
					<button value="여권/비자/환전" type="button" class="btn btn-outline-dark" id="passport">여권/비자/환전</button>
					<button value="홈페이지/기타" type="button" class="btn btn-outline-dark" id="other">홈페이지/기타</button>
				</div>
			</div>
			
			<!-- 페이징 -->
			<div id="listAll" class="accordion mb-3">
				<c:set var="number" value="0" />
				<c:choose>
					<c:when test="${list ne null }">
						<c:forEach var="faq" items="${list}">
							<c:set var="number" value="${number+=1}" />
							<div class="accordion mb-2" id="accordionPanelsStayOpenExample">
								<div class="accordion-item">
									<h2 class="accordion-header" id="panelsStayOpen-heading-${number}">
										<button class="accordion-button collapsed" type="button" data-bs-toggle="collapse"
											data-bs-target="#panelsStayOpen-collapse-${number}" aria-controls="panelsStayOpen-collapse-${number}">
											${faq.faq_category}&nbsp;&nbsp;|&nbsp;&nbsp;Q.&nbsp;${faq.faq_title }</button>
									</h2>
									<div id="panelsStayOpen-collapse-${number}" class="accordion-collapse collapse"
										aria-labelledby="panelsStayOpen-heading-${number}">
										<div class="accordion-body">${faq.faq_content }
											<c:if test="${userInfo.member_master eq 'Y'}">
												<button id="delBtn" type="button" class="btn btn-close"
													data-bs-toggle="modal" data-bs-target="#staticBackdrop"
													onclick="setNum(${faq.faq_num})" data-what="${faq.faq_num}"></button>
											</c:if>
										</div>
									</div>
								</div>
							</div>
							<form name="faqInfo">
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
								<input type="hidden" name="faq_num" value="${faq.faq_num}">
							</form>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<h1>게시글이 없습니다.</h1>
					</c:otherwise>
				</c:choose>
			</div>

			<!-- Modal -->
			<div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
				aria-labelledby="staticBackdropLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title" id="staticBackdropLabel">해당 글을 삭제하시겠습니까?</h5>
							<button type="button" class="btn-close" data-bs-dismiss="modal"aria-label="Close"></button>
						</div>
						<div class="modal-body">
							삭제된 게시글 정보는 다시 불러올수 없습니다. <br />주의사항을 확인 후 삭제하시겠습니까?
						</div>
						<div class="modal-footer">
							<button id="btnDelCancel" type="button" class="btn btn-secondary"
								data-bs-dismiss="modal">취소</button>
							<button id="btnDelAgree" type="button" class="btn btn-primary"
								onclick="clickDel(faqInfo)">삭제</button>
						</div>
					</div>
				</div>
			</div>
			
			<!-- 페이징처리 -->
			<div id="pagingAll">
				<nav id="allPaging" aria-label="Page navigation mb-3">
					<ul class="pagination justify-content-center">
						<c:if test="${pm.first}">
							<li class="page-item">
								<a class="page-link" href="${pm.makeQuery(1)}"> 
									<span aria-hidden="true">&laquo;</span>
								</a>
							</li>
						</c:if>
						<c:if test="${pm.prev}">
							<li class="page-item">
								<a class="page-link" href="${pm.makeQuery(pm.startPage-1)}">&lt;</a>
							</li>
						</c:if>
						<c:forEach var="i" begin="${pm.startPage}" end="${pm.endPage}">
							<li class="page-item" aria-current="page" ${( pm.cri.page ==i)? 'class="active"' :''}>
								<a id="pageNum" class="page-link" href="${pm.makeQuery(i)}">${i}</a>
							</li>
						</c:forEach>
						<c:if test="${pm.next}">
							<li class="page-item">
								<a class="page-link" href="${pm.makeQuery(pm.endPage+1)}">&gt;</a>
							</li>
						</c:if>
						<c:if test="${pm.last}">
							<li class="page-item">
								<a class="page-link" href="${pm.makeQuery(pm.maxPage)}"> 
									<span aria-hidden="true">&raquo;</span>
								</a>
							</li>
						</c:if>
					</ul>
				</nav>
			</div>
			
			<div class="row">
				<c:if test="${userInfo.member_master eq 'Y'}">
					<div class="text-end mb-3">
						<button class="btn btn-outline-secondary" id="addBtn">추가하기</button>
					</div>
				</c:if>

			</div>
		</div>
	</div>
</section>


<script>
	var faqnum;
	function setNum(num) {
		faqnum = num;
		console.log(faqnum)
	}
	var userInfo= "<%=session.getAttribute("userInfo")%>";
	console.log("userInfo: "+userInfo);
	var userMaster= '${userInfo.member_master}';
	
	$("#addBtn").click(function() {
		location.href = "${contextPath}/board/faqWrite";
	});

	$("#qnaBoard").click(function() {
		location.href = "board/qna";
	});

	$("#faqBoard").click(function() {
		location.href = "board/faq";
	});

	$("#noticeBoard").click(function() {
		location.href = "board/notice";
	});

	$("#all").on("click", function() {
		location.href="${contextPath}/board/faq";
	});

	$("#trip").on("click", function() {
		location.href="${contextPath}/board/faq/trip";
	});
	
	$("#airline").on("click", function() {
		location.href="${contextPath}/board/faq/airline";
	});

	$("#reservation").on("click", function() {
		location.href="${contextPath}/board/faq/reserv";
	});

	$("#passport").on("click", function() {
		location.href="${contextPath}/board/faq/pass";
	});

	$("#other").on("click", function() {
		location.href="${contextPath}/board/faq/other";
	});
	
	function clickDel(formName) {
		for (var tag of formName) {
			if (tag.faq_num.value == faqnum) {
				tag.action = "${contextPath}/board/faqDelete";
				tag.method = "post";
				tag.submit();
			}
		}
	}
	
	$("#btnSearch").click(function(){
		location.href="${contextPath}/board/faq?keyword="+$("#keyword").val();
		
	});
	
	$('input[type="text"]').keydown(function() {
		if (event.keyCode === 13) {
			event.preventDefault();
			$("#btnSearch").click();
		};
	});
	
	var pageUrl = window.location.href; 
	$(window).on('load', function(){ 
	    $('.btn-group').siblings('button').removeClass('active'); 
	    
	    if (pageUrl.indexOf('trip') > -1) { 
	        $("#trip").addClass('active');
	    } else if (pageUrl.indexOf('airline') > -1) { 
	    	 $("#airline").addClass('active');
	    } else if (pageUrl.indexOf('reserv') > -1) { 
	    	 $("#reservation").addClass('active');
	    } else if (pageUrl.indexOf('pass') > -1) { 
	    	 $("#passport").addClass('active');
	    } else if (pageUrl.indexOf('other') > -1) { 
	    	 $("#other").addClass('active');
	    } else if (pageUrl.indexOf('airline') > -1) { 
	    	 $(".btn-outline-dark").removeClass('active');
	    }
	});
	
</script>
<%@ include file="../common/footer.jsp"%>