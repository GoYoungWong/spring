<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h3>상품정보</h3>
<!-- product.name 필드처럼 참조하지만, 내부적으로는 getName()메서드가 호출된다. -->
<p>상품이름? <b>${product.name}</b> </p>
<p>상품가격? <b>${product.price }</b> </p>
</body>
</html>