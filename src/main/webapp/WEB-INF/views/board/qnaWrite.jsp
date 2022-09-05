<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/header.jsp" %>
<section class="container">
	<div class="row">
		<div class="col-md-2">
			<div class="card mb-3">
				<div class="card-header">
					고객센터
				</div>
				<div class="list-group list-group-flush">
					<a href="${contextPath}/board/faq" class="list-group-item">자주 묻는 질문</a>
					<a href="${contextPath}/board/qna" class="list-group-item active">질문과 답변</a>
					<a href="${contextPath}/board/notice" class="list-group-item">공지사항</a>
				</div>
			</div>
		</div>
		
		<div class="col-md-10 mb-5">
			<h2 class="mb-4">질문과 답변</h2>
			<form action="qnaWrite" method="POST" class="h-100">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				<input type="hidden" name="member_num" value="${userInfo.member_num }">
				<input type="hidden" name="qna_writer" value="${userInfo.member_name}">
				<div class="text-end mb-3">
					${userInfo.member_name}
				</div>
				<div class="form-floating mb-3">
					<input type="text"  class="form-control" id="floatingInputTitle" name="qna_title">
					<label for="floatingInputTitle">제목</label>
				</div>
				<div class="form-floating mb-3">
					<textarea  class="form-control mytextarea" id="floatingTextarea" name="qna_question"></textarea>
					<label for="floatingTextarea">내용</label>
				</div>
				<button class="btn btn-outline-secondary">작성 완료</button>
			</form>
		</div>
	</div>
</section>
<script src="https://cdn.tiny.cloud/1/mreuxwvmvo99s2c3asxl4t6ujhyqgni44dt6mle4qlfz9pq6/tinymce/5/tinymce.min.js" referrerpolicy="origin"></script>
<script>
    tinymce.init({
        selector: '.mytextarea',
        height: 300,
        language: 'ko_KR',
        plugins: [
            'advlist autolink link image lists charmap print preview hr anchor pagebreak',
            'searchreplace wordcount visualblocks visualchars code fullscreen insertdatetime media nonbreaking',
            'table emoticons template paste help'
        ],
        toolbar: 'undo redo | styleselect | bold italic image | alignleft aligncenter alignright alignjustify | outdent indent',
        menubar: false,
        image_title: true,
        images_upload_url: '${contextPath}/products/htmlImage',
        images_reuse_filename: true,
        relative_urls: false,
        file_picker_types: 'image',
        file_picker_callback: function (cb, value, meta) {
            var input = document.createElement('input');
            input.setAttribute('type', 'file');
            input.setAttribute('accept', 'image/*');
            input.onchange = function () {
                var file = this.files[0];
                var reader = new FileReader();
                reader.onload = function () {
                    var id = 'blobid' + (new Date()).getTime();
                    var blobCache = tinymce.activeEditor.editorUpload.blobCache;
                    var base64 = reader.result.split(',')[1];
                    var blobInfo = blobCache.create(id, file, base64);
                    blobCache.add(blobInfo);
                    cb(blobInfo.blobUri(), { title: file.name });
                };
                reader.readAsDataURL(file);
            }
            input.click();
        }
    });
</script>
<%@ include file="../common/footer.jsp" %>