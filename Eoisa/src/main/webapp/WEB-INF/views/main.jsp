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
    
	<!-- CSRF Token -->
	<meta name="_csrf_token" content="${_csrf.token}"/>
    
    <!-- Manifest -->
    <link rel="manifest" href="/resources/manifest.json">
    
    <!-- Favicon -->
    <link rel="icon" type="image/png" href="/resources/assets/favicon.png">
	
    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" href="/resources/css/bootstrap.min.css">
    <link rel="stylesheet" href="/resources/css/bootstrap-toggle.min.css">
    <link rel="stylesheet" href="/resources/css/all.min.css">
    
    <link rel="stylesheet" href="/resources/css/main.css">
    
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
                <img src="<c:if test="${empty userinfo.profile_pic}">/resources/assets/profile.png</c:if>${userinfo.profile_pic}" id="profile-img" class="rounded-circle" alt="profile_picture">
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
            <!--
            <button class="sidenav-dropdown-btn" data-toggle="collapse" data-target=".sidenav-community-dropdown"><i class="fas fa-users"></i> 커뮤니티<i class="fas fa-caret-down"></i></button>
            <div class="sidenav-community-dropdown collapse">
                <a href="#">공지사항</a>
                <a href="#">자유게시판</a>
                <a href="#">후기게시판</a>
            </div>
            -->
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
		    
            <!-- Left-sidebar -->
            <div id="left-sidebar" class="col-lg-3 col-md-12 follow">
            	<sec:authorize access="!isAuthenticated()">
                <div id="sign" class="sidebar-box component-desktop">
                    <div id="sign-header">
                        <h5 class="text-dark"><i class="fas fa-key"></i> Sign in</h5>
                    </div>
                    <hr>
                    <div id="sign-body">
                        <a href="/sign/form" id="signin-eoisa" class="btn btn-sign"><img src="/resources/assets/eoisa.png" alt=""><span id="eoisa">　어이사 로그인</span></a>
                        <button id="social-signin-naver" class="btn btn-sign"><img src="/resources/assets/naver.png" alt=""><span id="naver">　네이버 로그인</span></button>
                        <button id="social-signin-kakao" class="btn btn-sign"><img src="/resources/assets/kakao.png" alt=""><span id="kakao">　카카오 로그인</span></button>
                    </div>
                </div>
                </sec:authorize>
                <sec:authorize access="isAuthenticated()">
				<div id="userinfo" class="sidebar-box component-desktop">
					<div id="userinfo-header">
						<div id="userinfo-profile">
							<c:choose>
								<c:when test="${empty userinfo.profile_pic}"><img src="/resources/assets/profile.png" id="profile-img" class="rounded-circle" alt="profile_picture"></c:when>
								<c:otherwise><img src="${userinfo.profile_pic}" id="profile-img" class="rounded-circle" alt="profile_picture"></c:otherwise>
							</c:choose>
						</div>
						<h5 class="text-dark"><strong>${userinfo.nickname}</strong> <small class="text-black-50">${userinfo.platform}</small></h5>
					</div>
					<hr>
					<sec:authorize access="hasAuthority('USER')">
						<button id="btn-userinfo" class="btn btn-info" data-toggle="modal" data-target="#modal-modify-userinfo" data-backdrop="static" data-keyboard="false">My Info</button>
					</sec:authorize>
					<sec:authorize access="hasAuthority('ADMIN')">
						<button id="btn-userinfo" class="btn btn-danger" onclick="location.href='/admin/main'">Admin Page</button>
					</sec:authorize>
					<form id="form-signout" action="/signout" method="POST">
						<button id="btn-signout" class="btn btn-secondary" type="submit" form="form-signout">Sign out</button>
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					</form>
				</div>
				</sec:authorize>
                
                <c:set var="uri" value="${requestScope['javax.servlet.forward.request_uri']}"/>
                <c:choose>
	                <c:when test="${fn:contains(uri, 'rank') || fn:contains(uri, 'issue') || fn:contains(uri, 'likeit') || fn:contains(uri, 'coffee') || fn:contains(uri, 'merit')}"></c:when>
	                <c:otherwise>
		                <div id="filter" class="sidebar-box">
		                    <div id="filter-header">
		                        <h5 class="text-dark"><i class="fas fa-filter"></i> Filtering <button class="btn btn-transparent" data-toggle="collapse" data-target="#filter-body"><i class="fas fa-angle-down"></i></button></h5>
		                    </div>
		                    <hr>
		                    <div id="filter-body" class="collapse">
		                        <span class="filter-title"><i class="fas fa-globe"></i> 지역</span>
		                        <ul class="filter-menu region">
		                            <li class="filter-list active" data-key="region" data-value="국내">국내</li>
		                            <li class="filter-list active" data-key="region" data-value="해외">해외</li>
		                        </ul>
		                        <span class="filter-title"><i class="fas fa-desktop"></i> 사이트</span>
		                        <ul class="filter-menu site">
		                            <li class="filter-list active" data-key="site" data-value="알구몬">알구몬</li>
		                            <li class="filter-list active" data-key="site" data-value="뽐뿌">뽐뿌</li>
		                            <li class="filter-list active" data-key="site" data-value="딜바다">딜바다</li>
		                            <li class="filter-list active" data-key="site" data-value="쿨앤조이">쿨앤조이</li>
		                            <li class="filter-list active" data-key="site" data-value="클리앙">클리앙</li>
		                            <li class="filter-list active" data-key="site" data-value="루리웹">루리웹</li>
		                            <li class="filter-list active" data-key="site" data-value="어미새">어미새</li>
		                        </ul>
		                        <span class="filter-title"><i class="fas fa-shopping-basket"></i> 쇼핑몰</span>
		                        <ul class="filter-menu shop">
		                            <li class="filter-list active" data-key="shop" data-value="11번가">11번가</li>
		                            <li class="filter-list active" data-key="shop" data-value="옥션">옥션</li>
		                            <li class="filter-list active" data-key="shop" data-value="G마켓">G마켓</li>
		                            <li class="filter-list active" data-key="shop" data-value="티몬">티몬</li>
		                            <li class="filter-list active" data-key="shop" data-value="위메프">위메프</li>
		                            <li class="filter-list active" data-key="shop" data-value="쿠팡">쿠팡</li>
		                            <li class="filter-list active" data-key="shop" data-value="Amazon">Amazon</li>
		                            <li class="filter-list active" data-key="shop" data-value="ebay">eBay</li>
		                            <li class="filter-list active" data-key="shop" data-value="Newegg">Newegg</li>
		                            <li class="filter-list active" data-key="shop" data-value="Aliexpress">Aliexpress</li>
		                        </ul>
		                        <span class="filter-title"><i class="fas fa-hourglass-end"></i> 종료 여부</span>
		                        <ul class="filter-menu isended">
		                            <li class="filter-list active" data-key="isended" data-value="종료">종료 포함</li>
		                        </ul>
		                    </div>
		                </div>
	                </c:otherwise>
                </c:choose>
            </div>

            <!-- Main-Content -->
            <div id="main-content-wrapper" class="col-lg-6 col-md-12">
	            <c:choose>
					<c:when test="${fn:contains(uri, 'rank')}"><c:set var="text" value="어머이건사야해 > <strong>핫딜 랭킹</strong>"/></c:when>
					<c:when test="${fn:contains(uri, 'issue')}"><c:set var="text" value="어머이건사야해 > 테마 핫딜 > <strong>화제의 핫딜</strong>"/></c:when>
					<c:when test="${fn:contains(uri, 'likeit')}"><c:set var="text" value="어머이건사야해 > 테마 핫딜 > <strong>추천 많은 핫딜</strong>"/></c:when>
					<c:when test="${fn:contains(uri, 'coffee')}"><c:set var="text" value="어머이건사야해 > 테마 핫딜 > <strong>커피 한 잔 값 핫딜</strong>"/></c:when>
					<c:when test="${fn:contains(uri, 'merit')}"><c:set var="text" value="어머이건사야해 > 테마 핫딜 > <strong>핫딜 유력</strong>"/></c:when>
					<c:when test="${fn:contains(uri, 'search')}"><c:set var="text" value="어머이건사야해 > 검색 > <strong>'${keyword}'</strong>"/></c:when>
					<c:otherwise><c:set var="hide" value='style="display:none"'/><c:set var="text" value=""/></c:otherwise>
				</c:choose>
				<div class="current-page-info" ${hide}><p>${text}</p></div>
				<c:if test="${empty deal}"><div class="result-info"><i class="far fa-times-circle"></i> 핫딜 정보가 없습니다.</div></c:if>
	            
	            <p class="total-page" data-key="totalPage" data-value="${totalPage}"></p>
	            <div id="main-content">
					<c:forEach var="deal" items="${deal}">
	                <ul data-dealno="${deal.dealno}" class="deal-list">
	                    <li>
	                        <div class="post-group" <c:if test="${not empty deal.isended}">style="opacity: 0.25;"</c:if>>
	                            <div class="deal-header">
	                                <p>
	                                    <span>
                                        	<c:choose>
                                        		<c:when test="${empty deal.isended}"><label class="bg-secondary"><i class="fas fa-stopwatch"></i>${deal.writetime}</label></c:when>
                                        		<c:otherwise><label class="bg-dark"><i class="fas fa-stopwatch"></i><strong>${deal.isended}</strong></label></c:otherwise>
                                        	</c:choose>
	                                        <c:if test="${not empty deal.site_buy}"><label class="bg-info"><i class="fas fa-shopping-bag"></i>${deal.site_buy}</label></c:if>
	                                        <c:if test="${not empty deal.site_src}"><label class="bg-info"><i class="fas fa-flag"></i>${deal.site_src}</label></c:if>
	                                        <label class="bg-danger" style="float:right;">
		                                      	<c:forEach var="ranking" items="${ranking}">
			                                      	<c:choose>
			                                      		<c:when test="${ranking['DEALNO'] eq deal.dealno && ranking['RK'] le 20}"><i class="fas fa-fire-alt"></i>랭킹 ${ranking['RK']}위</c:when>
			                                      		<c:otherwise></c:otherwise>
			                                      	</c:choose>
		                                      	</c:forEach>
	                                        </label>
	                                    </span>
	                                </p>
	                            </div>
	                            <div class="thumbnail">
	                            	<c:choose>
	                            		<c:when test="${empty deal.goods_pic}"><img src="/resources/assets/dealimg.png" alt="deal_thumbnail_default"></c:when>
	                            		<c:otherwise><img src="${deal.goods_pic}" alt="deal_thumbnail"></c:otherwise>
	                            	</c:choose>
	                            </div>
	                            <div class="dealinfo">
	                                <p>
	                                	<a href="/deal/${deal.dealno}" target="_blank">${deal.goods_title}</a>
	                                </p>
	                                <p class="price">
                                       <i class="far fa-credit-card"></i> <c:if test="${empty deal.price}">-</c:if>${deal.price}　|　<i class="fas fa-truck"></i> <c:if test="${empty deal.deliever_fee}">-</c:if>${deal.deliever_fee}
	                                </p>
	                                <p class="naver-price">                                    
                                       <img src="/resources/assets/ns.png" alt="">
                                       <c:choose>
	                                      	<c:when test="${deal.price_naver eq '정보 없음'}">${deal.price_naver}</c:when>
	                                      	<c:otherwise>${deal.price_naver}원</c:otherwise>
                                       </c:choose>
                                       <small class="text-success">
                                       	<c:choose>
                                       		<c:when test="${empty deal.merit}"></c:when>
                                       		<c:otherwise>　${deal.merit}원 이득!</c:otherwise>
                                       	</c:choose>
                                       </small>
	                                </p>
	                                <p>
	                                	<i class="fas fa-comment-alt"></i> ${deal.replycount_src}　|　<i class="fas fa-thumbs-up"></i> ${deal.likeit_src}　|　<i class="fas fa-thumbs-down"></i> ${deal.dislikeit_src}
	                                </p>
	                            </div>
	                            <div class="opinion">
	                                <p>
	                                	<c:set var="i" value="far fa-heart"/><c:forEach var="wishlist" items="${wishlist}"><c:if test="${wishlist.dealno eq deal.dealno}"><c:set var="i" value="far fa-heart fas"/></c:if></c:forEach>
	                                	<button type="button" class="btn btn-xs btn-wish" data-dealno="${deal.dealno}" <sec:authorize access='!isAuthenticated()'>data-toggle="modal" data-target="#modal-signin"</sec:authorize>><i class="${i}"></i> 찜하기</button>
										<button type="button" class="btn btn-xs btn-share" data-clipboard-text="https://eoisa.ml/deal/${deal.dealno}"><i class="fas fa-share-alt"></i> 공유하기</button>
	                                    <c:if test="${deal.replycount ne 0}"><button type="button" class="btn btn-xs btn-toggle" data-dealno="${deal.dealno}"><i class="far fa-eye-slash"></i> 댓글 보기/숨기기</button></c:if>
	                                </p>
	                            </div>
	                            <hr>
	                            <!-- Reply -->
	                            <div class="reply-box-${deal.dealno}" style="display: none;">
	                                <ul class="reply-list-${deal.dealno}"></ul>
	                                <hr>
	                            </div>
	                            <div class="reply-input">
	                                <form data-dealno="${deal.dealno}">
	                                	<textarea name="reply" rows="1" maxlength="500" spellcheck="false" placeholder="댓글 작성 ... " required <sec:authorize access='!isAuthenticated()'>data-toggle="modal" data-target="#modal-signin"</sec:authorize>></textarea>
	                                </form>
	                                <sec:authorize access="isAuthenticated()"><button type="button" class="btn btn-submit">확인</button></sec:authorize>
	                            </div>
	                        </div>
	                    </li>
	                </ul>
					</c:forEach>
					<!-- Ad Banner -->
					<div class="ad-banner bottom"><img src="/resources/assets/adbanner.png" alt="ad_banner"></div>
					<!-- Page Loading Indicator -->
					<div class="loading-indicator"><span class="spinner-border text-danger"></span><strong>　핫딜 정보를 불러오고 있습니다 …</strong></div>
	            </div>
            </div>

            <!-- Right-sidebar -->
            <div id="right-sidebar" class="col-md-3 component-desktop">
                <div id="ranking" class="sidebar-box">
                    <div id="ranking-header">
                        <h5 class="text-dark"><i class="fab fa-hotjar"></i> HotDeal Ranking</h5>
                    </div>
                    <hr>
                    <div id="ranking-body">
                        <div class="ticker">
                            <ul>
                            	<c:forEach var="ranking" items="${ranking}" end="10">
	                                <li>
	                                	<strong class="text-info">#${ranking['RK']}&nbsp;</strong>
	                                	<c:set var="title" value="${ranking['GOODS_TITLE']}"/>
	                                	<c:choose>
	                                		<c:when test="${fn:length(title) ge 15}"><a href="${ranking['URL_SRC']}" target="_blank">${fn:substring(title, 0, 15)} ..</a></c:when>
	                                		<c:otherwise><a href="${ranking['URL_SRC']}" target="_blank">${title}</a></c:otherwise>
	                                	</c:choose>
	                                </li>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>
                </div>
                
                <div id="recent" class="sidebar-box">
                	<div class="ad-banner sidebar">
                		<img src="/resources/assets/adbanner_side.png">
                	</div>
                	<!-- 
                    <div id="recent-article-header">
                        <h5 class="text-dark"><i class="fas fa-chalkboard"></i> Recent Articles</h5>
                    </div>
                    <hr>
                    <div id="recent-article-body">
                        <p><a href="#">공무원 합격은 에듀윌!</a></p>
                        <p><a href="#">공인중개사 합격 ~</a></p>
                        <p><a href="#">주택관리사 합격 ~</a></p>
                        <p><a href="#">서울사이버대학에 다니고</a></p>
                        <p><a href="#">나의 성공시대 시작됬다 ~</a></p>
                    </div>
                    <div id="recent-article-header">
                        <h5 class="text-dark"><i class="fas fa-comments"></i> Recent Replies</h5>
                    </div>
                    <hr>
                    <div id="recent-reply-body">
                        <p><a href="#">고구마 장사가 너무 힘들어요</a></p>
                        <p><a href="#">왜?</a></p>
                        <p><a href="#">고구마가 너무 달아서................</a></p>
                        <p><a href="#">뀔뀌리깔뀔꼴뀔</a></p>
                        <p><a href="#">JMT</a></p>
                    </div>
                    -->
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
                            <a href="/sign/form" id="signin-eoisa" class="btn btn-sign"><img src="/resources/assets/eoisa.png" alt=""><span id="eoisa">　어이사 로그인</span></a>
                            <button id="social-signin-naver" class="btn btn-sign"><img src="/resources/assets/naver.png" alt=""><span id="naver">　네이버 로그인</span></button>
                            <button id="social-signin-kakao" class="btn btn-sign"><img src="/resources/assets/kakao.png" alt=""><span id="kakao">　카카오 로그인</span></button>
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
            					<button type="button" id="btn-namecheck" class="btn btn-info btn-xs" disabled>중복확인</button>　<strong class="message-namecheck"></strong>
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
            						<input type="file" id="input-profile" name="profile_pic" formenctype="multipart/form-data" accept="image/jpeg, image/png">
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
	            							<button type="button" class="btn btn-xs btn-light" onclick="window.open('/deal/${wishlist.dealno}')">바로가기</button>
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
        
        <!-- Scroll to Top Button -->
        <button id="scrolltop" class="btn btn-xs"></button>
    </div>

    <script src="/resources/js/jquery-3.3.1.min.js"></script>
    <script src="/resources/js/popper.min.js"></script>
    <script src="/resources/js/bootstrap.min.js"></script>
    <script src="/resources/js/bootstrap-toggle.min.js"></script>
    <script src="/resources/js/mobile-detect.min.js"></script>
    <script src="/resources/js/infinite-scroll.pkgd.min.js"></script>
    <script src="/resources/js/jquery.easy-ticker.min.js"></script>
    <script src="/resources/js/clipboard.min.js"></script>
    <script src="/resources/js/main.js"></script>
    <script src="/resources/js/reply.js"></script>
    <script src="/resources/js/social-signin.js"></script>
	<script src="https://www.gstatic.com/firebasejs/5.8.2/firebase-app.js"></script>
    <script src="https://www.gstatic.com/firebasejs/5.8.2/firebase-messaging.js"></script>
    <script src="/resources/js/fcm.js"></script>
</body>

</html>