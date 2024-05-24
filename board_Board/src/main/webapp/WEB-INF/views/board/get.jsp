<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>    

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">

<!-- jQuery library -->
<!-- <script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.slim.min.js"></script> -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

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
                <h3 class="card-title">게시물 조회</h3>
                </div> 
                <!-- 목록으로 돌아오기 위한 정보 -->
                <!-- 수정,삭제,리스트에 사용하기 위해 만듬 -->
                <form id="actionForm" action="/board/list" method=get>
                	<input type="hidden" name="bno" value="${boardVO.bno }">
                	<input type="hidden" name="pageNum" value="${cri.pageNum }">
                	<input type="hidden" name="amount" value="${cri.amount}">
                	<input type="hidden" name="type" value="${cri.type}">
                	<input type="hidden" name="keyword" value="${cri.keyword}">
                </form>
                <form action="/board/get" method="post">
                <div class="card-body">
                <div class="form-group">
                <label for="bno">bno</label>
                <input type="text" class="form-control" id="bno" name="bno" readonly value="${boardVO.bno}">
                </div>
                </div>
                
                <div class="card-footer">
                <div class="form-group">
                <label for="title">title</label>
                <input type="text" class="form-control" id="title" name="title" readonly value="${boardVO.title}">
                </div>
                </div>

                <div class="card-footer">
                <div class="form-group">
                <label for="Content">Content</label>
                <textarea class="form-control" rows="3" id="content" name="content" readonly>${boardVO.content}</textarea>
                </div>
                </div>

                <div class="card-footer">
                <div class="form-group">
                <label for="writer">writer</label>
                <input type="text" class="form-control" id="writer" name="writer" readonly value="${boardVO.writer }">
                </div>
                </div>
                
                <div class="card-footer">
                <div class="form-group">
                <label for="Regdate">Regdate</label>
                <input type="text" class="form-control" id="Regdate" name="Regdate" readonly value="<fmt:formatDate value="${boardVO.regdate}" pattern="yyyy-MM-dd" />">
                </div>
                </div>
                
                <div class="card-footer">
                <div class="form-group">
                <label for="Updatedate">Updatedate</label>
                <input type="text" class="form-control" id="Updatedate" name="Updatedate" readonly value="<fmt:formatDate value="${boardVO.updatedate}" pattern="yyyy-MM-dd" />">
                </div>
                </div>
                
                <div class="card-footer">
                <div class="form-group">
                <label for="Viewcount">Viewcount</label>
                <input type="text" class="form-control" id="viewcount" name="viewcount" readonly value="${boardVO.viewcount }">
                </div>
                </div>
                
                <div class="card-footer">
                <button type="button" class="btn btn-primary" onclick="fn_modify()">Modify</button>
                <button type="button" class="btn btn-danger" onclick="fn_delete()">Delete</button>
                <button type="button" class="btn btn-primary" onclick="fn_list()">List</button>
                </div>
             </form>
          </div>
        </div>
    </div>
</div>
<script>
let actionForm = document.getElementById("actionForm");  // 위에 form태그 참조

    function fn_modify() {
        actionForm.action = "/board/modify";
        actionForm.submit();
    }

	function fn_delete(bno) {    // confirm : 확인창 기능 구현
        if(!confirm(${boardVO.bno} + "번 게시물을 삭제하시겠습니까?")) return;

        // location.href="/board/delete?bno=" + bno;

        // <form id="actionForm" action="/board/list" method="get">

        actionForm.setAttribute("action", "/board/delete");
        actionForm.submit();
    }

    function fn_list() {
        actionForm.setAttribute("action" ,"/board/list");
        actionForm.submit();
    }
</script>
</body>
</html>