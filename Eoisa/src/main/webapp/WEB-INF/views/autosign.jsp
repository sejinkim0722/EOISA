<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <title>로그인 - 어머이건사야해</title>

    <!-- Favicon -->
    <link rel="icon" type="image/png" href="/resources/assets/favicon.png">
</head>
<body>
	<form id="signup" action="/signup" method="POST">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<input type="hidden" name="username" type="text" value="${username}"/>
		<input type="hidden" name="password" type="text" value="${password}"/>
		<input type="hidden" name="nickname" type="text" value="${nickname}"/>
		<input type="hidden" name="profile_pic" type="text" value="${profile_pic}"/>
		<input type="hidden" name="platform" type="text" value="${platform}"/>
	</form>
	
	<form id="signin" action="/signin" target="parent" method="POST">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<input type="hidden" name="username" type="text" value="${username}"/>
		<input type="hidden" name="password" type="text" value="${password}"/>
	</form>

	<script src="/resources/js/jquery-3.3.1.min.js"></script>
	<script>
		$(document).ready(function() {
			if('${request}' == 'signup') {
				$("form#signup").submit();
			} else if('${request}' == 'signin') {
				$("form#signin").submit();
				self.close();
			}
		});
	</script>
</body>
</html>