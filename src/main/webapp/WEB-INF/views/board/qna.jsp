<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="../common/header.jsp"%>
<style>
 	a{
 		text-decoration: none;
 	}
</style>
<section class="container">
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	<div class="row">
		<div class="col-md-2">
			<div class="card mb-3">
				<div class="card-header">고객센터</div>
				<div class="list-group list-group-flush">
					<a href="${contextPath}/board/faq" class="list-group-item" id="faqBoard">자주 묻는 질문</a> 
					<a href="${contextPath}/board/qna" class="list-group-item active" id="qnaBoard">질문과 답변</a> 
					<a href="${contextPath}/board/notice" class="list-group-item" id="noticeBoard">공지사항</a>
				</div>
			</div>
		</div>
		<div class="col-md-10 mb-5">
			<h2 class="mb-4">질문과 답변</h2>
			<div class="mb-4">
				<div class="dropdown">
					<a class="btn btn-outline-secondary dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
						보기 </a>
					<ul class="dropdown-menu">
						<li><a class="dropdown-item" href="${contextPath}/board/qna">전체</a></li>
						<li><a class="dropdown-item" href="${contextPath}/board/qna/checking">확인중</a></li>
						<li><a class="dropdown-item" href="${contextPath}/board/qna/checked">답변 완료</a></li>
					</ul>
				</div>
			</div>
			<div class="table-responsive">
				<table class="table">
					<thead>
						<tr class="table-warning">
							<th class="col-sm-2">구분</th>
							<th class="col-sm-6">제목</th>
							<th class="col-sm-2">글쓴이</th>
							<th class="col-sm-2">등록일</th>
						</tr>
					</thead>
					<tbody class="table table-info">
						<c:choose>
							<c:when test="${qnaNoticeList ne null }">
								<c:forEach var="n" items="${qnaNoticeList }">
									<tr>
										<td class="col-sm-2">${n.notice_category}</td>
										<td class="col-sm-6">
											<a href="${contextPath}/board/noticeDetail?notice_num=${n.notice_num}">${n.notice_title}</a></td>
										<td class="col-sm-2">${n.notice_writer}</td>
										<td class="col-sm-2"><f:formatDate value="${n.notice_regdate}" pattern="yyyy.MM.dd" /></td>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr>
									<td colspan="4"><h1>현재 게시글이 존재하지 않습니다.</h1></td>
								</tr>
							</c:otherwise>
						</c:choose>
					</tbody>
					<tbody class="table table-striped">
						<c:choose>
						<c:when test="${qnaList ne null}">
							<c:forEach var="q" items="${qnaList }">
								<tr>
									<c:choose>
										<c:when test="${q.qna_answer eq null}">
											<td id="qna_status" class="col-sm-1">확인중</td>
										</c:when>
										<c:otherwise>
											<td id="qna_status" class="col-sm-1">답변완료</td>
										</c:otherwise>
									</c:choose>
									
									<c:choose>
										<c:when test="${q.qna_writer eq userInfo.member_name or userInfo.member_master eq 'Y' }">
											<td id="qnaTitle" class="col-sm-6"><a href="${contextPath}/board/qnaDetail?qna_num=${q.qna_num}">${q.qna_title}</a></td>
										</c:when>
										<c:otherwise>
											<td class="col-sm-6">작성자 외에는 확인할수 없습니다.</td>
										</c:otherwise>
									</c:choose>
									<td id="qnaWriter" class="col-sm-2"><c:set var="writer" value="${q.qna_writer}"/><c:set var="index" value="${fn:length(writer)}"/>
									${fn:substring(writer, 0, 1)}<c:forEach begin="2" end="${fn:length(writer)-1}">*</c:forEach>${fn:substring(writer, index-1, index)}
									</td>
									<td class="col-sm-2"><f:formatDate value="${q.qna_regdate}" pattern="yyyy.MM.dd" /></td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="4"><h1>현재 게시글이 존재하지 않습니다.</h1></td>
							</tr>
						</c:otherwise>
						</c:choose>
					</tbody>
				</table>
			</div>
			<c:if test="${pm ne null}">
				<nav aria-label="Page navigation mb-3">
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
							<li class="page-item" aria-current="page" ${pm.cri.page ==i? 'class="active"':''}>
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
			</c:if>
			<div class="mb-4 text-end">
			
				<button type="button" class="btn btn-outline-secondary" id="addBtn">글쓰기</button>
				<button type="button" class="btn btn-outline-secondary" id="myQNA">나의 질문</button>
			</div>
		</div>
	</div>
</section>
<script src="${contextPath}/resources/js/popper.min.js"></script>
<script>
	$("#addBtn").click(function(){
		if(${sessionScope.userInfo eq null}){
			alert('로그인이 필요한 서비스입니다.로그인페이지로 이동합니다.');
			location.href="${contextPath}/member/login";
		}
		location.href="qnaWrite";
	});
	
	$("#myQNA").click(function(){
		if(${sessionScope.userInfo eq null}){
			alert('로그인이 필요한 서비스입니다.로그인페이지로 이동합니다.');
			location.href="${contextPath}/member/login";
		}
		location.href="${contextPath}/board/myQNA";
	});	
</script>
<%@ include file="../common/footer.jsp"%>