<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>

<head>
    <title>로그인 - 어머이건사야해</title>

    <meta charset="utf-8">
    <meta name="description" content="Bitcamp Project Testing"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    
    <!-- CSRF Token -->
	<meta name="_csrf_token" content="${_csrf.token}"/>
    
    <!-- Manifest -->
    <link rel="manifest" href="/resources/manifest.json">
    
    <!-- Favicon -->
    <link rel="icon" type="image/png" href="/resources/assets/favicon.png">
	
    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" href="/resources/css/bootstrap.min.css">
    
    <link rel="stylesheet" href="/resources/css/sign.css">
    
	<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Nanum+Gothic:400,700&amp;subset=korean">
</head>

<body>
	<div class="signin-page">
		<a href="/"><img src="/resources/assets/logo.png"></a>
		<div class="message-box">
			<c:if test="${status eq 'fail'}"><p class="alert alert-danger">로그인에 실패하였습니다.<br>회원 정보가 존재하지 않거나, 비활성 상태의 계정입니다.</p></c:if>
			<c:if test="${status eq 'success'}"><p class="alert alert-success">회원가입에 성공하였습니다.<br>작성하신 이메일 주소로 인증 메일을 발송하였으니 확인해 주세요.</p></c:if>
			<c:if test="${status eq 'duplicated'}"><p class="alert alert-danger">이미 가입된 회원이 존재합니다.</p></c:if>
		</div>
		<div class="form">
			<form class="register-form">
				<input id="input-signup-email" type="text" name="username" placeholder="email" autocomplete="off" spellcheck="false" maxlength="50" data-toggle="popover" data-content="올바른 이메일 주소 형식이 아닙니다."/>
				<input id="input-signup-nickname" type="text" name="nickname" placeholder="nickname" autocomplete="off" spellcheck="false" maxlength="10" data-toggle="popover" data-content="닉네임은 최소 2글자, 최대 10글자까지 허용됩니다."/>
				<input id="input-signup-password" type="password" name="password" maxlength="16" placeholder="password" autocomplete="off" data-toggle="popover" data-content="비밀번호는 4글자 이상의 영문, 숫자, 특수기호 조합이어야 합니다."/>
				<input id="input-signup-platform" type="hidden" name="platform" value="EOISA"/>
				<button id="btn-signup" type="button">Sign Up</button>
				<p class="message">이미 회원이신가요? <a href="#"><strong>Sign in</strong></a></p>
			</form>
	        
			<form action="/findpw" class="findpw-form" method="POST">
				<input id="input-findpw-email" type="text" name="username" placeholder="email" autocomplete="off" spellcheck="false" maxlength="50" required/>
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				<button id="btn-findpw" type="button">Find</button>
				<p class="find">로그인 화면으로 되돌아갈까요? <a href="#"><strong>Sign In</strong></a></p>
			</form>
	        
	        <form action="/signin" class="signin-form" method="POST">
				<input id="input-signin-email" type="text" name="username" placeholder="email" autocomplete="off" spellcheck="false" maxlength="50" required/>
				<input id="input-signin-password" type="password" name="password" maxlength="50" placeholder="password" autocomplete="off" required/>
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				<button id="btn-signin" type="button">Sign In</button>
				<p class="message">아직 회원이 아니신가요? <a href="#"><strong>회원가입 하기</strong></a></p>
				<p class="find">비밀번호를 잊으셨나요? <a href="#"><strong>비밀번호 찾기</strong></a></p>
			</form>
		</div>
	</div>
 
    <script src="/resources/js/jquery-3.3.1.min.js"></script>
    <script src="/resources/js/popper.min.js"></script>
    <script src="/resources/js/bootstrap.min.js"></script>
    <script src="/resources/js/sign.js"></script>
</body>

</html>