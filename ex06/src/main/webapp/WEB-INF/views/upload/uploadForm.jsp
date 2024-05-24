<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h3>form태그를 이용한 파일 업로드</h3>
<!-- enctype:서버로 제출될 때 해당 데이터가 인코딩되는 방법을 명시-->
<!-- form태그의 enctype="application/x-www-form-urlencoded" 기본값. 생략함-->  
<!-- 파일첨부할때는 enctype="multipart/form-data" 이거로 한다.-->                 
<form method="post" action="uploadFormAction" enctype="multipart/form-data">
    <input type="file" name="uploadFile" multiple> <!--multiple 파일을 다중선택이 가능하다는 뜻 -->
    <button type="submit">전송</button>
</form>
</body>
</html>