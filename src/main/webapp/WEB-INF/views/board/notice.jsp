<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="../common/header.jsp"%>
<style>
 	a{
 		text-decoration: none;
 	}
</style>
<section class="container">
	<div class="row">
		<div class="col-md-2">
			<div class="card mb-3">
				<div class="card-header">고객센터</div>
				<div class="list-group list-group-flush">
					<a href="${contextPath}/board/faq" class="list-group-item" id="faqBoard">자주 묻는 질문</a> 
					<a href="${contextPath}/board/qna" class="list-group-item" id="qnaBoard">질문과 답변</a> 
					<a href="${contextPath}/board/notice" class="list-group-item active" id="noticeBoard">공지사항</a>
				</div>
			</div>
		</div>
	
		<div class="col-md-10 mb-5">
			<h2 class="mb-4">공지사항</h2>
			<div class="mb-4">
				<div class="btn-group" role="group">
					<button type="button" value="전체" class="btn btn-outline-dark" id="all">전체</button>
					<button type="button" value="공지사항" class="btn btn-outline-dark" id="notice">공지사항</button>
					<button type="button" value="외교부소식" class="btn btn-outline-dark" id="mofa">외교부소식</button>
				</div>
			</div>

			<div class="table-responsive" id="listAll">
				<table class="table table-striped">
					<thead>
					<tr class="table-warning">
						<th class="col-sm-2">구분</th>
						<th class="col-sm-6">제목</th>
						<th class="col-sm-2">작성자</th>
						<th class="col-sm-2">작성일</th>
					</tr>
					</thead>
					<tbody>
					<c:choose>
						<c:when test="${noticeList ne null }">
							<c:forEach var="n" items="${noticeList }">
								<tr>
									<td class="col-sm-2">${n.notice_category}</td>
									<td class="col-sm-6"><a href="${contextPath}/board/noticeDetail?notice_num=${n.notice_num}">${n.notice_title}</a></td>
									<td class="col-sm-2">${n.notice_writer}</td>
									<td class="col-sm-2"><f:formatDate value="${n.notice_regdate}" pattern="yyyy.MM.dd"/></td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="5"><h1>현재 게시글이 존재하지 않습니다.</h1></td>
							</tr>
						</c:otherwise>
					</c:choose>
					</tbody>
				</table>
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
			
			<div id="pagingAll">
			<nav id="allPaging" aria-label="Page navigation mb-3">
				<ul class="pagination justify-content-center">
					<c:if test="${pm.first}">
						<li class="page-item"><a class="page-link" href="${pm.makeQuery(1)}"
							aria-label="Previous"> <span aria-hidden="true">&laquo;</span></a>
						</li>
					</c:if>
					<c:if test="${pm.prev}">
						<li class="page-item"><a class="page-link"
							href="${pm.makeQuery(pm.startPage-1)}">&lt;</a>
						</li>
					</c:if>
					<c:forEach var="i" begin="${pm.startPage}" end="${pm.endPage}">
						<li class="page-item " ${pm.cri.page ==i? 'class="active"':''}>
							<a class="page-link" href="${pm.makeQuery(i)}">${i}</a>
						</li>
					</c:forEach>
					<c:if test="${pm.next}">
						<li class="page-item"><a class="page-link" href="${pm.makeQuery(pm.endPage+1)}">&gt;</a>
						</li>
					</c:if>
					<c:if test="${pm.last}">
						<li class="page-item"><a class="page-link" href="${pm.makeQuery(pm.maxPage)}"
							aria-label="Previous"> <span aria-hidden="true">&raquo;</span></a>
						</li>
					</c:if>
				</ul>
			</nav>
			</div>
			<c:if test="${userInfo.member_master eq 'Y'}">
					<div class="text-end mb-3">
						<button class="btn btn-outline-secondary" id="addBtn">새글 작성</button>
					</div>
			</c:if>
		</div>
	</div>
</section>
<script>
	$("#addBtn").click(function() {
		location.href = "${contextPath}/board/noticeWrite";
	});
	
	$("#all").on("click", function() {
		location.href="${contextPath}/board/notice";
	});

	$("#notice").on("click", function(){
		location.href="${contextPath}/board/notice/inform";
	});
	
	$("#mofa").on("click", function(){
		location.href="${contextPath}/board/notice/mofa";
	});

	var pageUrl = window.location.href; 
	$(window).on('load', function(){ 
	    $('.btn-group').siblings('button').removeClass('active'); 
	    
	    if (pageUrl.indexOf('inform') > -1) { 
	        $("#notice").addClass('active');
	    } else if (pageUrl.indexOf('mofa') > -1) { 
	    	 $("#mofa").addClass('active');
	    }  else if (pageUrl.indexOf('airline') > -1) { 
	    	 $(".btn-outline-dark").removeClass('active');
	    }
	    
	});
</script>
<%@ include file="../common/footer.jsp"%>