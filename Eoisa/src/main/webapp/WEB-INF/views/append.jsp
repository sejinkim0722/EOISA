<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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
                            		<c:when test="${empty deal.goods_pic}"><img src="/resources/assets/dealimg.png"></c:when>
                            		<c:otherwise><img src="${deal.goods_pic}"></c:otherwise>
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
                                      <img src="/resources/assets/ns.png">
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
									<button type="button" class="btn btn-xs btn-share" data-clipboard-text="${deal.url_src}"><i class="fas fa-share-alt"></i> 공유하기</button>
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
                                <sec:authorize access="isAuthenticated()"><button class="btn btn-submit">확인</button></sec:authorize>
                            </div>
                        </div>
                    </li>
                </ul>
				</c:forEach>
				<!-- Ad Banner -->
				<div class="ad-banner"><img src="/resources/assets/adbanner.png"></div>
				<!-- Page Loading Indicator -->
				<div class="loading-indicator"><span class="spinner-border text-danger"></span><strong>　핫딜 정보를 불러오고 있습니다 …</strong></div>
            </div>