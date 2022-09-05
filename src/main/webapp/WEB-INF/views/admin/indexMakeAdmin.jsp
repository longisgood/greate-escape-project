<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/header.jsp" %>
<section class="container">
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
			<h2 class="mb-5">관리자 계정 생성 </h2>
			<div class="table-responsive col-11">
				<form id="new" action="newAdmin" method="post">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
					<table class="table table-borderless">
						<tbody>
							<tr>
								<th>아이디</th>
								<td class="input-group mb-3"><input type="text" name="member_id" id="member_id" class="form-control">
								<button class="btn btn-primary" type="button" id="reduplication_check">중복확인</button>
								<input type="hidden" name="reduCkConfirm" id="reduCkConfirm" value="N" /></td>
							</tr>
							
							<tr>
								<th>비밀번호</th>
								<td><input type="password" name="member_pw" id="member_pw" class="form-control"></td>
							</tr>
							<tr>
								<th>비밀번호 확인</th>
								<td><input type="password" name="passwordCheck" id="passwordCheck" class="form-control"></td>
							</tr>
							<tr>
								<td></td>
								<td><font id="confirm" size="2"></font></td>
							</tr>
							<tr>
								<th>이름</th>
								<td><input type="text" name="member_name" id="member_name" class="form-control"></td>
							</tr>
							<tr>
								<th>성별</th>
								<td>
									<div class="form-check form-check-inline">
										<input class="form-check-input" type="radio"
											name="member_gender" id="male" value="male"> <label
											class="form-check-label" for="male">남성</label>
									</div>
									<div class="form-check form-check-inline">
										<input class="form-check-input" type="radio"
											name="member_gender" id="female" value="female"> <label
											class="form-check-label" for="female">여성</label>
									</div>
								</td>
							</tr>
							<tr>
								<th>생년월일</th>
								<td><input type="date" class="form-control" name="member_birth" id="member_birth" placeholder=""></td>
							</tr>
							<tr>
								<th>휴대폰</th>
								<td><input type="text" name="member_phone" id="member_phone" class="form-control"></td>
							</tr>
							<tr>
								<th>이메일</th>
								<td><input type="text" name="member_email" id="member_email" class="form-control"></td>
							</tr>
						</tbody>
					</table>
				</form>
				<div class="mb-4 text-end">
					<button class="btn btn-outline-secondary" id="addBtn">관리자 계정 추가</button>
				</div>
			</div>
		</div>
</section>
<script>

	$("#reduplication_check").click(function() {
		$.ajax({
			url : "redu_check",
			type : "POST",
			dataType : "json",
			data : {
				"member_id" : $("#member_id").val()
			},
			success : function(data) {
				if (data == 1) {
					alert("중복된 아이디입니다. 다시 입력해주세요.");
					$("#member_id").val("");
				} else {
					alert("사용가능한 아이디입니다.");
					$("#reduCkConfirm").attr("value", "Y");
				}
			}
		});
	});
	
	$(function () {
		$('#member_pw').keyup(function () {
			$('#confirm').html('');
		});

		$('#passwordCheck').keyup(function () {
			if ($('#member_pw').val() != $('#passwordCheck').val()) {
				$('#confirm').html('비밀번호가 일치하지 않습니다.');
				$('#confirm').attr('color', 'red');
			} else {
				$('#confirm').html('비밀번호가 일치합니다');
				$('#confirm').attr('color', 'blue');
			}
		});

	});
	
	$(function () {
		$('#addBtn').click(function () {
			var valId = $('#member_id').val();
			var valPass = $('#member_pw').val();
			var valCPass = $('#passwordCheck').val();
			var valName = $('#member_name').val();
			var valBirth = $('#member_birth').val();
			var valPhone = $('#member_phone').val();
			var valEmail = $('#member_email').val();
			var idCheck = $('#reduCkConfirm').val();


			if (valId == null || valId == undefined || valId == "") { alert('아이디를 입력해주세요.'); $('#member_id').focus(); return; }
			if (idCheck == "" || idCheck == "N") { alert('아이디 중복확인를 해주세요'); $('#reduCkConfirm').focus(); return; }
			if (valPass == null || valPass == undefined || valPass == "") { alert('비밀번호를 입력해주세요.'); $('#member_pw').focus(); return; }
			if (valPass != valCPass) { alert('비밀번호가 일치 하지 않습니다.'); $('#member_pw').val(""); $('#passwordCheck').val(""); $('#member_pw').focus(); return; }
			if (valName == null || valName == undefined || valName == "") { alert('이름을 입력해주세요.'); $('#member_name').focus(); return; }
			if (!$('#male').is(':checked') && !$('#female').is(':checked')) { alert('성별을 체크해주세요.'); $('#male').focus(); return; }
			if (valBirth == null || valBirth == undefined || valBirth == "") { alert('생년월일을 체크해주세요'); $("member_birth").focus(); return; }
			if (valPhone == null || valPhone == undefined || valPhone == "") { alert('핸드폰 번호를 입력해주세요'); $("member_phone").focus(); return; }
			if (valEmail == null || valEmail == undefined || valEmail == "") { alert('이메일을 입력해주세요.'); $('#member_email').focus(); return; }
			$('#new').submit();
		});
	});
	$(document).ajaxSend(function (e, xhr, options) {
		xhr.setRequestHeader('${_csrf.headerName}', '${_csrf.token}');
	});
	
</script>
<%@ include file="../common/footer.jsp" %>