<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">

<!-- jQuery library -->
<script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.slim.min.js"></script>

<!-- Popper JS -->
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>

<!-- Latest compiled JavaScript -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <div class="col-12">
            <div class="card card-primary">
                <div class="card-header">
                <h3 class="card-title">글수정 폼</h3>
                    </div>
                    <!-- form태그를 따로 만들지 말고 안에 넣어서 사용 -->
                    <form id="actionForm" action="/board/modify" method="post">
                    <div class="card-body">
                    <div class="form-group">
                        <label for="title">Bno</label>
                        <input type="text" class="form-control" id="bno" name="bno" value="${boardVO.bno }" readonly>
                        <input type="hidden" name="pageNum" value="${cri.pageNum}">
						<input type="hidden" name="amount" value="${cri.amount}">
						<input type="hidden" name="type" value="${cri.type}">
						<input type="hidden" name="keyword" value="${cri.keyword}"> 
                    </div>
                    </div>
                    <div class="card-footer">
                    <div class="form-group">
                        <label for="title">title</label>
                        <input type="text" class="form-control" id="title" name="title" placeholder="Enter title" value="${boardVO.title }">
                    </div>
                    </div>
                    <div class="card-footer">
                    <div class="form-group">
                        <label for="Content">Content</label>
                        <textarea class="form-control" rows="3" id="content" name="content" placeholder="Enter content">${boardVO.content }</textarea>
                    </div>
                    </div>
                    <div class="card-footer">
                    <div class="form-group">
                        <label for="writer">writer</label>
                        <input type="text" class="form-control" id="writer" name="writer" placeholder="Enter writer"value="${boardVO.writer }">
                    </div>
                    </div>
                    
                    <div class="card-footer">
                    <button type="submit" class="btn btn-primary">submit</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

</div>
</body>
</html>