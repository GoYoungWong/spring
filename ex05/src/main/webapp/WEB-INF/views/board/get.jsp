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
        <tr><!-- 태그에 접근하기 위해 작성-->
            <td>[<span id="rno_{{rno}}">{{rno}}</span>] <span id="replyer_{{rno}}">{{replyer}}</span> {{convertDate replydate}}</td>
        </tr>
        <tr>
            <td><span id="retext_{{rno}}">{{retext}}</span></td>
        </tr>
        <tr>
            <td>                                <!--data-rno="{{rno}}": 데이터 속성-->
                <button type="button" name="btnReplyModify" data-rno="{{rno}}" class="btn btn-primary btn-xs">수정</button>
                <button type="button" name="btnReplyDelete" data-rno="{{rno}}" class="btn btn-danger btn-xs">삭제</button>
            </td>
        </tr>
        {{/each}}
    </table>
  </script>
  
    <script>
        // Handlebar Template에서 사용할 사용자 정의함수 작업
        Handlebars.registerHelper("convertDate", function(replydate){
            const date = new Date(replydate);

            let month = (date.getMonth()+1 < 10 ? "0" + (date.getMonth()+1) : date.getMonth()+1);
            let day = (date.getDate() < 10 ? "0" + (date.getDate()) : date.getDate());
            return date.getFullYear() + "/" + month  + "/" + day;
        });
    </script>
  <!-- 여기까지 댓글기능 -->
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
                <form id="actionForm" action="/board/list" method="get">
                	<input type="hidden" name="bno" value="${boardVO.bno}">
					<input type="hidden" name="pageNum" value="${cri.pageNum}">
					<input type="hidden" name="amount" value="${cri.amount}">
					<input type="hidden" name="type" value="${cri.type}">
					<input type="hidden" name="keyword" value="${cri.keyword}">                
                </form>
                <form action="/board/get" method="post">
                        <div class="card-footer">
                            <div class="form-group">
                            <label for="title">bno</label>
                            <input type="text" class="form-control" id="bno" name="bno" readonly value="${boardVO.bno }">
                        </div>
                        </div>
                        
                    <div class="card-footer">
                    <div class="form-group">
                        <label for="title">title</label>
                        <input type="text" class="form-control" id="title" name="title" readonly value="${boardVO.title }">
                    </div>
                    </div>

                    <div class="card-footer">
                    <div class="form-group">
                        <label for="content">Content</label>
                        <textarea class="form-control" rows="3" id="content" name="content" readonly >${boardVO.content }</textarea>
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
                        <input type="text" class="form-control" id="Regdate" name="Regdate" readonly value="<fmt:formatDate value="${boardVO.regdate }" pattern="yyyy-MM-dd" />">
                    </div>
                    </div>
                    
                    <div class="card-footer">
                    <div class="form-group">
                        <label for="Updatedate">Updatedate</label>
                        <input type="text" class="form-control" id="updatedate" name="updatedate" readonly value="<fmt:formatDate value="${boardVO.updatedate }" pattern="yyyy-MM-dd" />">
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
    
    <!-- 여기부터 -->
    <div class="row">
        <div class="col">
            <!-- Button trigger modal -->
            <button type="button" class="btn btn-primary btn-xs" data-toggle="modal" id="btnReplyWrite">
                댓글쓰기
            </button>
            <!-- 댓글목록 위치 -->
            <div id="replyList"></div>
            <!-- 댓글페이지 위치 -->
            <div id="replypager"></div>
        </div>
    </div>
</div>
<!-- 여기까지 댓글기능 -->

<script>
let actionForm = document.getElementById("actionForm");   // form태그 참조

	function fn_modify() {
		actionForm.action = "/board/modify";
		actionForm.submit();
}
    
    function fn_delete(bno) {     // confirm : 확인창 기능 구현
        if(!confirm(bno + "번 게시물을 삭제하시겠습니까?")) return;

        // location.href="/board/delete?bno=" + bno;

        // <form id="actionForm" action="/board/list" method="get">
        actionForm.setAttribute("action", "/board/delete");
        actionForm.submit();

    }

    function fn_list() {
    	actionForm.setAttribute("action", "/board/list");
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
       // 동적태그를 이벤트 설정을 하는 경우
        $("div#replypager").on("click", "li a", function(e) {
            e.preventDefault(); // a태그의 href속성의 링크기능 없애기.
            // 클릭한 a태그를 참조
            replyPage = $(this).attr("href");  // a태그를 클릭했을 때 href에 주소를 참조하여 replyPage에 집어넣음

            // console.log("페이지: " , replyPage);

            url = "/replies/pages/" + bno + "/" + replyPage;
            getPage(url);
        });

        // 댓글쓰기 대화상자 버튼클릭.   document.getElementById("btnReplyWrite") 이 기능과 유사
        // $("댓글쓰기 버튼태그 참조하는 선택자")
        // function() : 익명함수(이름이 없는 함수)
        $("#btnReplyWrite").on("click", function() {
            // console.log("댓글버튼클릭"); // 코드작업이 잘 동작하는지 확인용

            // 댓글번호,댓글 작성자, 내용 초기화
            $("#reply_rno").html("");
            $("#replyer").val("");
            $("#retext").val("");

            // 모달버튼 화면 보임/숨김 작업.  <button name="btnModalReply"></button>
            // [] : 속성
            $("button[name='btnModalReply']").hide();  // 등록,수정,삭제 3개버튼 화면에서 숨김
            $("#btnModalReplySave").show();  // 다시 등록버튼만 화면에 보여짐.


            $("#replyDialog").modal('show'); // replyDialog을 화면에 보이게 함
        });

        // 1)모달(Modal)대화상자 댓글등록
        $("#btnModalReplySave").on("click",function(){
            // console.log("댓글등록버튼"); // 코드작업이 잘 동작하는지 확인용

            // $("#replyer").val(); : <input type="text" id="replyer"> 태그의 value값
            let replyer = $("#replyer").val();
            let retext = $("#retext").val();

            // 1)댓글등록데이터를 자바스크립트 Object문법으로 표현
            // 자바스크립트 오브젝트 문법
            let replyData = {bno: ${boardVO.bno }, replyer : replyer, retext : retext};

            // 2)댓글데이터를 JSON변환하여 서버에 전송. 
            // JSON변환: 서로다른 데이터포맷을 공통된 포맷으로 보낼떄 사용되는 표현법
            // console.log(JSON.stringify(replyData));

            // return;

            // ajax문법 
            // 이걸 만들기전에 스프링에서 먼저 이거 관련 코딩을 먼저 해야한다.
            $.ajax({
                type: 'post',  // post형식으로 보낸다는 뜻
                url : '/replies/new',   // 댓글저장 메핑주소
                // ajax문법을 사용할때 headers는 JSON이라는것을 명시해줘야 data: JSON.stringify(replyData)로 JSON으로 전송할수 있다.
                headers : {
                    "Content-type" : "application/json", "X-HTTP-Method-Override" : "POST" // 보낼때 JSON 방식으로 보내진다고 명시해 주는 것
                },
                dataType: "text",  // 스프링 주소의 메서드 리턴타입(리턴타입이 String이기 떄문에 text로 적은 것)
                data: JSON.stringify(replyData), // 서버로 전송할 JSON 데이터
                success: function(data) {   // ajax 통신이 성공했을 때 실행되는 콜백함수
                    if(data == "success") {
                        alert("댓글 등록됨");
                        let url = "/replies/pages/" + bno + "/" + replyPage;
                        getPage(url);

                        // 댓글 작성자, 내용 초기화(괄호안에 매개변수가 있으면 그 값으로 채움 없으면 내용을 읽어옴)
                        $("#replyer").val("");
                        $("#retext").val("");

                        // modal dialog 화면에서 사라짐
                        $("#replyDialog").modal('hide');
                    }
                }
            });
        });

        // 2)모달(Modal)대화상자 댓글수정
        $("#btnModalReplyUpdate").on("click",function(){

            // $("#replyer").val(); : <input type="text" id="replyer"> 태그의 value값
            let rno = $("#reply_rno").html();
            let replyer = $("#replyer").val();
            let retext = $("#retext").val();

            // 1)댓글수정데이터를 자바스크립트 Object문법으로 표현
            let replyData = {rno: $("#reply_rno").html(), replyer : replyer, retext : retext};

            // 2)댓글데이터를 JSON변환하여 서버에 전송. 
            // JSON변환: 서로다른 데이터포맷을 공통된 포맷으로 보낼떄 사용되는 표현법
            // console.log(JSON.stringify(replyData));

            // return;

            // ajax문법 
            // 이걸 만들기전에 스프링에서 먼저 이거 관련 코딩을 먼저 해야한다.
            $.ajax({
                type: 'put',  // 댓글 수정작업은 rest api에서는 put,patch 요청방식 사용.
                url : '/replies/modify',   // 댓글수정 메핑주소
                // ajax문법을 사용할때 headers는 JSON이라는것을 명시해줘야 data: JSON.stringify(replyData)로 JSON으로 전송할수 있다.
                headers : {
                    "Content-type" : "application/json", "X-HTTP-Method-Override" : "PUT" // 보낼때 JSON 방식으로 보내진다고 명시해 주는 것
                },
                dataType: "text",  // 스프링 주소의 메서드 리턴타입(리턴타입이 String이기 떄문에 text로 적은 것)
                data: JSON.stringify(replyData), // 서버로 전송할 JSON 데이터
                success: function(data) {   // ajax 통신이 성공했을 때 실행되는 콜백함수
                    if(data == "success") {
                        alert("댓글 수정됨");
                        let url = "/replies/pages/" + bno + "/" + replyPage;
                        getPage(url);

                        // 댓글번호 작성자, 내용 초기화(괄호안에 매개변수가 있으면 그 값으로 채움 없으면 내용을 읽어옴)
                        $("#reply_rno").html("");  // "" 공백
                        $("#replyer").val("");   // "" 공백
                        $("#retext").val("");    // "" 공백

                        // modal dialog 화면에서 사라짐
                        $("#replyDialog").modal('hide');
                    }
                }
            });
        });

            // 3)모달(Modal)대화상자 댓글삭제
            $("#btnModalReplyDelete").on("click",function(){

                // 1)댓글삭제데이터를 자바스크립트 Object문법으로 표현
                // let replyData = {rno: $("#reply_rno").html()};

                // 2)댓글데이터를 JSON변환하여 서버에 전송. 
                // JSON변환: 서로다른 데이터포맷을 공통된 포맷으로 보낼떄 사용되는 표현법
                // console.log(JSON.stringify(replyData));

                // return;

                // ajax문법 
                // 이걸 만들기전에 스프링에서 먼저 이거 관련 코딩을 먼저 해야한다.
                $.ajax({
                    type: 'delete',  // 댓글 삭제작업은 rest api에서는 delete 요청방식 사용.
                    url : '/replies/delete/' + $("#reply_rno").html(),   // 댓글수정 메핑주소
                    // ajax문법을 사용할때 headers는 JSON이라는것을 명시해줘야 data: JSON.stringify(replyData)로 JSON으로 전송할수 있다.
                    headers : {
                        "Content-type" : "application/json", "X-HTTP-Method-Override" : "DELETE" // 보낼때 JSON 방식으로 보내진다고 명시해 주는 것
                    },
                    dataType: "text",  // 스프링 주소의 메서드 리턴타입(리턴타입이 String이기 떄문에 text로 적은 것)
                    // data: JSON.stringify(replyData), // 서버로 전송할 JSON 데이터
                    success: function(data) {   // ajax 통신이 성공했을 때 실행되는 콜백함수
                        if(data == "success") {
                            alert("댓글 삭제됨");
                            let url = "/replies/pages/" + bno + "/" + replyPage;
                            getPage(url);

                            // 댓글번호 작성자, 내용 초기화(괄호안에 매개변수가 있으면 그 값으로 채움 없으면 내용을 읽어옴)
                            $("#reply_rno").html("");  // "" 공백
                            $("#replyer").val("");   // "" 공백
                            $("#retext").val("");    // "" 공백

                            // modal dialog 화면에서 사라짐
                            $("#replyDialog").modal('hide');
                        }
                    }
                });
            });

        // 댓글목록에서 수정버튼을 클릭시
        $("div#replyList").on("click", "button[name='btnReplyModify']", function() {
            // console.log("수정버튼을 클릭");
            // $(this) : 클릭한 수정버튼 태그를 참조
            let rno = $(this).data("rno");   // <button data-rno="500">수정</button>
            let replyer = $(this).parents("table#replytable").find("#replyer_" + rno).html();
            let retext = $(this).parents("table#replytable").find("#retext_" + rno).html();  // .html(); 태그와 태그사이에 있는 내용을 읽어올떄 사용

            // console.log("rno", rno);
            // console.log("replyer" ,replyer);
            // console.log("retext",retext);

            // 모달버튼 화면 보임/숨김 작업.  <button name="btnModalReply"></button>
            // [] : 속성
            $("button[name='btnModalReply']").hide();  // 등록,수정,삭제 3개버튼 화면에서 숨김
            $("#btnModalReplyUpdate").show();  // 다시 수정버튼만 화면에 보여짐.


            // Modal 대화상자에 값을 출력하는 작업.
            $("#reply_rno").html(rno); // 일반태그 <span>은 html()메서드 사용

            // <input>태그는 val()메서드 사용    
            $("#replyer").val(replyer);
            $("#retext").val(retext);
            
            // 화면에서 보임
            $("#replyDialog").modal('show');
        }); 

        // 댓글목록에서 삭제버튼을 클릭시
        $("div#replyList").on("click", "button[name='btnReplyDelete']", function() {
            // console.log("수정버튼을 클릭");
            // $(this) : 클릭한 수정버튼 태그를 참조
            let rno = $(this).data("rno");   // <button data-rno="500"삭제</button>
            let replyer = $(this).parents("table#replytable").find("#replyer_" + rno).html();
            let retext = $(this).parents("table#replytable").find("#retext_" + rno).html();  // .html(); 태그와 태그사이에 있는 내용을 읽어올떄 사용

            // console.log("rno", rno);
            // console.log("replyer" ,replyer);
            // console.log("retext",retext);

            // 모달버튼 화면 보임/숨김 작업.  <button name="btnModalReply"></button>
            // [] : 속성
            $("button[name='btnModalReply']").hide();  // 등록,수정,삭제 3개버튼 화면에서 숨김
            $("#btnModalReplyDelete").show();  // 다시 수정버튼만 화면에 보여짐.


            // Modal 대화상자에 값을 출력하는 작업.
            $("#reply_rno").html(rno); // 일반태그 <span>은 html()메서드 사용

            // <input>태그는 val()메서드 사용    
            $("#replyer").val(replyer);
            $("#retext").val(retext);
            
            // 화면에서 보임
            $("#replyDialog").modal('show');
        }); 



    });

    // 게시물 글번호
    let bno = ${boardVO.bno};  // 게시물번호 511
    let replyPage = 1;    // 댓글목록중 1번째 페이지

    // 댓글목록과댓글페이지정보를 요청하는 매핑주소
    let url = "/replies/pages/" + bno + "/" + replyPage; // /replies/pages/511/1

    // console.log("url", url); 주소확인용

    getPage(url);

    // 댓글목록 함수
    function getPage(url) {
        
        // ajax기능 지원.
        $.getJSON(url, function(data) {
            // data.list  data.pageMaker

            // console.log("list", data.list);  확인용 코딩
            // console.log("pageMaker", data.pageMaker);  확인용 코딩
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
            displayReplyData(data.list, $("#replyList"), $("#reply-template"));   
            // data.list -> replyData 
            // $("#replyList") -> target 
            // $("#reply-template") -> template

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
        
        // template 위에 있는 reply-template이 들어옴(문법검사를 마친 결과를 참조)
        let templateObj = Handlebars.compile(template.html());  // (template.html()) : template안에있는 내용을 읽어오고 Handlebars.compile가 template에 문법검사를 하고, 이상이 없는지 확인하고 문제가 없으면 templateObj에 참조함
        
        let replyHtml = templateObj(replyData);  // 댓글데이터와 테이블태그(reply-template)가 결합한 데이터가 replyHtml에 들어옴

        console.log("댓글목록: " + replyHtml); // 코드작업이 잘 동작하는지 확인용

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
        if(pageData.next) {
            pageStr += '<li class="page-item">';
            pageStr += '<a class="page-link" href="' + (pageData.endPage + 1) + '">Next</a></li>';
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
                    <label for="retext">retext</label>
                    <textarea class="form-control" id="retext" rows="3"></textarea>
                </div>
            </div>
            <div class="modal-footer">
                <!--id: 중복허용하지않음. name: 중복허용. -->
                <!-- name을 여러개 한 이유는 한번에 제어하기 위해.-->
            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            <button type="button" class="btn btn-primary" name="btnModalReply" id="btnModalReplySave">등록</button>
            <button type="button" class="btn btn-primary" name="btnModalReply" id="btnModalReplyUpdate">수정</button>
            <button type="button" class="btn btn-primary" name="btnModalReply" id="btnModalReplyDelete">삭제</button>
            </div>
        </div>
        </div>
    </div>
    <!-- 여기까지 댓글기능 -->
</body>
</html>