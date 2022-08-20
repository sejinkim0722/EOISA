<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>

<head>
	<title>접근 거부 - 어머이건사야해</title>

	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    
	<!-- Manifest -->
	<link rel="manifest" href="/resources/manifest.json">
    
	<!-- Favicon -->
	<link rel="icon" type="image/png" href="/resources/assets/favicon.png">
	
	<!-- Bootstrap core CSS -->
	<link rel="stylesheet" href="/resources/css/bootstrap.min.css">
	<link rel="stylesheet" href="/resources/css/all.min.css">
    
	<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Nanum+Gothic:400,700&amp;subset=korean">
	
	<style>
		body {
		    font-family: 'Nanum Gothic', sans-serif;
			background-color: #eee;
			text-align: center;
		}
		
		.container {
			position: absolute;
			top: 50%;
  			left: 50%;
  			transform: translate(-50%, -50%);
		}
		
		i { 
			color: #ff5a5f;
			margin-bottom: 10px;	
		}
		
		.title { 
			color: #2b2d2e;
			font-size: 72px;
			font-weight: bold;
			margin-bottom: -25px;
		}
		
		.title-sub {
			color: #565a5c;
			font-size: 36px;
			margin-bottom: 25px;
		}
		
		.text {
			color: #2b2d2e;
			font-size: 24px;
			margin-bottom: 50px;
		}
		
		p { margin: 0; }
		
		@media (max-width: 991px) {
			.container {
				zoom: 0.75;
			}
		}
	</style>
</head>

<body>
	<div class="container">
		<i class="fas fa-exclamation-triangle fa-8x"></i>
		<p class="title">403</p>
		<p class="title-sub">Access Denied</p>
		<p class="text">접근 권한이 없습니다.</p>
		<button class="btn btn-dark btn-lg" onclick="location.href='/'">Return to Main Page</button>
	</div>
</body>

</html>