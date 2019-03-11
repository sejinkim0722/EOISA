<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <title>회원 인증 - 어머이건사야해</title>

    <!-- Favicon -->
    <link rel="icon" type="image/png" href="/resources/assets/favicon.png">
</head>
<body>
	<script src="/resources/js/jquery-3.3.1.min.js"></script>
	<script>
		$(document).ready(function() {
			if('${result}' == 'success') {
				alert("이메일 주소가 인증되었습니다.\n어이사의 회원이 되신 것을 환영합니다.");
			} else if('${result}' == 'fail') {
				alert("유효하지 않은 인증 정보입니다.\n로그인 페이지로 되돌아갑니다.");
			} else if('${result}' == 'exist') {
				alert("가입하신 이메일 주소로 임시 비밀번호를 보내드렸습니다.\n로그인하신 뒤 비밀번호를 수정해 주세요.");
			} else if('${result}' == 'notexist') {
				alert("해당 이메일 주소로 가입된 회원 정보가 없습니다.\n다시 확인해 주세요.");
			} 
			location.href = "/sign/form";
		});
	</script>
</body>
</html>