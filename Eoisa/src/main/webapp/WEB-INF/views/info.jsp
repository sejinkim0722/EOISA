<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>

<head>
    <title>어머이건사야해</title>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    
    <!-- Manifest -->
    <link rel="manifest" href="/resources/manifest.json">
    
    <!-- Favicon -->
    <link rel="icon" type="image/png" href="/resources/assets/favicon.png">
	
    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" href="/resources/css/bootstrap.min.css">
    <link rel="stylesheet" href="/resources/css/bootstrap-toggle.min.css">
    <link rel="stylesheet" href="/resources/css/all.min.css">
    
    <link rel="stylesheet" href="/resources/css/info.css">
    
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Nanum+Gothic:400,700&amp;subset=korean">
</head>

<body>
    <!-- Header -->
    <header>
        <sec:authentication var="userinfo" property="principal"/>
        <!-- Page Loading Overlay -->
		<div class="loading-overlay"></div>
        
    	<!-- TopNav -->
        <div id="div-top">
            <div id="div-top-content" class="container">
                <div id="div-logo" class="col-lg-3 col-md-12">
                    <button type="button" class="btn btn-sidenav-open component-mobile"></button>
                    <a href="/">
                    	<img class="component-desktop" src="/resources/assets/logo.png" alt="logo">
                    	<img class="component-mobile" src="/resources/assets/logo_mobile.png" alt="logo">	
                    </a>
                </div>
                <div id="div-search" class="col-lg-5 col-md-12">
                    <form id="form-search" method="POST" action="/search">
	                    <input type="text" name="keyword" spellcheck="false" autocomplete="off" maxlength="20">
	                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form>
                    <i class="fas fa-search fa-2x"></i>
                </div>
            </div>
        </div>

        <!-- MainNav -->
        <nav class="navbar navbar-expand navbar-light top-navbar" data-toggle="sticky-onscroll">
            <div class="navbar-collapse justify-content-center">
                <ul class="navbar-nav">
                    <li class="nav-item home">
                        <a href="/"><i class="fas fa-shopping-cart"></i> 어머이건사야해</a>
                    </li>
                    <li class="component-desktop" style="color: #ced1cc;">|</li>
                    <li class="nav-item rank">
                        <a href="/rank"><i class="fas fa-fire"></i> 핫딜 랭킹</a>
                    </li>
                    <li class="component-desktop" style="color: #ced1cc;">|</li>
                    <li class="nav-item theme">
                        <div class="dropdown">
                            <a data-toggle="dropdown"><i class="fas fa-lightbulb"></i> 테마 핫딜</a>
                            <ul class="dropdown-menu">
                                <li><a class="dropdown-item" href="/theme/issue">화제의 핫딜</a></li>
                                <li><a class="dropdown-item" href="/theme/likeit">추천 많은 핫딜</a></li>
                                <li><a class="dropdown-item" href="/theme/coffee">커피 한 잔 값 핫딜</a></li>
                                <li><a class="dropdown-item" href="/theme/merit">핫딜 유력</a></li>
                            </ul>
                        </div>
                    </li>
                    <!-- 
                    <li class="component-desktop" style="color: #ced1cc;">|</li>
                    <li class="nav-item community component-desktop">
                        <div class="dropdown">
                            <a data-toggle="dropdown"><i class="fas fa-users"></i> 커뮤니티</a>
                            <ul class="dropdown-menu">
                                <li><a class="dropdown-item" href="#">공지사항</a></li>
                                <div class="dropdown-divider"></div>
                                <li><a class="dropdown-item" href="#">자유게시판</a></li>
                                <li><a class="dropdown-item" href="#">후기게시판</a></li>
                            </ul>
                        </div>
                    </li>
                    -->
                    <li class="component-desktop" style="color: #ced1cc;">|</li>
                    <li class="nav-item notification component-desktop">
                    	<div class="dropdown">
                        	<a data-toggle="dropdown"><i class="fas fa-bell"></i> 핫딜 알림</a>
                        	<ul class="dropdown-menu">
								<li style="text-align: center;">
									<input type="checkbox" data-toggle="toggle" data-onstyle="danger" data-on="새 핫딜 알림 켬" data-off="새 핫딜 알림 끔" data-width="145" data-height="25">
								</li>
                        	</ul>
                        </div>
                    </li>
                    <li class="component-desktop" style="color: #ced1cc;">|</li>
                    <li class="nav-item info component-desktop">
                        <a href="/info"><i class="fas fa-info-circle"></i> 사이트 안내</a>
                    </li>
                    <!-- 
                    <li class="nav-item community component-mobile">
                        <a href="#"> 커뮤니티</a>
                    </li>
                    -->
                </ul>
            </div>
        </nav>
        
        <!-- SideNav (for Mobile) -->
        <div class="sidenav component-mobile">
            <button type="button" class="btn btn-sidenav-close"><i class="fas fa-times fa-2x"></i></button>
            <sec:authorize access="!isAuthenticated()">
            <div class="sidenav-profile">
                <img src="/resources/assets/favicon.png" id="profile-img" class="rounded-circle" alt="profile_picture_guest">
                <h4 class="text-dark"><strong>Guest</strong></h4>
            </div>
            </sec:authorize>
            <sec:authorize access="isAuthenticated()">
            <div class="sidenav-profile">
                <img src="<c:if test="${empty userinfo.profile_pic}">/resources/assets/favicon.png</c:if>${userinfo.profile_pic}" id="profile-img" class="rounded-circle" alt="profile_picture">
                <h4 class="text-dark"><strong>${userinfo.nickname}</strong> <small class="text-black-50">${userinfo.platform}</small></h4>
            </div>
            </sec:authorize>
            <hr>
            <sec:authorize access="!isAuthenticated()">
            	<a data-toggle="modal" data-target="#modal-signin"><i class="fas fa-sign-in-alt"></i> 로그인</a>
            </sec:authorize>
            <sec:authorize access="isAuthenticated()">
            	<a data-toggle="modal" data-target="#modal-modify-userinfo" data-backdrop="static" data-keyboard="false"><i class="fas fa-user-edit"></i> 회원정보수정</a>
           		<form id="form-signout-mobile" action="/signout" method="POST">
					<button class="sidenav-signout-btn" type="submit" form="form-signout-mobile"><i class="fas fa-sign-out-alt"></i> 로그아웃</button>
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				</form>
            </sec:authorize>
            <a href="/"><i class="fas fa-home"></i> 어머이건사야해</a>
            <a href="/rank"><i class="fab fa-hotjar"></i> 핫딜 랭킹</a>
            <button class="sidenav-dropdown-btn" data-toggle="collapse" data-target=".sidenav-themedeal-dropdown"><i class="fas fa-lightbulb"></i> 테마 핫딜<i class="fas fa-caret-down"></i></button>
            <div class="sidenav-themedeal-dropdown collapse">
                <a href="/theme/issue">화제의 핫딜</a>
                <a href="/theme/likeit">추천 많은 핫딜</a>
                <a href="/theme/coffee">커피 한 잔 값 핫딜</a>
                <a href="/theme/merit">핫딜 유력</a>
            </div>
            <button class="sidenav-dropdown-btn" data-toggle="collapse" data-target=".sidenav-community-dropdown"><i class="fas fa-users"></i> 커뮤니티<i class="fas fa-caret-down"></i></button>
            <div class="sidenav-community-dropdown collapse">
                <a href="#">공지사항</a>
                <a href="#">자유게시판</a>
                <a href="#">후기게시판</a>
            </div>
            <a><i class="fas fa-bell"></i> 핫딜 알림　<input type="checkbox" data-toggle="toggle" data-onstyle="danger" data-size="mini" data-on="ON" data-off="OFF" data-width="50"></a>
            <a href="/info"><i class="fas fa-info-circle"></i> 사이트 안내</a>
            <hr>
        </div>
        <div class="sidenav-overlay component-mobile"></div>
    </header>

    <!-- Main -->
    <div id="main" class="container base">	
        <div class="content row">
        	<sec:authorize access="isAuthenticated()">
		        <input type="hidden" name="username" value="${userinfo.username}">
		      	<input type="hidden" name="nickname" value="${userinfo.nickname}">
		      	<input type="hidden" name="profile_pic" value="${userinfo.profile_pic}">
		    </sec:authorize>
		    
            <!-- Main-Content -->
            <div id="main-content-wrapper" class="col-lg-12 col-md-12">
				<div class="wrapper">
					<!-- Start navbar -->
					<div id="navbar" class="navbar">
						<ul class="nav-menu">
							<li>
	                            <a data-scroll="gr-1" href="#gr-1" class="dot active">
	                                <span>STEP 1</span>
	                            </a>
	                        </li>
	                        <li>
	                            <a data-scroll="gr-2" href="#gr-2" class="dot">
	                                <span>STEP 2</span>
	                            </a>
	                        </li>
	                        <li>
	                            <a data-scroll="gr-3" href="#gr-3" class="dot">
	                                <span>STEP 3</span>
	                            </a>
	                        </li>
	                        <li>
	                            <a data-scroll="gr-4" href="#gr-4" class="dot">
	                                <span>STEP 4</span>
	                            </a>
	                        </li>
	                        <li>
	                            <a data-scroll="gr-5" href="#gr-5" class="dot">
	                                <span>STEP 5</span>
	                            </a>
	                        </li>
	                        <li>
	                            <a data-scroll="gr-6" href="#gr-6" class="dot">
	                                <span>STEP 6</span>
	                            </a>
	                        </li>
	                    </ul>
	                </div>
	                <!-- End navbar -->

	                <div id="gr-title">
	                    <img src="/resources/assets/information.jpg" alt="title">
	                </div>
	                <div id="gr-1" class="gr-bg bg-1">            
						<div class="gr-contents-odd row align-items-center">
							<div id="border" class="row align-items-center">
								<div id="border-txt1">
									<p class="steps">STEP 1</p>
									<br/>
									<p class="title">어머! 이건 사야 해~!</p><br/>
									<p class="cont">'지름신이 강림한 모두를 위해'<br/><br/>온라인 커뮤니티 곳곳의<br/>핫딜 정보들을 한 데 모아<br/>편리하게 볼 수 있습니다.</p>
								</div>
							</div>
						</div>
	                    <div class="gr-contents-img-odd align-items-center">
	                        <img src="/resources/assets/info_main.png" alt="photo">
	                    </div>
	                </div>
	                <div id="gr-2" class="gr-bg bg-2">
						<div class="gr-contents-even row align-items-center">
							<div id="border2" class="row align-items-center">
								<div id="border-txt2">
									<p class="steps">STEP 2</p>
	                                <br/>
	                                <p class="title">실시간 핫딜 알림</p><br/>
	                                <p class="cont">'하루 종일 눈팅할 필요가 없다!'<br/><br/>새로운 핫딜이 등록될 때마다<br/>웹/모바일 푸시 메시지를<br/>받아볼 수 있습니다.<br/>
									</p>
	                            </div>
	                        </div>
	                    </div>
	                    <div class="gr-contents-img-even align-items-center">
	                        <img src="/resources/assets/info_main_1.png" alt="photo">
	                    </div>
	                </div>
	                <div id="gr-3" class="gr-bg bg-3">    
	                    <div class="gr-contents-odd row align-items-center">
	                       <div id="border3" class="row align-items-center">
	                           <div id="border-txt3">
	                               <p class="steps">STEP 3</p>
	                               <br/>
	                               <p class="title">네이버 최저가 비교</p><br/>
	                               <p class="cont">'이거 진짜 핫딜 맞아?'<br/></br>실시간으로 업데이트되는<br/>네이버쇼핑 최저가 정보를 통해<br/>가격을 비교해볼 수 있습니다.</p>
	                           </div>
	                       </div>
	                    </div>
	                    <div class="gr-contents-img-odd align-items-center">
	                        <img src="/resources/assets/info_main_2.png" alt="photo">
	                    </div>    
	                </div>
	                <div id="gr-4" class="gr-bg bg-4">    
	                    <div class="gr-contents-even row align-items-center">
	                       <div id="border4" class="row align-items-center">
	                           <div id="border-txt4">
	                               <p class="steps">STEP 4</p>
	                               <br/>
	                               <p class="title">테마 핫딜 기능</p><br/>
	                               <p class="cont">'커피 한 잔 가격, 화제의 딜 …'<br/><br/>특정 주제로 재정렬된<br/>알짜배기 핫딜들만 골라보세요!<br/></p>
	                           </div>
	                       </div>
	                    </div>
	                    <div class="gr-contents-img-even align-items-center">
	                        <img src="/resources/assets/info_coffee.png" alt="photo">
	                    </div>    
	                </div>
	                <div id="gr-5" class="gr-bg bg-5">
	                    <div class="gr-contents-odd row align-items-center">
	                       <div id="border5" class="row align-items-center">
	                           <div id="border-txt5">
	                               <p class="steps">STEP 5</p>
	                               <br/>
	                               <p class="title">키워드&필터링 검색</p><br/>
	                               <p class="cont">'검색으로 쉽고 빠르게'<br/><br/>키워드 및 필터링 검색을 통해<br/>내 관심사에 부합하는 핫딜을<br/>찾아볼 수 있습니다.</p>
	                           </div>
	                       </div>
	                    </div>
	                    <div class="gr-contents-img-odd align-items-center">
	                        <img src="/resources/assets/info_main_3.png" alt="photo">
	                    </div>
	                </div>
	                <div id="gr-6" class="gr-bg bg-6">
	                    <div class="gr-contents-even row align-items-center">
	                       <div id="border6">
	                           <div id="border-txt6">
	                               <p class="steps">STEP 6</p>
	                               <br/>
	                               <p class="title">어떤 접속 환경도 OK</p><br/>
	                               <p class="cont">'프로그레시브 웹 앱 지향'<br/><br/>디바이스, 브라우저와 관계 없이<br/>최적의 사이트 환경 제공!</p>
	                           </div>
	                       </div>
	                    </div>
	                    <div class="gr-contents-img-even align-items-center">
	                        <img src="/resources/assets/info_main_4.png" alt="photo">
	                    </div>
	                </div>
	            </div>
            </div>
        </div>
    </div>
    
	<!-- Signin Modal -->
	<sec:authorize access="!isAuthenticated()">
	<div id="modal-signin" class="modal fade">
		<div class="modal-dialog modal-sm modal-dialog-centered">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title text-dark"><i class="fas fa-key"></i> Sign in</h5>
				</div>
				<div class="modal-body">
					<div id="sign-body">
						<a href="/sign/form" id="signin-eoisa" class="btn btn-sign"><img src="/resources/assets/eoisa.png"><span id="eoisa">　어이사 로그인</span></a>
						<button id="social-signin-naver" class="btn btn-sign"><img src="/resources/assets/naver.png"><span id="naver">　네이버 로그인</span></button>
						<button id="social-signin-kakao" class="btn btn-sign"><img src="/resources/assets/kakao.png"><span id="kakao">　카카오 로그인</span></button>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-close" data-dismiss="modal">닫기</button>
				</div>
			</div>
		</div>
	</div>
	</sec:authorize>
        
	<!-- Modify Userinfo Modal -->
	<sec:authorize access="isAuthenticated()">
	<div id="modal-modify-userinfo" class="modal fade">
		<div class="modal-dialog modal-md modal-dialog-centered">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title text-dark"><i class="fas fa-user-edit"></i> 회원정보수정</h5>
				</div>
				<div class="modal-body">
					<div class="userinfo-body">
						<c:if test="${userinfo.platform eq 'EOISA'}">
						<div class="div-username">
							<label for="input-username"><i class="fas fa-envelope"></i> EMAIL <small>(Cannot Modify)</small></label>
							<br>
							<input type="text" id="input-username" name="username" autocomplete="false" spellcheck="false" maxlength="25" placeholder="${userinfo.username}" readonly>
						</div>
						<hr>
						</c:if>
						<div class="div-nickname">
							<label for="input-nickname"><i class="fas fa-id-card"></i> NICKNAME</label>
							<button type="button" id="btn-namecheck" class="btn btn-info btn-xs" disabled>중복체크</button>　<strong class="message-namecheck"></strong>
							<br>
							<input type="text" id="input-nickname" name="nickname" autocomplete="false" spellcheck="false" maxlength="10" value="${userinfo.nickname}" required>
						</div>
						<hr>
						<div class="div-password">
							<label for="input-password"><i class="fas fa-unlock"></i> PASSWORD</label>　<strong class="message-passwordcheck"></strong>
							<br>
							<input type="password" id="input-password" name="password" maxlength="50" placeholder="4자 이상의 숫자+영문+특수기호 조합">
						</div>
					<hr>
					<c:if test="${userinfo.platform eq 'EOISA'}">
					<div class="div-profile">
						<label for="input-profile"><i class="fas fa-camera-retro"></i> PROFILE</label>
						<br>
						<c:choose>
							<c:when test="${empty userinfo.profile_pic}"><img src="/resources/assets/profile.png" id="profile-img" class="rounded-circle" alt="profile_picture"></c:when>
							<c:otherwise><img src="${userinfo.profile_pic}" id="profile-img" class="rounded-circle" alt="profile_picture"></c:otherwise>
						</c:choose>
						<form id="form-profile">
							<input type="file" id="input-profile" name="profile_pic" formenctype="multipart/form-data" accept="image/jpeg, image/png, image/gif">
						</form>
						<button type="button" class="btn btn-dark">업로드</button>
					</div>
					<hr>
					</c:if>
					<div class="div-wishlist">
						<label><i class="fas fa-heartbeat"></i> WISHLIST</label>
						<c:if test="${empty wishlist}"><p class="text-secondary">찜한 핫딜이 없어요!</p></c:if>
						<ul>
						<c:forEach var="wishlist" items="${wishlist}">
							<li class="wishes" data-dealno="${wishlist.dealno}">
								<a href="/search/${wishlist.goods_title}">
								<c:set var="goods_title" value="${wishlist.goods_title}"/>
			            		<c:choose>
			            			<c:when test="${fn:length(goods_title) ge 30}">${fn:substring(goods_title, 0, 30)} …</c:when>
			            			<c:otherwise>${goods_title}</c:otherwise>
			            		</c:choose>
			            		</a>
			            		<span class="badge badge-fill badge-secondary">${wishlist.writetime}</span>
			            		<button type="button" class="btn btn-xs btn-light" onclick="window.open('${wishlist.url_src}')">바로가기</button>
			            	</li>
			            </c:forEach>
			            </ul>
            			<br>
            		</div>
            	</div>
        	</div>
			<div class="modal-footer">
				<button type="button" id="btn-complete" class="btn btn-danger">수정 완료</button>
				<button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
			</div>
		</div>
	</div>
	</div>
	</sec:authorize>

    <script src="/resources/js/jquery-3.3.1.min.js"></script>
    <script src="/resources/js/popper.min.js"></script>
    <script src="/resources/js/bootstrap.min.js"></script>
    <script src="/resources/js/bootstrap-toggle.min.js"></script>
    <script src="/resources/js/info.js"></script>
	<script src="https://www.gstatic.com/firebasejs/5.8.2/firebase-app.js"></script>
    <script src="https://www.gstatic.com/firebasejs/5.8.2/firebase-messaging.js"></script>
    <script src="/resources/js/fcm.js"></script>
</body>

</html>