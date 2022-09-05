<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/header.jsp"%>
<section
	class="container d-flex flex-column justify-content-center align-items-center h-100">
	<div class="row w-100">
		<div class="col-md-8 offset-md-2 col-xl-6 offset-xl-3">
			<h3 class="mb-3">아이디/비밀번호 찾기</h3>
			<c:choose>
				<c:when test="${!empty id}">
					<form id="idForm" action="findId" method="post"
						class="mb-4 text-center">
						<div class="mb-3 row w-100">
							<label for="member_email" class="col-sm-2 col-form-label">E-mail</label>
							<div class="col-sm-8">
								<input class="form-control" id="member_email"
									name="member_email" type="email"
									placeholder="인증번호를 받을 이메일을 입력해주세요." />
							</div>
							<button class="col-sm-2 btn btn-primary h-100" type="button"
								id="acceptEmail">이메일 인증</button>
						</div>
						<div class="mb-3 row w-100" id="emailCodeWrap">
							<label for="member_email" class="col-sm-2 col-form-label">인증코드</label>
							<div class="col-sm-8">
								<input class="form-control" type="text" id="emailCode" />
							</div>
							<button class="col-sm-2 btn btn-primary" type="button"
								id="acceptId">인증완료</button>
						</div>
					</form>
					<div id="idFinder" class="input-group mb-3">
						<div class="input-group-prepend">
							<span class="input-group-text" id="basic-addon1">아이디</span>
						</div>
						<input type="text" class="form-control"
							placeholder="찾으신 아이디가 출력됩니다." aria-label="Username"
							aria-describedby="basic-addon1" id="sendIds" readonly />
					</div>
				</c:when>
				<c:otherwise>
					<form id="pwForm" action="findPw" method="post"
						class="mb-4 text-center">
						<div class="mb-3 row w-100">
							<label for="member_id" class="col-sm-2 col-form-label">아이디</label>
							<div class="col-sm-8">
								<input type="text" class="form-control" name="member_id"
									id="member_id" placeholder="비밀번호를 찾으실 아이디를 입력해주세요.">
							</div>
							<button class="col-sm-2 btn btn-primary h-100" type="button"
								id="reduplication_check">아이디 확인</button>
							<input type="hidden" name="reduCkConfirm" id="reduCkConfirm"
								value="N" />
						</div>
						<div class="mb-5 row w-100">
							<label for="member_emails" class="col-sm-2 col-form-label">E-mail</label>
							<div class="col-sm-8">
								<input class="form-control" id="member_emails"
									name="member_email" type="email"
									placeholder="인증번호를 받을 이메일을 입력해주세요." />
							</div>
							<button class="col-sm-2 btn btn-primary h-100" type="button"
								id="acceptEmails">이메일 인증</button>
						</div>
						<div class="mb-3 row w-100" id="emailCodeWrap">
							<div class="col-sm-2"></div>
							<div class="col-sm-8 row m-0 ps-0">
								<label for="emailCode" class="col-sm-4 col-form-label">인증코드</label>
								<div class="col-sm-8 px-0">
									<input class="form-control" type="text" id="emailCode" />
								</div>
							</div>
							<button class="col-sm-2 btn btn-primary h-100" type="button"
								id="acceptPw">인증완료</button>
						</div>
					</form>
					<div id="idFinder" class="input-group mb-3">
						<div class="input-group-prepend">
							<span class="input-group-text" id="basic-addon1">아이디</span>
						</div>
						<input type="text" class="form-control"
							placeholder="찾으신 아이디가 출력됩니다." aria-label="Username" id="sendId"
							aria-describedby="basic-addon1" readonly />
					</div>
					<div id="pwFinder" class="input-group mb-3">
						<div class="input-group-prepend">
							<span class="input-group-text" id="basic-addon1">비밀번호</span>
						</div>
						<input type="text" class="form-control"
							placeholder="찾으신 비밀번호가 출력됩니다." aria-label="Username" id="sendPw"
							aria-describedby="basic-addon1" readonly />
					</div>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</section>
<script>
	$("#idFinder").hide();
	$("#pwFinder").hide();

	var msg = '${message}';
	if (msg != '') {
		alert(msg);
	}

	// 아이디 확인 체크
	var idCheck = $('#reduCkConfirm').val();

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
					alert("등록된 아이디입니다.");
					$("#reduCkConfirm").attr("value", "Y");
				} else {
					alert("등록되지않은 아이디입니다.");
					$("#member_id").val("");
				}
			}
		});
	});

	// 인증 메일에 발송된 코드 저장
	// 아이디 찾기용
	var emailCode = "";
	$("#acceptEmail").click(
			function() {
				if ($("#member_email").val() == ""
						|| $("#member_email").val() == undefined
						|| $("#member_email").val() == null) {
					alert('이메일을 입력해주세요');
					return;
				}
				$.ajax({
					type : "GET",
					dataType : "text",
					url : "checkEmail",
					data : {
						member_email : $("#member_email").val()
					},
					success : function(code) {
						console.log(code);
						if (code) {
							emailCode = code;
							alert("메일 발송 완료\n메일을 확인해주세요.");
						} else {
							alert("메일 발송 실패 관리자에게 문의해 주세요.");
						}
					}
				});
			});

	// 인증코드 확인 완료 여부
	var boolEmailCode = false;
	//아이디 인증 코드 검사
	$("#acceptId").click(function() {
		if (emailCode == "" || emailCode == undefined || emailCode == null) {
			alert('코드가 송신되지않았습니다.이메일 인증을 진행해주세요.');
			return;
		}
		var userCode = $("#emailCode").val();
		if (emailCode == userCode) {
			console.log("일치");
			alert("이메일 인증이 완료 되었습니다.");
			$.ajax({
				url : "findInfo",
				type : "POST",
				dataType : "json",
				data : {
					"member_email" : $("#member_email").val()
				},
				success : function(data) {
					if (data) {
						alert('해당하는 아이디를 찾았습니다.');
						$("#idFinder").show();
						$("#sendIds").attr("value", data.member_id);
					} else {
						alert('해당 이메일은 등록되지않은 이메일 입니다. 가입하실때 입력하신 이메일을 입력해주세요.');
					}
				}
			});
		} else {
			boolEmailCode = false;
			alert("인증코드를 다시 확인해주세요");
		}
	});

	// 인증 메일에 발송된 코드 저장
	// 비밀번호 찾기용
	var emailCode = "";
	$("#acceptEmails").click(
			function() {
				if($("#reduCkConfirm").val() == "N"){
					alert('아이디 인증부터 진행해주세요.')
					return;
				}
				if ($("#member_emails").val() == ""
						|| $("#member_emails").val() == undefined
						|| $("#member_emails").val() == null) {
					alert('이메일을 입력해주세요');
					return;
				}
				$.ajax({
					type : "GET",
					dataType : "text",
					url : "checkEmail",
					data : {
						member_email : $("#member_emails").val()
					},
					success : function(code) {
						console.log(code);
						if (code) {
							emailCode = code;
							alert("메일 발송 완료\n메일을 확인해주세요.");
						} else {
							alert("메일 발송 실패 관리자에게 문의해 주세요.");
						}
					}
				});
			});

	// 인증코드 확인 완료 여부
	var boolEmailCode = false;
	console.log($("#member_emails").val());
	console.log(idCheck);
	console.log($("#reduCkConfirm").val());
	//비밀번호 인증 코드 검사
	$("#acceptPw").click(function() {
		if (emailCode == "" || emailCode == undefined || emailCode == null) {
			alert('코드가 송신되지않았습니다.이메일 인증을 진행해주세요.');
			return;
		}
		if ($("#reduCkConfirm") == "N") {
			alert('해당하는 아이디는 존재하지 않습니다.');
			$('#member_id').focus();
			return;
		}
		var userCode = $("#emailCode").val();
		if (emailCode == userCode) {
			console.log("일치");
			alert("이메일 인증이 완료 되었습니다.");
			$.ajax({
				url : "findInfo",
				type : "POST",
				dataType : "json",
				data : {
					"member_email" : $("#member_emails").val()
				},
				success : function(data) {
					if (data) {
						alert('임시 비밀번호가 발급되었습니다.');
						console.log(data);
						$("#idFinder").show();
						$("#pwFinder").show();
						$("#sendId").attr("value", data.member_id);
						$("#sendPw").attr("value", data.member_pw);
					} else {
						alert('해당 이메일은 등록되지않은 이메일 입니다. 가입하실때 입력하신 이메일을 입력해주세요.');
					}
				}
			});
		} else {
			boolEmailCode = false;
			alert("인증코드를 다시 확인해주세요");
		}
	});
	$(document).ajaxSend(function (e, xhr, options) {
		xhr.setRequestHeader('${_csrf.headerName}', '${_csrf.token}');
	});
</script>
<%@ include file="../common/footer.jsp" %>