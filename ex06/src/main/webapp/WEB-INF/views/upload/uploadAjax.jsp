<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	.uploadResult {
		width: 100%;
		background-color: gray;
	}
	
	.uploadResult ul {
		display: flex;
		flex-flow: row;
		justify-content: center;
		align-items: center;
	}
	
	.uploadResult ul li {
		list-style: none;
		padding: 10px;
	}
	
	.uploadResult ul li img {
		width: 100px;
	}

	.bigPictureWrapper {
	  position: absolute;
	  display: none;
	  justify-content: center;
	  align-items: center;
	  top:0%;
	  width:100%;
	  height:100%;
	  background-color: gray; 
	  z-index: 100;
	}
	
	.bigPicture {
	  position: relative;
	  display:flex;
	  justify-content: center;
	  align-items: center;
	}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>
	<h3>ajax를 이용한 업로드</h3>
    <div class="bigPictureWrapper">
        <div class="bigPicture"></div>
    </div>

    <div class="upploadDiv">
        <input type="file" name="uploadFile" multiple>
    </div>

	<!-- 서버로부터 받아온 파일목록정보를 출력하는 위치 -->
    <div class="uploadResult">
        <ul></ul>
    </div>
    <button id="uploadBtn">Upload</button>
<script>

	function checkExtension(fileName, fileSize) {
		// 파일 확장자제한, 파일크기 제한
		let regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");
		let maxSize = 5 * 1024 * 1024;  // 5mb

		if(fileSize >= maxSize) {
			alert("파일 크기용량 초과");
			return false;
		}

		if(regex.test(fileName)) {
			alert("해당 종류의 파일은 업로드 불가");
			return false;
		}

		return true;
	}

	function showImage(fileCalPath) {
		$(".bigPictureWrapper").css("display", "flex").show();
		$(".bigPicture").html("<img src='display?fileName=" + fileCalPath + "'>").animate({width: '100%', height: '100%'}, 1000);
	}



    $(document).ready(function() {
        
		// 파일전송 클릭버튼
		$("#uploadBtn").on("click", function() {
            let formData = new FormData(); // form태그에 해당하는 객체.

            // <input type="file" name="uploadFile" multiple> 를 복수로 참조.
            let inputFile = $("input[name='uploadFile']"); // 복수
			// inputFile[0] : 첫번째 태그
			// inputFile[0].files : 선택한 파일들
            let files = inputFile[0].files;

            console.log(files);

            for(let i=0; i<files.length; i++) {

				// files[i].name : 파일이름, files[i].size : 파일크기
				if(!checkExtension(files[i].name, files[i].size)) {
					return false;
				}


                // uploadFile 이름으로 파일전송을 하고 스프링에서 참조하게 됨.
            	formData.append("uploadFile", files[i]);
            }

            $.ajax({
                url: '/upload/uploadAjaxAction',
                processData: false, // 기본값 true.  false 의미? key:value값의 구조를 QueryString으로 변환.  주소?문자열
                contentType: false, // 기본값 true.  false 의미? "application/x-www-form-urlencoded;charset=UTF-8"  "multipart/form-data" 인코딩을 사용하여 전송.
                data: formData, // 스프링으로(서버)로 전송할 데이터
                type: 'post',   // 요청방식
                dataType: 'json', // 스프링에서 호출된 메서드의 리턴타입
                success: function(result) {
                    
					// 업로드 파일목록 정보
					// result[i]에 AttachFileDTO에 내용이 들어있다.
                	for(let i=0; i<result.length; i++) {
						console.log("날짜폴더명", result[i].uploadPath);
                		console.log("클라이언트에서 보낸 파일명" , result[i].fileName);
						console.log("중복되지 않는 파일명", result[i].uuid);
						console.log("이미지파일여부", result[i].image);
                	}
					

					showUploadedFile(result);
                }
            });
        });


		// 파일삭제 클릭.   X 클릭
		$(".uploadResult").on("click", "span", function() {
			console.log("삭제이벤트");

			// $(this).data("file") : 2024%5C05%5C20/s_53358eee-9fb3-4d36-a0d4-9332cb5b4662_2.jpg
			// 날짜와 파일명 분리작업.
			let targetFile = $(this).data("file").split('/'); // data-file
			let type = $(this).data("type"); // data-type

			// 파일명에서 날짜폴더명을 분리해야 한다.
			console.log("targetFile[0]", targetFile[0]);
			console.log("targetFile[1]", targetFile[1]);
			console.log("type", type);

			//return;

			$.ajax({
				url: 'deleteFile',
				data: {dateFolderName: targetFile[0], fileName : targetFile[1], type: type},  // 자바스크립트 Ojbect문법
				dataType: 'text',  // 스프링 메서드 리턴타입
				type: 'post',
				success: function(result) {
					if(result = "success") {
						alert("이미지가 삭제됨");
					}
				}
			});
		});

    });  // ready end

	//파일 업로드목록 정보가 출력될 위치를 참조.
	let uploadResult = $(".uploadResult ul");

	// 업로드한 파일정보를 리스트형태로 출력
	function showUploadedFile(uploadResultArr) {
		let str = "";


		// each : 참조하는 대상이 여러개의 정보를 가지고 있을 경우(for문같은거)
        // obj: AttachFileDTO클래스의 구조를 가짐
        // i : 0 ,1 ,2
        // data-XXX : 프로그래밍목적으로 태그에 값을 숨길때 사용.
		$(uploadResultArr).each(function(i, obj) {


			if(!obj.image) {  // 일반파일
				let fileCalPath = encodeURIComponent(obj.uploadPath) + "/" + obj.uuid;

				str += "<li><div><a href='/upload/download?fileName=" + fileCalPath + "'><img src='/img/attach.png'>" +
				obj.fileName + "</a><span style='cursor:pointer;' data-file=\'" + fileCalPath + "\' data-type='file'> X </span></div></li>";
			}else {			// 이미지파일
				
				// 자바스크립트 인코딩 url https://oper0116.tistory.com/17
				// let fileCalPath = obj.uploadPath + "/" + "s_" + obj.uuid;  // 에러발생 400.  역슬래시 문자를 서버에서 허용안함.
				let fileCalPath = encodeURIComponent(obj.uploadPath) + "/" + "s_" + obj.uuid;
				let originPath = obj.uploadPath + "\\" + obj.uuid;

				originPath = originPath.replace(new RegExp(/\\/g), "/");

				str += "<li><a href=\"javascript:showImage('" + originPath + "')\"><img src='display?fileName=" + fileCalPath + "'></a>" +
					"<span style='cursor:pointer;' data-file=\'" + fileCalPath + "\' data-type='image'> X </span></li>";
			}
		});

		// 파일정보 리스트형태의 작업한 태그내용을 자식레벨로 추가하는 작업
		uploadResult.append(str);
	}

</script>    
</body>
</html>