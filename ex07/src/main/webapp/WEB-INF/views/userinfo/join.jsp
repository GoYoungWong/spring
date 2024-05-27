<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!doctype html>
<html lang="en" class="h-100">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
    <meta name="generator" content="Hugo 0.101.0">
    <title>Sticky Footer Navbar Template · Bootstrap v4.6</title>

    <link rel="canonical" href="https://getbootstrap.com/docs/4.6/examples/sticky-footer-navbar/">

    <!-- 부투스트랩파일, JQuery, css파일(css선택자사용을위해) 떄문에 앞에있어야 함-->
	<%@include file="/WEB-INF/views/comm/config.jsp" %>


    <!-- Favicons -->
<link rel="apple-touch-icon" href="/docs/4.6/assets/img/favicons/apple-touch-icon.png" sizes="180x180">
<link rel="icon" href="/docs/4.6/assets/img/favicons/favicon-32x32.png" sizes="32x32" type="image/png">
<link rel="icon" href="/docs/4.6/assets/img/favicons/favicon-16x16.png" sizes="16x16" type="image/png">
<link rel="manifest" href="https://getbootstrap.com/docs/4.6/assets/img/favicons/manifest.json">
<link rel="mask-icon" href="/docs/4.6/assets/img/favicons/safari-pinned-tab.svg" color="#563d7c">
<link rel="icon" href="/docs/4.6/assets/img/favicons/favicon.ico">
<meta name="msapplication-config" content="/docs/4.6/assets/img/favicons/browserconfig.xml">
<meta name="theme-color" content="#563d7c">


    <style>
      .bd-placeholder-img {
        font-size: 1.125rem;
        text-anchor: middle;
        -webkit-user-select: none;
        -moz-user-select: none;
        -ms-user-select: none;
        user-select: none;
      }

      @media (min-width: 768px) {
        .bd-placeholder-img-lg {
          font-size: 3.5rem;
        }
      }
    </style>

    
    <!-- Custom styles for this template -->
    <!-- css  static에 sticky-footer-navbar.css 이름으로 따로 관리하고 있음 -->
    <link href="/sticky-footer-navbar.css" rel="stylesheet">
  </head>
  <body class="d-flex flex-column h-100">
    
<!-- header.jsp파일을 참조함 -->    
<%@include file="/WEB-INF/views/comm/header.jsp" %>

<!-- Begin page content -->
<main role="main" class="flex-shrink-0">
<div class="container">
    <h1 class="mt-5" style="text-align: center">Register</h1>
    <div class="row">
        <div class="col">
            <div class="card card-info">
                <div class="card-header">
                <h3 class="card-title">Join Form</h3>
                </div>
                
                <form class="form-horizontal" id="joinForm" action="/userinfo/join" method="post">
                <div class="card-body">
                        <div class="form-group row">     <!-- 아이디 중복체크 만들기 위해 반으로 나누기. col 2 + col 4 = 6 -->
                            <label for="u_id" class="col-sm-2 col-form-label">U_ID</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="u_id" name="u_id" placeholder="U_ID">
                            </div>
                            <div class="col-sm-4">
                                <button type="button" class="btn btn-outline-primary" id="btnIDCheck">ID Check</button>
                            </div>
                            <span for="u_id" class="col-sm-2" style="color:red" id="idCheckMsg"></span>
                        </div>
                    	

                    <div class="form-group row">   <!-- 비밀번호 작성 및 비밀번호확인-->
                        <label for="u_pwd" class="col-sm-2 col-form-label">U_PWD</label>
                        <div class="col-sm-4">
                            <input type="password" class="form-control" id="u_pwd" name="u_pwd" placeholder="U_PWD">
                        </div>
                        <label for="u_confirm_pwd" class="col-sm-2 col-form-label">U_Confirm_PWD</label>
                        <div class="col-sm-4">
                            <input type="password" class="form-control" id="u_confirm_pwd" placeholder="u_confirm_pwd">
                        </div>
                    </div>


                    <div class="form-group row">  <!-- 이름 작성 -->
                        <label for="u_name" class="col-sm-2 col-form-label">U_NAME</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="u_name" name="u_name" placeholder="U_NAME">
                        </div>
                    </div>


                    <div class="form-group row">  <!-- 이메일작성 및 인증요청 및 인증확인 -->
                        <label for="u_email" class="col-sm-2 col-form-label">U_EMAIL</label>
                        <div class="col-sm-3">
                            <input type="email" class="form-control" id="u_email" name="u_email" placeholder="U_EMAIL">
                        </div>
                        <div class="col-sm-2">
                            <button type="button" class="btn btn-outline-primary" id="btnMailAuthcode">메일인증요청</button>
                        </div>
                        <div class="col-sm-3">
                            <input type="text" class="form-control" id="u_authcode" name="u_authcode" placeholder="인증코드를 입력하세요.">
                        </div>
                        <div class="col-sm-2">
                            <button type="button" class="btn btn-outline-primary" id="btnConfirmAuth">인증확인</button>
                        </div>
                    </div>


                    <div class="form-group row">           <!-- 우편번호 -->
                        <label for="u_zipcode" class="col-sm-2 col-form-label">U_ZIPCODE</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="sample2_postcode" name="u_zip_code" placeholder="U_ZIPCODE">
                        </div>
                        <div class="col-sm-4">
                            <button type="button" class="btn btn-outline-primary" onclick="sample2_execDaumPostcode()">우편번호</button>
                        </div>
                    </div>
                    


                    <div class="form-group row">      <!-- 주소 -->
                        <label for="u_addr" class="col-sm-2 col-form-label">U_ADDR</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="sample2_address" name="u_addr" placeholder="U_ADDR">
                        </div>
                    </div>


                    <div class="form-group row">      <!-- 상세주소 -->
                        <label for="u_addrdetail" class="col-sm-2 col-form-label">U_ADDRDETAIL</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="sample2_detailAddress" name="u_addrdetail" placeholder="U_ADDRDETAIL">
                            <input type="hidden" id="sample2_extraAddress" placeholder="참고항목">
                        </div>
                    </div>


                    <div class="form-group row">       <!-- 전화번호 -->
                        <label for="u_phone" class="col-sm-2 col-form-label">U_PHONE</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="u_phone" name="u_phone" placeholder="U_PHONE">
                        </div>
                    </div>
                </div>
                
                    <div class="card-footer"><!-- id="btnJoin"   -->
                    <button type="button" class="btn btn-info" id="btnJoin">Sign up</button> <!--type에 submit(전송기능있음) 이거나 button(전송기능없음) 이거나 하면 코드가 달라짐-->
                    <button type="reset" class="btn btn-default float-right">Cancel</button>
                    </div>
                    
                </form>
            </div>
        </div>
    </div>
</div>
</main>

<!-- footer.jsp파일을 참조 -->
<%@include file="/WEB-INF/views/comm/footer.jsp" %>
    

<!-- iOS에서는 position:fixed 버그가 있음, 적용하는 사이트에 맞게 position:absolute 등을 이용하여 top,left값 조정 필요 -->
<div id="layer" style="display:none;position:fixed;overflow:hidden;z-index:1;-webkit-overflow-scrolling:touch;">
    <img src="//t1.daumcdn.net/postcode/resource/images/close.png" id="btnCloseLayer" style="cursor:pointer;position:absolute;right:-3px;top:-3px;z-index:1" onclick="closeDaumPostcode()" alt="닫기 버튼">
    </div>
    
    <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
    <script>
        // 우편번호 찾기 화면을 넣을 element
        var element_layer = document.getElementById('layer');
    
        function closeDaumPostcode() {
            // iframe을 넣은 element를 안보이게 한다.
            element_layer.style.display = 'none';
        }
    
        function sample2_execDaumPostcode() {
            new daum.Postcode({
                oncomplete: function(data) {
                    // 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.
    
                    // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                    // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                    var addr = ''; // 주소 변수
                    var extraAddr = ''; // 참고항목 변수
    
                    //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                    if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                        addr = data.roadAddress;
                    } else { // 사용자가 지번 주소를 선택했을 경우(J)
                        addr = data.jibunAddress;
                    }
    
                    // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
                    if(data.userSelectedType === 'R'){
                        // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                        // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                        if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                            extraAddr += data.bname;
                        }
                        // 건물명이 있고, 공동주택일 경우 추가한다.
                        if(data.buildingName !== '' && data.apartment === 'Y'){
                            extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                        }
                        // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                        if(extraAddr !== ''){
                            extraAddr = ' (' + extraAddr + ')';
                        }
                        // 조합된 참고항목을 해당 필드에 넣는다.
                        document.getElementById("sample2_extraAddress").value = extraAddr;
                    
                    } else {
                        document.getElementById("sample2_extraAddress").value = '';
                    }
    
                    // 우편번호와 주소 정보를 해당 필드에 넣는다.
                    document.getElementById('sample2_postcode').value = data.zonecode;
                    document.getElementById("sample2_address").value = addr;
                    // 커서를 상세주소 필드로 이동한다.
                    document.getElementById("sample2_detailAddress").focus();
    
                    // iframe을 넣은 element를 안보이게 한다.
                    // (autoClose:false 기능을 이용한다면, 아래 코드를 제거해야 화면에서 사라지지 않는다.)
                    element_layer.style.display = 'none';
                },
                width : '100%',
                height : '100%',
                maxSuggestItems : 5
            }).embed(element_layer);
    
            // iframe을 넣은 element를 보이게 한다.
            element_layer.style.display = 'block';
    
            // iframe을 넣은 element의 위치를 화면의 가운데로 이동시킨다.
            initLayerPosition();
        }
    
        // 브라우저의 크기 변경에 따라 레이어를 가운데로 이동시키고자 하실때에는
        // resize이벤트나, orientationchange이벤트를 이용하여 값이 변경될때마다 아래 함수를 실행 시켜 주시거나,
        // 직접 element_layer의 top,left값을 수정해 주시면 됩니다.
        function initLayerPosition(){
            var width = 300; //우편번호서비스가 들어갈 element의 width
            var height = 400; //우편번호서비스가 들어갈 element의 height
            var borderWidth = 5; //샘플에서 사용하는 border의 두께
    
            // 위에서 선언한 값들을 실제 element에 넣는다.
            element_layer.style.width = width + 'px';
            element_layer.style.height = height + 'px';
            element_layer.style.border = borderWidth + 'px solid';
            // 실행되는 순간의 화면 너비와 높이 값을 가져와서 중앙에 뜰 수 있도록 위치를 계산한다.
            element_layer.style.left = (((window.innerWidth || document.documentElement.clientWidth) - width)/2 - borderWidth) + 'px';
            element_layer.style.top = (((window.innerHeight || document.documentElement.clientHeight) - height)/2 - borderWidth) + 'px';
        }
    </script>


    <script>
        
        $(document).ready(function() {   // ready event start

            let useIDCheck = false; // 아이디 중복체크 기능을 사용했는 지 유무를 확인.

            $("#btnIDCheck").on("click", function() {

                // .val : 아이디 박스에 값을 읽어옴
                if($("#u_id").val() == "") {
                    alert("아이디를 입력하세요.");
                    $("#u_id").focus();
                    return;    // 아이디를 입력하지 않으면 더이상 프로그램이 진행이 되면 안되서 return; 을 넣어서 동작을 멈춤
                }
                
                $.ajax({

                    url: '/userinfo/idCheck',
                    type: 'get',
                    data: {u_id : $("#u_id").val()},   // u_id: 스프링주소에 메서드파라미터와(매개변수) 일치 해야한다.
                    dataType: 'text',    // text,html,xml,json 값이 사용.   text는 리턴타입이 String이여서 text로 잡는다.
                    success: function(result) {   // result : idUse변수의 값이 들어온다(값이 일치해야함 대소문자도 같아야함).  success: HttpStatus.OK로 인해 상태코드200 이 작동해 성공했다는 의미
                        if(result == 'yes') {
                            alert("아이디 사용가능");
                            $("#idCheckMsg").html("사용가능");
                        }else {
                            alert("아이디 사용불가능");
                            $("#idCheckMsg").html("사용불가능");
                            $("#u_id").val(""); // 입력한 아이디가 삭제.
                            $("#u_id").focus();
                        }
                        
                    }



                });

            });
            // 메일인증코드요청
            $("#btnMailAuthcode").on("click", function() {
                
                if($("#u_email").val() == "") {
                    alert("메일을 입력하세요.");
                    $("#u_email").focus();
                    return;
                }

                $.ajax({
                    url: '/email/authcode',
                    type: 'get',
                    data: {receiverMail : $("#u_email").val()},  // EmailDTO dto로 받을 데이터
                    dataType: 'text', // success의 리턴타입이 String이기 때문에 text사용
                    success: function(result) {
                        if(result == "success") {  // Controller에 success에서 넘어온다.
                            alert("메일로 인증코드가 발급되었습니다.")
                        }
                    }
                });
            });

            // 메일인증확인
            $("#btnConfirmAuth").on("click", function() {

                if($("#u_authcode").val() == "") {
                    alert("인증코드를 입력하세요.");
                    $("#u_authcode").focus();
                    return;
                }

                // 제이쿼리 ajax문법    이 내용과 스프링을 일치시키는 작업을 해줘야함.  그래야 작동됨.
                $.ajax({
                    url: '/email/confirm_authcode',
                    type: 'get',
                    data: {authcode : $("#u_authcode").val()},
                    dataType: 'text',
                    success: function(result) {
                        if(result == "success") {
                            alert("인증이 확인되었습니다.");
                        }else if(result == "fail") {
                            alert("인증코드값을 확인해주세요.");
                        }else if(result == "request") {
                            alert("인증코드가 소멸되었습니다.\n인증요청메일을 다시 진행해주세요.");
                        }
                    },
                    error: function() {     // 스프링쪽에서 에러가 나면 클라이언트에서 여기로 넘어옴

                    }

                });
            });

            // 회원가입 클릭
            // 아래 코드는 양립할수가 없다.
            // 1) <button type="submit" class="btn btn-info" id="btnJoin">Sign up</button> - 폼 submit이벤트 사용.
            /*$("#joinForm").on("submit", function(e) {
                e.preventDefault();
                console.log("submit event");
                return;
            });
            */


            // 2) <button type="button" class="btn btn-info" id="btnJoin">Sign up</button> - click이벤트 사용.
            $("#btnJoin").on("click", function() {
                // console.log("click event");

                // 회원가입 유효성검사. : 정규식활용.

                $("#joinForm").submit();  // Form에 있던 정보를 전송
            
            });

        }); // ready event end



    </script>
  </body>
</html>
