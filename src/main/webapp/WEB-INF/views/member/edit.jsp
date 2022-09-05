<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/header.jsp" %>
<section class="container">
	<div class="row">
		<div class="col-md-2">
			<div class="card mb-3">
				<div class="card-header">
					마이 페이지
				</div>
				<div class="list-group list-group-flush">
					<a href="myPage" class="list-group-item">예약내역 확인</a>
					<a href="edit_check" class="list-group-item active">정보수정/탈퇴</a>
				</div>
			</div>
		</div>
		<div class="col-md-10 mb-5">
			<h2 class="mb-4">정보수정/탈퇴</h2>
			<div class="row">
				<div class="col-10 offset-1">
					<form id="changeForm" action="modify" method="post">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
						<input type="hidden" name="member_num" value="${userInfo.member_num}"/>
						<div class="mb-3">
							<label for="id" class="form-label">아이디</label>
							<input type="text" class="form-control" name="member_id" id="member_id" value="${userInfo.member_id}" readonly/>
						</div>
						<div class="mb-3">
							<label for="password" class="form-label">비밀번호</label>
							<input type="password" class="form-control" name="member_pw" id="member_pw"/>
						</div>
						<div class="mb-3">
							<label for="password2" class="form-label">비밀번호 확인</label>
							<font id="confirm" size="2"></font>
							<input type="password" class="form-control" id="passwordCheck"/>
						</div>
						<div class="row">
							<div class="col-md-6 mb-3">
								<label for="name" class="form-label">이름</label>
								<input type="text" class="form-control" name="member_name" id="member_name" value="${userInfo.member_name}" readonly/>
							</div>
							<div class="col-md-6 mb-3">
								<div>
									<label for="gender" class="form-label">성별</label>
								</div>
								<div class="pt-1">
								<c:choose>
									<c:when test="${userInfo.member_gender eq 'male'}">
											<div class="form-check form-check-inline">
												<input class="form-check-input" type="radio"
													name="member_gender" id="male" value="male" checked /> <label
													class="form-check-label" for="male">남성</label>
											</div>
											<div class="form-check form-check-inline">
												<input class="form-check-input" type="radio"
													name="member_gender" id="female" value="female"> <label
													class="form-check-label" for="female">여성</label>
											</div>
										</c:when>
									<c:otherwise>
											<div class="form-check form-check-inline">
												<input class="form-check-input" type="radio"
													name="member_gender" id="male" value="male"> <label
													class="form-check-label" for="male">남성</label>
											</div>
											<div class="form-check form-check-inline">
												<input class="form-check-input" type="radio"
													name="member_gender" id="female" value="female" checked />
												<label class="form-check-label" for="female">여성</label>
											</div>
										</c:otherwise>
								</c:choose>
								</div>
							</div>
						</div>
						<div class="mb-3">
							<label for="birth" class="form-label">생년월일</label>
							<input type="date" class="form-control" name="member_birth" id="member_birth" value="${userInfo.member_birth}"/>
						</div>
						<div class="mb-3">
							<label for="phone" class="form-label">핸드폰</label>
							<input type="text" class="form-control" name="member_phone" id="member_phone" value="${userInfo.member_phone}"/>
						</div>
						<div class="mb-3">
							<label for="addr" class="form-label">주소</label>
							<div class="input-group mb-2">
								<input type="text" class="form-control" id="sample6_address" name="member_address"
									placeholder="주소" value="${userInfo.member_address}" >
								<input type="button" class="btn btn-outline-secondary"
									onclick="sample6_execDaumPostcode()" value="주소 찾기">
							</div>
							<div class="row g-3">
								<div class="col-md-8">
									<input type="text" class="form-control" name="member_address_detail"
										id="sample6_detailAddress" value="${userInfo.member_address_detail}" placeholder="상세주소">
								</div>
							</div>
						</div>
						<div class="mb-3">
							<label for="email" class="form-label">이메일</label>
							<input type="text" class="form-control" name="member_email" id="member_email" value="${userInfo.member_email}"/>
						</div>
					</form>
				</div>
			</div>
			<div class="text-center mb-5">
				<button id="changeBtn" class="btn btn-secondary">수정완료</button>
				<button id="withdrawBtn" class="btn btn-secondary">회원탈퇴</button>
			</div>
		</div>
	</div>
</section>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>

	function sample6_execDaumPostcode() {
		new daum.Postcode({
			oncomplete : function(data) {
				var addr = ''; // 주소 변수
				var extraAddr = ''; // 참고항목 변수
				if (data.userSelectedType === 'R') {
					addr = data.roadAddress;
				} else {
					addr = data.jibunAddress;
				}
				document.getElementById("sample6_address").value = "("
						+ data.zonecode + ")" + addr;
				document.getElementById("sample6_detailAddress").focus();
			}
		}).open();
	}

	$(function() {
		$('#member_pw').keyup(function() {
			$('#confirm').html('');
		});

		$('#passwordCheck').keyup(function() {
			if ($('#member_pw').val() != $('#passwordCheck').val()) {
				$('#confirm').html('비밀번호 일치하지 않습니다.');
				$('#confirm').attr('color', 'red');
			} else {
				$('#confirm').html('비밀번호가 일치합니다.');
				$('#confirm').attr('color', 'blue');
			}
		});

	});
	
	
	$('#changeBtn').click(function() {

		var valId = $('#member_id').val();
		var valPass = $('#member_pw').val();
		var valCPass = $('#passwordCheck').val();
		var valName = $('#member_name').val();
		var valBirth = $('#member_birth').val();
		var valPhone = $('#member_phone').val();
		var valEmail = $('#member_email').val();
		var idCheck = $('#reduCkConfirm').val();

		if (valId == null || valId == undefined || valId == "") {
			alert('아이디를 입력해주세요.');
			$('#member_id').focus();
			return;
		}
		if (idCheck == "" || idCheck == "N") {
			alert('아이디 중복확인를 해주세요');
			$('#reduCkConfirm').focus();
			return;
		}
		if (valPass == null || valPass == undefined || valPass == "") {
			alert('비밀번호를 입력해주세요.');
			$('#member_pw').focus();
			return;
		}
		if (valPass != valCPass) {
			alert('비밀번호가 일치 하지 않습니다.');
			$('#member_pw').val("");
			$('#passwordCheck').val("");
			$('#member_pw').focus();
			return;
		}
		if (valName == null || valName == undefined || valName == "") {
			alert('이름을 입력해주세요.');
			$('#member_name').focus();
			return;
		}
		if (!$('#male').is(':checked') && !$('#female').is(':checked')) {
			alert('성별을 체크해주세요.');
			$('#male').focus();
			return;
		}
		if (valBirth == null || valBirth == undefined || valBirth == "") {
			alert('생년월일을 체크해주세요');
			$("member_birth").focus();
			return;
		}
		if (valPhone == null || valPhone == undefined || valPhone == "") {
			alert('핸드폰 번호를 입력해주세요');
			$("member_phone").focus();
			return;
		}
		if (valEmail == null || valEmail == undefined || valEmail == "") {
			alert('이메일을 입력해주세요.');
			$('#member_email').focus();
			return;
		}
		$('#changeForm').submit();
	});
	
	var msg = '${message}';
		if(msg != ''){
			alert(msg);
		}
	
	$("#withdrawBtn").click(function(){
		location.href='withdraw';
	});	
</script>
<%@ include file="../common/footer.jsp" %>