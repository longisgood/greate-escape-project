<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/header.jsp" %>
<section class="container d-flex flex-column justify-content-center align-items-center h-100">
	<form id="joinForm" action="joinPost" method="post" class="row mb-3">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		<h3 class="mb-3">회원가입</h3>
		<div class="col-md-6">
			<label for="id" class="form-label">아이디</label>
			<div class="input-group mb-3">
				<input type="text" class="form-control" name="member_id" id="member_id" placeholder="">
				<button class="btn btn-primary" type="button" id="reduplication_check">중복확인</button>
			</div>
			<input type="hidden" name="reduCkConfirm" id="reduCkConfirm" value="N" />
			<div class="mb-3">
				<label for="password" class="form-label">비밀번호</label>
				<input type="password" class="form-control" name="member_pw" id="member_pw" placeholder="">
			</div>
			<div class="mb-3">
				<label for="password2" class="form-label">비밀번호 확인</label>
				<font id="confirm" size="2"></font>
				<input type="password" class="form-control" name="passwordCheck" id="passwordCheck" placeholder="">
			</div>
			<div class="mb-3">
				<label for="name" class="form-label">이름</label>
				<input type="text" class="form-control" name="member_name" id="member_name" placeholder="">
			</div>
			<div class="mb-3">
				<div class="d-flex">
					<label for="gender" class="form-label me-auto">성별</label>
					<div class="form-check form-check-inline">
						<input class="form-check-input" type="radio" name="member_gender" id="male" value="male">
						<label class="form-check-label" for="male">남성</label>
					</div>
					<div class="form-check form-check-inline">
						<input class="form-check-input" type="radio" name="member_gender" id="female" value="female">
						<label class="form-check-label" for="female">여성</label>
					</div>
				</div>
			</div>
		</div>
		<div class="col-md-6">
			<div class="mb-3">
				<label for="birth" class="form-label">생년월일</label>
				<input type="date" class="form-control" name="member_birth" id="member_birth" placeholder="">
			</div>
			<div class="mb-3">
				<label for="phone" class="form-label">핸드폰</label>
				<input type="text" class="form-control" name="member_phone" id="member_phone" placeholder="'-'없이 입력부탁드립니다.">
			</div>
			<div class="mb-3">
				<label for="addr" class="form-label">주소</label>
				<div class="input-group mb-2">
					<input type="text" class="form-control" id="sample6_address" name="member_address" placeholder="주소">
					<input type="button" class="btn btn-primary" onclick="sample6_execDaumPostcode()" value="주소 찾기">
				</div>
				<div class="row g-3">
					<div class="col">
						<input type="text" class="form-control" name="member_address_detail" id="sample6_detailAddress" placeholder="상세주소">
					</div>
				</div>
			</div>
			<div class="mb-3">
				<label for="email" class="form-label">이메일</label>
				<input type="text" class="form-control" name="member_email" id="member_email" placeholder="비밀번호 찾기에 사용됩니다.">
			</div>
		</div>
	</form>
	<div class="text-center">
		<button id="submitBtn" class="btn btn-primary mx-3">가입하기</button>
		<button id="cancelBtn" class="btn btn-primary mx-3">가입취소</button>
	</div>
	</form>
</section>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="https://code.jquery.com/jquery-latest.min.js"></script>
<script>
	function sample6_execDaumPostcode() {
		new daum.Postcode({
			oncomplete: function (data) {
				var addr = ''; // 주소 변수
				var extraAddr = ''; // 참고항목 변수
				if (data.userSelectedType === 'R') {
					addr = data.roadAddress;
				} else {
					addr = data.jibunAddress;
				}
				document.getElementById("sample6_address").value = "(" + data.zonecode + ")" + addr;
				document.getElementById("sample6_detailAddress").focus();
			}
		}).open();
	}

	var msg = '${message}';
	if (msg != '') {
		alert(msg);
	}

	$("#reduplication_check").click(function () {
		$.ajax({
			url: "redu_check",
			type: "POST",
			dataType: "json",
			data: { "member_id": $("#member_id").val() },
			success: function (data) {
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

	$("#cancelBtn").click(function () {
		location.href = "login";
	});

	$(function () {
		$('#submitBtn').click(function () {
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
			$('#joinForm').submit();
		});
	});

	$(document).ajaxSend(function (e, xhr, options) {
		xhr.setRequestHeader('${_csrf.headerName}', '${_csrf.token}');
	});
</script>
<%@ include file="../common/footer.jsp" %>