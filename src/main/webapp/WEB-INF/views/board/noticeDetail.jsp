<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="../common/header.jsp" %>
<section class="container">
	<div class="row">
		<div class="col-md-2">
			<div class="card mb-3">
				<div class="card-header">
					고객센터
				</div>
				<div class="list-group list-group-flush">
					<a href="${contextPath}/board/faq" class="list-group-item" id="faqBoard">자주 묻는 질문</a> 
					<a href="${contextPath}/board/qna" class="list-group-item" id="qnaBoard">질문과 답변</a> 
					<a href="${contextPath}/board/notice" class="list-group-item active" id="noticeBoard">공지사항</a>
				</div>
			</div>
		</div>
		
		<div class="col-md-10 mb-5">
			<h2 class="mb-4">공지사항</h2>
			<div class="card mb-5">
				<div class="card-header">
					<c:if test="${userInfo.member_master eq 'Y'}">
					<button  id="delBtn" type="button" class="btn-close float-end" data-bs-toggle="modal" 
							data-bs-target="#staticBackdrop" onclick="setNum(${notice.notice_num})" data-what="${notice.notice_num}">
					</button>
					</c:if>
					<h3 class="card-title">${notice.notice_title }</h3>
					<p class="card-text text-muted d-inline">작성자: ${notice.notice_writer }</p><br/>
					<p class="card-text text-muted d-inline">작성일: <f:formatDate value="${notice.notice_regdate}" pattern="yyyy/MM/dd"/></p>
				</div>
				<div class="card-body">
					<p class="catd-text">${notice.notice_content }</p>
					
					
				<form name="noticeInfo" action="noticeDelete" method="POST">
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
					<input type="hidden" name="notice_num" value="${notice.notice_num}">
				</form>
				<c:if test="${!empty files}">
					<div class="card-footer">
						<p class="card-text">첨부파일</p>					
						<c:forEach var="file" items="${files}">
							<a class="d-block" href="${contextPath}/attach${file}">${file.substring(file.lastIndexOf("_") + 1)}</a>
						</c:forEach>
					</div>
				</c:if>
			</div>
		</div>
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
					<button id="btnDelAgree" type="button" class="btn btn-primary">삭제</button>
				</div>
				
			</div>
		</div>
	</div>
	
</section>
<script>
	$("#btnDelAgree").click(function(){
		$('form[name="noticeInfo"]').submit();
	});
</script>
<%@ include file="../common/footer.jsp" %>