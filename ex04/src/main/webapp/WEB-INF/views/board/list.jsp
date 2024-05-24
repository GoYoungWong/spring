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
            <div class="card">
                <div class="card-header">
                    <h3 class="card-title">Board List</h3>
                </div>
                <div class="card-body">
                <table class="table table-bordered">
                <thead>
                    <tr>
                    <th style="width: 10%">bno</th>
                    <th style="width: 30%">title</th>
                    <th style="width: 25%">write</th>
                    <th style="width: 25%">regdate</th>
                    <th style="width: 10%">viewcount</th>
                </tr>
                </thead>
                    <tbody>
                    <c:forEach items="${list}" var="boardVO">
                            <tr>
                                <td>${boardVO.bno}</td>     <!-- PRMIARY KEY -->
                                <td><a href="/board/get?bno=${boardVO.bno}&type=${pageMaker.cri.type}&keyword=${pageMaker.cri.keyword}&pageNum=${pageMaker.cri.pageNum}&amount=${pageMaker.cri.amount}">${boardVO.title }</a></td>
                                <td>${boardVO.writer }</td>
                                <td><fmt:formatDate value="${boardVO.regdate }" pattern="yyyy-MM-dd"/></td>
                                <td>${boardVO.viewcount}</td>
                            </tr>
                    </c:forEach>        
                        </tbody>
                    </table>
                </div>
                
                <!-- 
                			1 	2	3	4	5	6	7	8	9	10	[다음]
                	[이전]	11	12	13	14	15	16	17	18	19	20	[다음]
                 -->
                 <!-- EL문법의 목적은 작성된 코드에 결과를 출력하기위함 -->
                
                <div class="card-footer clearfix">
                    <!-- 검색기능 시작 -->
                    <div class="float-left">
                        <form id="searchform" action="/board/list" method="get">
                            <div class="form-group">
                                <select name="type">
                                    <option value="" <c:out value="${pageMaker.cri.type == null ? 'selected' : '' }" />>-----------</option>
                                    <option value="T" <c:out value="${pageMaker.cri.type == 'T' ? 'selected' : '' }" />>제목</option>
                                    <option value="C" <c:out value="${pageMaker.cri.type == 'C' ? 'selected' : '' }" />>내용</option>
                                    <option value="W" <c:out value="${pageMaker.cri.type == 'W' ? 'selected' : '' }" />>작성자</option>
                                    <option value="TC" <c:out value="${pageMaker.cri.type == 'TC' ? 'selected' : '' }" />>제목 or 내용</option>
                                    <option value="TW" <c:out value="${pageMaker.cri.type == 'TW' ? 'selected' : '' }" />>제목 or 작성자</option>
                                    <option value="TWC" <c:out value="${pageMaker.cri.type == 'TWC' ? 'selected' : '' }" />>제목 or 작성자 or 내용</option>
                                </select>
                                <input type="text" name="keyword" placeholder="검색어를 입력하세요" value="${pageMaker.cri.keyword}">
                                <input type="hidden" name="pageNum" value="1">
                                <input type="hidden" name="amount" value="${pageMaker.cri.amount}">
                                <input type="submit" class="btn btn-primary btn-sm" value="Search">
                            </div>
                        </form>
                    </div>
                    <!-- 검색기능 끝 -->
                	
                	<ul class="pagination pagination-sm m-0 float-right">
                		<c:if test="${pageMaker.prev }">
                			<li class="page-item"><a class="page-link" href="/board/list?type=${pageMaker.cri.type}&keyword=${pageMaker.cri.keyword}&pageNum=${pageMaker.startPage - 1 }&amount=${pageMaker.cri.amount}">이전</a></li>
                		</c:if>
                							
		                <c:forEach begin="${pageMaker.startPage }" end="${pageMaker.endPage }" var="page">
		                <li class='page-item ${pageMaker.cri.pageNum == page ? "active" : ""}'>
		                <!-- /board/list 메핑주소 요청시 스프링에서는 메서드의 파라미터가 Criteria cri 로 되어있기 때문에  
		                	 클라이언트에서 pageNum,amount 두 파라미터로 정보를 보내야 한다.
		                -->
		                <a class="page-link" href="/board/list?type=${pageMaker.cri.type}&keyword=${pageMaker.cri.keyword}&pageNum=${page }&amount=${pageMaker.cri.amount}">${page }</a>
		                </li>								
		              	</c:forEach>		
		                
		                <c:if test="${pageMaker.next }">
                			<li class="page-item"><a class="page-link" href="/board/list?type=${pageMaker.cri.type}&keyword=${pageMaker.cri.keyword}&pageNum=${pageMaker.endPage + 1 }&amount=${pageMaker.cri.amount}">다음</a></li>
                		</c:if>
                	</ul>
                </div>
             </div>
        </div>
    </div>
</div>
</body>
</html>