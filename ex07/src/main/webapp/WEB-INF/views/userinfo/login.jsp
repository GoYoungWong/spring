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
                <h3 class="card-title">login Form</h3>
                </div>
                
                <form class="form-horizontal" id="loginForm" action="/userinfo/login" method="post">
                <div class="card-body">
                        <div class="form-group row">     <!-- 아이디입력-->
                            <label for="u_id" class="col-sm-4 col-form-label">U_ID</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control" id="u_id" name="u_id" placeholder="U_ID">
                            </div>
                        </div>
                    	

                    <div class="form-group row">   <!-- 비밀번호입력-->
                        <label for="u_pwd" class="col-sm-4 col-form-label">U_PWD</label>
                        <div class="col-sm-8">
                            <input type="password" class="form-control" id="u_pwd" name="u_pwd" placeholder="U_PWD">
                        </div>
                    </div>
                </div>
                
                    <div class="card-footer text-center" ><!-- id="btnJoin"   -->
                    <button type="submit" class="btn btn-info" id="btnJoin">Login</button> <!--type에 submit(전송기능있음) 이거나 button(전송기능없음) 이거나 하면 코드가 달라짐-->
                    </div>
                    
                </form>
            </div>
        </div>
    </div>
</div>
</main>

<!-- footer.jsp파일을 참조 -->
<%@include file="/WEB-INF/views/comm/footer.jsp" %>

    <script>

        let msg = '$(msg)';  // "failPW" or "failID"
        if(msg == "failID") {
            alert("아이디를 정확히 입력해주세요.");
            document.getElementById("u_id").focus();
        }else if(msg == "failPW") {
            alert("비밀번호를 정확히 입력해주세요.");
            document.getElementById("u_pwd").focus();
        }
        
        $(document).ready(function() {   // ready event start

            

        }); // ready event end



    </script>
  </body>
</html>
