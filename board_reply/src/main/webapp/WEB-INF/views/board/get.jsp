<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>    

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
    table, td, th {
        border: 1px solid;
    }
    
    table {
        width: 70%;
        border-collapse: collapse;
    }
</style>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">

<!-- jQuery library -->
<!-- <script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.slim.min.js"></script> -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

<!-- Popper JS -->
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>

<!-- Latest compiled JavaScript -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>

<!-- 여기부터 -->
<!-- 1) Include Handlebars from a CDN -->
<script src="https://cdn.jsdelivr.net/npm/handlebars@latest/dist/handlebars.js"></script>

<!--2) Handlebar Template-->
<script id="reply-template" type="text/x-handlebars-template">
	
	<table id="replytable">
        <!-- {{#each .}} {{/each}} 반복시킬때 사용 -->
        {{#each .}}
        <tr><!--서버에서 전달된 각 댓글 객체의 rno, replyer, replydate 값을 HTML에 삽입합니다.--> <!--Handlebars 헬퍼 함수로, replydate를 원하는 형식으로 변환하여 출력합니다-->
            <td>[<span id="rno_{{rno}}">{{rno}}</span>] <span id="replyer_{{rno}}">{{replyer}}</span> {{convertDate replydate}}</td>
        </tr>
        <tr><!--{{retext}}는 댓글 객체의 retext 속성 값을 HTML에 삽입합니다.-->
            <td><span id="retext_{{rno}}">{{retext}}</span></td>
        </tr>
        <tr>                      
            <td><!--버튼에 data-rno 속성을 추가합니다. 이 속성은 댓글의 고유 번호(rno)를 저장합니다. 이는 자바스크립트로 특정 댓글을 수정하거나 삭제할 때 사용됩니다.-->
            <button type="button" name="btnReplyModify" data-rno="{{rno}}" class="btn btn-primary btn-xs">수정</button>
            <button type="button" name="btnReplyDelete" data-rno="{{rno}}" class="btn btn-danger btn-xs">삭제</button>
            </td>
        </tr>
    {{/each}}
</table>
</script>
    
    <script>
        // Handlebar Template에서 사용할 사용자 정의함수 작업
        // Handlebars.registerHelper: Handlebars 템플릿 엔진에 헬퍼 함수를 등록합니다.
        // 첫 번째 매개변수는 헬퍼 함수의 이름인 "convertDate"입니다.
        // 두 번째 매개변수는 헬퍼 함수 자체로, 이 경우 replydate를 받아 특정 형식으로 변환합니다.
        Handlebars.registerHelper("convertDate", function(replydate) {
            
            // new Date(replydate): replydate 문자열을 Date 객체로 변환합니다.
            const date = new Date(replydate);

            // 월을 0부터 11까지 반환하므로, 사람이 인식하는 월(1~12)을 맞추기 위해 1을 더합니다.
            let month = (date.getMonth()+1 < 10 ? "0" + (date.getMonth()+1) : date.getMonth()+1);
            
            // 날짜(1~31)를 반환합니다.  ? : 월과 일이 한 자리 수일 경우 앞에 0을 추가하여 두 자리 수로 만듭니다.
            let day = (date.getDate() < 10 ? "0" + (date.getDate()) : date.getDate());
            
            // 변환된 연도, 월, 일 값을 조합하여 "YYYY/MM/DD" 형식의 문자열로 반환합니다.
            return date.getFullYear() + "/" + month + "/" + day;
        });
    </script>
<!--여기까지 댓글기능-->
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
                <label for="content">Content</label>
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
                <label for="viewcount">Viewcount</label>
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

    <!-- 여기부터 -->
    <div class="row">
        <div class="col">
            <!-- Button trigger modal -->
            <button type="button" class="btn btn-primary btn-xs" data-toggle="modal" id="btnReplyWrite">
                댓글쓰기
            </button>
            <!-- 댓글목록 위치 -->
            <div id="replyList"></div>
            <!-- 댓글페이징 위치 -->
            <div id="replypager"></div>
        </div>
    </div>
</div>
    <!-- 여기까지 댓글기능 -->
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

// 여기부터
    // JQuery 작업을 하기위한 기본.
    // ready()이벤트 메서드. 브라우저가 모든 웹페이지의 내용을 읽고 시작되는 이벤트
    $(document).ready(function() {

        // 댓글페이지번호 클릭이벤트
        // 아래 선택자에 참조되는 태그가 동적으로 생성된 경우에는 이벤트 설정불가(엄청중요)
        /*
        $("동적태그 선택자 -> nave ul li").on("이벤트명 -> click", function() {

        });
        */

        // 동적태그를 이벤트 설정을 하는 경우
        /*
        $("정적태그 선택자").on("이벤트명", "동적태그 선택자", function(){

        });
        */
        $("div#replypager").on("click", "li a", function(e) {
            e.preventDefault(); // a태그의 href속성의 링크기능 없애기.

            // 클릭한 a태그를 참조
            replyPage = $(this).attr("href");  // a태그를 클릭했을 때 href에 주소를 참조하여 replyPage에 집어넣음

            // console.log("페이지: " ,replyPage);

            url = "/replies/pages" + bno + "/" + replyPage;
            getPage(url);
        });

        // 댓글쓰기 대화상자 버튼클릭.   document.getElementById("btnReplyWrite") 이 기능과 유사
        // $("댓글쓰기 버튼태그 참조하는 선택자")
        // function() : 익명함수(이름이 없는 함수)
        $("#btnReplyWrite").on("click" , function() {
            // console.log("댓글버튼 클릭"); // 코드작업이 잘 동작하는지 확인용

            // 댓글번호,댓글 작성자, 내용 초기화
            // 댓글쓰기 버튼 클릭시 있던 내용 초기화
            $("#reply_rno").html("");   // 일반 태그는 html로 사용
            $("#replyer").val("");     // input 같은 태그에 id는 val로 사용
            $("#retext").val("");

            // 모달버튼 화면 보임/숨김 작업.  <button name="btnModalReply"></button>
            // [] : 속성
            $("button[name='btnModalReply']").hide(); // 등록, 수정, 삭제 3개의 버튼 숨심
            $("#btnModalReplySave").show();    // 다시 등록만 화면에 보이게함

            $("replyDialog").modal('show'); // replyDialog(모달) 화면에 보이게함

        });
































    });    


    // 게시글 글번호
    let bno = ${boardVO.bno};  // 게시물번호 511
    let replyPage = 1;   // 댓글페이지중 1번째 페이지

    // 댓글목록과댓글페이지정보를 요청하는 메핑주소
    let url = "/replies/pages/" + bno + "/" + replyPage; // /replies/pages/511/1

    // console.log("url",url);

    getPage(url);

    // 댓글목록 함수
    function getPage(url) {

        // ajax기능 지원
        $.getJSON(url, function(data) {
            // data.list  data.pageMaker

            // console.log("list", data.list);
            // console.log("pageMaker", data.pageMaker);

            /*   수동으로 추가하는 방법
            let result = "";

            // 1) 뎃글데이터 화면에 출력
            for(let i=0; i<data.list.length; i++) {
                result += "댓글번호: " + data.list[i].rno + "<br>";
                result += "대글내용: " + data.list[i].retext + "<br>";
            }
            // 제이쿼리식 Css 선택자. id를 참조한다는 뜻
            $("#replyList").html(result);
            */
            
            /*
            data.list: 서버에서 받아온 댓글 데이터의 목록입니다. 이 목록은 서버에서 전달된 JSON 또는 XML 형식의 데이터입니다.
            $("#replyList"): 댓글을 표시할 HTML 요소입니다. 이 요소는 jQuery를 사용하여 선택됩니다.
            $("#reply-template"): Handlebars 템플릿입니다. 이 템플릿은 댓글 데이터를 어떻게 표시할지에 대한 HTML 구조와 동적 데이터를 결합한 것입니다.
            */
            displayReplyData(data.list, $("#replyList"),$("#reply-template"));
            // data.list -> replyData 
            // $("#replyList") -> target 
            // $("#reply-template") -> template

            /*
            data.pageMaker: 서버에서 받아온 페이징입니다. 현재 페이지 번호, 총 페이지 수, 이전 페이지 및 다음 페이지의 유무 등.
            $("#replypager") : 페이징을 표시할 HTML 요소입니다.
            */
            displayReplyPaging(data.pageMaker, $("#replypager"));
            // data.pageMaker -> pageData      pageMaker에 들어있는 정보가 pageData에 호출함
            // $("#replypager") -> target
        });
    }

        // 댓글목록 데이터 바인딩
        // replyData : 댓글목록데이터
        // target : 댓글목록이 출력될 태그위치
        // template : 댓글목록 UI 핸들바 템플릿
        // Handlebars.compile(template.html()); Handleber 라이브러리에 들어있음
        function displayReplyData(replyData, target, template) {

            /*
            Handlebars.compile(template.html())은 template.html()에 해당하는 문자열을 컴파일하여
            실행 가능한 JavaScript 함수로 반환하고, 이를 templateObj 변수에 할당합니다.
            이렇게 컴파일된 템플릿 함수는 나중에 데이터를 주입하여 HTML 문자열을 생성하는 데 사용됩니다.
            */
            let templateObj = Handlebars.compile(template.html());

            // 댓글데이터와 테이블태그(reply-template)가 결합한 데이터가 replyHtml에 들어옴
            let replyHtml = templateObj(replyData);
            
            // console.log("댓글목록: " + replyHtml);

            target.empty(); // target변수가 참조한 위치에 내용을 지운다.
            target.append(replyHtml); // target변수가 참조하는 태그위치에 자식레벨로 replyHtml변수의 내용을 추가한다.
        }

        // 댓글페이징 작업
        // pageData: 페이징에 필요한 데이터
        // target : 페이징기능이 삽입될 위치
        function displayReplyPaging(pageData, target) {

            /*
        <nav aria-label="Page navigation example">
            <ul class="pagination">
                <li class="page-item"><a class="page-link" href="#">Previous</a></li>
                
                <li class="page-item"><a class="page-link" href="#">1</a></li>
                <li class="page-item"><a class="page-link" href="#">2</a></li>
                <li class="page-item"><a class="page-link" href="#">3</a></li>
                
                <li class="page-item"><a class="page-link" href="#">Next</a></li>
            </ul>
        </nav>
            */
        

        let pageStr = '<nav aria-label="Page navigation example">';
            pageStr += '<ul class="pagination">';

        // 이전표시여부 작업
        if(pageData.prev) {
            pageStr += '<li class="page-item">';
            pageStr += '<a class="page-link" href="' + (pageData.startPage - 1) + '">Previous</a></li>';
        }

        // 페이지번호 작업
        for(let i=pageData.startPage; i <= pageData.endPage; i++) {
            let curPageClass = (pageData.cri.pageNum == i) ? 'active' : '';
            pageStr += '<li class="page-item ' + curPageClass + '">';
            pageStr += '<a class="page-link" href="' + i + '">' + i + '</a></li>';
        }

        // 다음표시여부 작업
        // 이전표시여부 작업
        if(pageData.next) {
            pageStr += '<li class="page-item">';
            pageStr += '<a class="page-link" href="' + (pageData.endPage + 1) + '">Previous</a></li>';
        }

        pageStr += '</ul></nav>';

        // target변수가 참조하는 태그내용에 pageStr변수의 값을 대입
        target.html(pageStr);
    }
</script>

<!-- Modal -->
<div class="modal fade" id="replyDialog" data-backdrop="static" data-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
        <div class="modal-header">
            <h5 class="modal-title" id="staticBackdropLabel">댓글 <span id="reply_rno"></span></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
        </div>
        <div class="modal-body">
            <div class="form-group">
                <label for="replyer">replyer</label>
                <input type="text" class="form-control" id="replyer" placeholder="Enter writer...">
            </div>
            <div class="form-group">
                <label for="reptext">reptext</label>
                <textarea class="form-control" id="reptext" rows="3"></textarea>
            </div>
        </div>
        <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            <button type="button" class="btn btn-primary" name="btnModalReply" id="btnModalReplySave">등록</button>
            <button type="button" class="btn btn-primary" name="btnModalReply" id="btnModalReplyModify">수정</button>
            <button type="button" class="btn btn-primary" name="btnModalReply" id="btnModalReplyDelete">삭제</button>
        </div>
        </div>
    </div>
</div>
</body>
</html>