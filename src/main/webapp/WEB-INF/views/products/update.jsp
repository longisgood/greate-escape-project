<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/header.jsp" %>
<section class="container mb-3">
    <h3 class="mb-3">여행상품 수정</h3>
    <form action="${contextPath}/products/${product.product_num}/update" method="post" enctype="multipart/form-data">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        <div class="row">
            <div class="input-group mb-3 col">
                <span class="input-group-text">대륙</span>
                <div class="form-floating">
                    <select class="form-select" name="product_continent" id="selectContinent">
                        <option value="아시아" <c:if test="${product.product_continent eq '아시아'}">selected</c:if>>아시아</option>
                        <option value="유럽" <c:if test="${product.product_continent eq '유럽'}">selected</c:if>>유럽</option>
                        <option value="아메리카" <c:if test="${product.product_continent eq '아메리카'}">selected</c:if>>아메리카</option>
                        <option value="오세아니아" <c:if test="${product.product_continent eq '오세아니아'}">selected</c:if>>오세아니아</option>
                    </select>
                    <label for="selectContinent">대륙</label>
                </div>
            </div>
            <div class="input-group mb-3 col">
                <span class="input-group-text">나라</span>
                <div class="form-floating">
                    <input type="text" class="form-control" id="inputCountry" name="product_country" value="${product.product_country}">
                    <label for="inputCountry">나라이름</label>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="input-group mb-3 col">
                <span class="input-group-text">지역</span>
                <div class="form-floating">
                    <input type="text" class="form-control" id="inputCity" name="product_city" value="${product.product_city}">
                    <label for="inputCity">지역이름</label>
                </div>
            </div>
            <div class="input-group mb-3 col">
                <span class="input-group-text">요금</span>
                <div class="form-floating">
                    <input type="number" class="form-control" id="inputAdult" name="product_adult" value="${product.product_adult}">
                    <label for="inputAdult">성인</label>
                </div>
                <div class="form-floating">
                    <input type="number" class="form-control" id="inputMinor" name="product_minor" value="${product.product_minor}">
                    <label for="inputMinor">소인</label>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="input-group mb-3 col">
                <span class="input-group-text">항공편</span>
                <div class="form-floating">
                    <input type="text" class="form-control" id="inputAirplane" name="product_airplane" value="${product.product_airplane}">
                    <label for="inputAirplane">--항공</label>
                </div>
            </div>
            <div class="input-group mb-3 col">
                <span class="input-group-text">확보좌석</span>
                <input type="number" class="form-control" name="product_seat" value="${product.product_seat}">
            </div>
        </div>
        <div class="row">
            <div class="input-group mb-3 col">
                <span class="input-group-text">도착일</span>
                <input type="date" class="form-control" name="arrive" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${product.product_departure}" />">
            </div>
            <div class="input-group mb-3 col">
                <span class="input-group-text">출발일</span>
                <input type="date" class="form-control" name="departure" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${product.product_departure}" />">
            </div>
        </div>
        <div class="row">
            <div class="card p-3 border-0 h-100 d-flex flex-column justify-content-between ">
                <div class="input-group input-group-lg mb-3">
                    <span class="input-group-text">상품명</span>
                    <input class="form-control" type="text" name="product_name" value="${product.product_name}">
                </div>
                <div class="mb-3">
                    <label for="inputInfo" class="form-label">상품 설명</label>
                    <textarea name="detail_info" class="form-control mytextarea" id="inputInfo" rows="10">${product.detail_info}</textarea>
                </div>
                <div class="mb-3">
                    <label for="inputSchedule" class="form-label">상세 일정</label>
                    <textarea name="detail_schedule" class="form-control mytextarea" id="inputSchedule" rows="10">${product.detail_schedule}</textarea>
                </div>
            </div>
        </div>
        </div>
        <div class="mb-3 text-center p-3">
            <label for="inputFile" class="form-label">타이틀 이미지를 첨부하세요.</label>
            <input class="form-control form-control-sm mb-3" id="inputFile" type="file" name="titleImage" accept="image/*">
            <input type="hidden" id="originalTitleImage" name="originalTitleImage" value="${product.detail_title_image}" />
            <img id="displayImage" class="img-fluid mb-3" alt="" src="${contextPath}/attach${product.detail_title_image}">
            <sec:authorize access="hasAnyRole('ROLE_MASTER','ROLE_ADMIN')">
                <button id="updateBtn" class="btn btn-success regist">수정</button>
            </sec:authorize>
        </div>
    </form>
</section>
<script>
    const imgTag = document.querySelector("#displayImage");
    const fileTag = document.querySelector("#inputFile");
    let formData = new FormData();
    fileTag.addEventListener("change", function () {
        if (this.files && this.files[0]) {
            if (this.files[0].size > 10485760) {
                alert("10MB를 초과하는 사진은 업로드할 수 없습니다.");
                this.files = null;
                this.value = "";
                return
            }
            let reader = new FileReader();
            reader.onload = function (e) {
                imgTag.src = e.target.result;
            }
            reader.readAsDataURL(this.files[0]);
            formData.set("titleImage", this.files[0]);
        } else {
            imgTag.src = "";
        }
    });
</script>
<script src="https://cdn.tiny.cloud/1/mreuxwvmvo99s2c3asxl4t6ujhyqgni44dt6mle4qlfz9pq6/tinymce/5/tinymce.min.js" referrerpolicy="origin"></script>
<script>
    tinymce.init({
        selector: '.mytextarea',
        height: 500,
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
        file_picker_types: 'image',
        relative_urls: false,
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