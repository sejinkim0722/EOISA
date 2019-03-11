<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>관리자 페이지 - 어머이건사야해</title>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    
	<!-- CSRF Token -->
	<meta name="_csrf_token" content="${_csrf.token}"/>
    
    <!-- Favicon -->
    <link rel="icon" type="image/png" href="/resources/assets/favicon.png">
	
    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" href="/resources/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.10.19/css/dataTables.bootstrap4.min.css">
   	<link rel="stylesheet" href="https://cdn.datatables.net/responsive/2.2.3/css/responsive.bootstrap4.min.css">
	<link rel="stylesheet" href="https://cdn.datatables.net/select/1.2.7/css/select.dataTables.min.css">
	<link rel="stylesheet" href="https://cdn.datatables.net/buttons/1.5.2/css/buttons.dataTables.min.css">
    <link rel="stylesheet" href="/resources/css/all.min.css">
    
    <link rel="stylesheet" href="/resources/css/admin.css">
    
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Nanum+Gothic:400,700&amp;subset=korean">
</head>
<body>
	<div id="wrapper">
		<!-- Navigation -->
		<nav class="navbar navbar-expand-md fixed-top navbar-dark shadow rounded">
			<div>
				<button class="navbar-toggler" type="button" data-toggle="collapse" data-target=".side-collapse">
					<span class="navbar-toggler-icon"></span>
				</button>
				<a class="navbar-brand" href="/"><img src="/resources/assets/logo_mobile.png" width="125" height="auto"></a>
			</div>
			<!-- Top Menu Items -->
			<div>
				<ul class="navbar-nav">
					<li class="nav-item dropdown">
						<ul class="dropdown-menu">
							<li class="dropdown-item"><a href="javascript:;"><i class="fa fa-fw fa-user"></i> Profile</a></li>
							<li class="divider"></li>
							<li class="dropdown-item"><a href="javascript:;"><i class="fa fa-fw fa-power-off"></i> Log Out</a></li>
						</ul>
					</li>
				</ul>
			</div>
			<!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens -->
			<div class="collapse navbar-collapse side-collapse">
				<ul class="nav side-nav list-group shadow rounded bg-dark">
					<li class="list-group-item list-group-item-dark bg-dark">
						<a href="/admin/main"><i class="fas fa-home"></i> 메인</a></li>
					<li class="list-group-item list-group-item-dark bg-dark">
						<a href="javascript:;" data-toggle="collapse" data-target="#mem">
							<i class="fa fa-users fa-fw"></i> 회원 관리<i class="fa fa-fw fa-caret-down"></i>
						</a>
						<ul id="mem" class="list-group collapse">
							<li class="list-group-item list-group-item-dark bg-dark"><a
								href="#" onclick="getTable(this); return false;"
								data-type="member" data-column="all">전체 회원 목록</a></li>
							<li class="list-group-item list-group-item-dark bg-dark"><a
								href="#" onclick="getTable(this); return false;"
								data-type="member" data-column="ADMIN">관리자 목록</a></li>
							<li class="list-group-item list-group-item-dark bg-dark"><a
								href="#" onclick="getTable(this); return false;"
								data-type="member" data-column="USER">사용자 목록</a></li>
							<li class="list-group-item list-group-item-dark bg-dark">
								<h6 class="text-center">회원 검색</h6>
								<div class="input-group ml-1">
									<input type="text" class="form-control" placeholder="회원 찾기"
										aria-label="username" aria-describedby="memberSearch">
									<div class="input-group-append" id="memberSearch">
										<button class="btn btn-info searching" type="button"
											data-type="member">검색</button>
									</div>
								</div>
							</li>
						</ul></li>
					<li class="list-group-item list-group-item-dark bg-dark"><a
						href="javascript:;" data-toggle="collapse" data-target="#boards">
							<i class="fa fa-table fa-fw"></i> 게시판 관리<i class="fa fa-fw fa-caret-down"></i>
					</a>
						<ul id="boards" class="list-group collapse">
							<li class="list-group-item list-group-item-dark bg-dark">
								<a href="#" onclick="getTable(this); return false;" data-type="board" data-column="notice">공지사항</a>
							</li>
							<li class="list-group-item list-group-item-dark bg-dark">
								<a href="#" onclick="getTable(this); return false;" data-type="board" data-column="free">자유게시판</a>
							</li>
							<li class="list-group-item list-group-item-dark bg-dark">
								<a href="#" onclick="getTable(this); return false;" data-type="board" data-column="review">후기게시판</a>
							</li>
							<li class="list-group-item list-group-item-dark bg-dark">
								<h6 class="text-center">게시글 검색</h6>
								<div class="input-group ml-1">
									<input type="text" class="form-control" placeholder="게시글찾기" aria-label="username" aria-describedby="boardSearch">
									<div class="input-group-append" id="boardSearch">
										<button class="btn btn-info searching" type="button" data-type="board">검색</button>
									</div>
								</div>
							</li>
						</ul></li>
					<li class="list-group-item list-group-item-dark bg-dark">
						<a href="javascript:;" data-toggle="collapse" data-target="#deals">
							<i class="fa fa-shopping-cart fa-fw"></i> 핫딜 관리<i class="fa fa-fw fa-caret-down"></i>
						</a>
						<ul id="deals" class="list-group collapse">
							<li class="list-group-item list-group-item-dark bg-dark"><a
								href="#" onclick="getTable(this); return false;"
								data-type="deal" data-column="null">전체 핫딜</a></li>
							<li class="list-group-item list-group-item-dark bg-dark">
								<h6 class="text-center">핫딜 검색</h6>
								<div class="input-group ml-1">
									<input type="text" class="form-control" placeholder="딜 찾기"
										aria-label="username" aria-describedby="dealSearch">
									<div class="input-group-append" id="dealSearch">
										<button class="btn btn-info searching" type="button"
											data-type="deal">검색</button>
									</div>
								</div>
							</li>
						</ul>
					</li>
				</ul>
			</div>
			<!-- /.navbar-collapse -->
		</nav>

		<div id="page-wrapper">
			<div class="container-fluid">
				<!-- Page Heading -->
				<div class="row">
					<div class="col-lg-12">
						<h1 id="page-title">
							관리자 페이지 <small>전체 현황</small>
						</h1>
					</div>
				</div>
				<!-- /.row -->
				<div id="content">
					<div class="row">
						<div class="col-xl-3 col-lg-6">
							<div class="card card-primary">
								<div class="card-header card-primary bg-dark">
									<div class="row">
										<div class="col-xs-3">
											<i class="fa fa-users fa-5x"></i>
										</div>
										<div class="col-xs-9 text-left">
											<div class="huge">${memberCount}</div>
											<div class="content">전체 회원 수</div>
										</div>
									</div>
								</div>
								<div class="card-footer card-default border border-secondary">
									<a href="javascript:;"><span class="float-left">View Details</span><span class="float-right">
										<i class="fa fa-arrow-circle-right"></i></span>
										<div class="clearfix"></div>
									</a>
								</div>
							</div>
						</div>
						<div class="col-xl-3 col-lg-6">
							<div class="card card-yellow card-inverse">
								<div class="card-header card-yellow">
									<div class="row">
										<div class="col-xs-3">
											<i class="fa fa-shopping-cart fa-5x"></i>
										</div>
										<div class="col-xs-9 text-left">
											<div class="huge">${dealCount}</div>
											<div class="content">전체 핫딜 수</div>
										</div>
									</div>
								</div>
								<div class="card-footer card-yellow">
									<a href="javascript:;"> <span class="float-left">View Details</span> <span class="float-right"> 
										<i class="fa fa-arrow-circle-right"></i></span>
										<div class="clearfix"></div>
									</a>
								</div>
							</div>
						</div>
						<div class="col-xl-3 col-lg-6">
							<div class="card card-green">
								<div class="card-header card-green">
									<div class="row">
										<div class="col-xs-3">
											<i class="fa fa-tasks fa-5x"></i>
										</div>
										<div class="col-xs-9 text-left">
											<div class="huge">0</div>
											<div class="content">전체 게시글 수</div>
										</div>
									</div>
								</div>
								<div class="card-footer card-green">
									<a href="javascript:;"><span class="float-left">View Details</span><span class="float-right">
										<i class="fa fa-arrow-circle-right"></i></span>
										<div class="clearfix"></div>
									</a>
								</div>
							</div>
						</div>
					</div>
					<!-- /.row -->
				</div>
			</div>
			<!-- /.container-fluid -->
		</div>
		<!-- /#page-wrapper -->
	</div>
	<!-- /#wrapper -->

	<script src="/resources/js/jquery-3.3.1.min.js"></script>
	<script src="/resources/js/bootstrap.min.js"></script>
	<script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
	<script src="https://cdn.datatables.net/1.10.19/js/dataTables.bootstrap4.min.js"></script>
	<script src="https://cdn.datatables.net/select/1.2.7/js/dataTables.select.min.js"></script>
	<script src="https://cdn.datatables.net/buttons/1.5.2/js/dataTables.buttons.min.js"></script>
	<script src="/resources/js/admin.js"></script>
</body>
</html>