<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/header.jsp" %>
<section class="container">
	<form id="reForm" action="${contextPath}/member/logOut" method="POST">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	</form>
</section>
<script>
	$(function() {
		$("#reForm").submit();
	});
</script>
<%@ include file="../common/footer.jsp" %>