<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>집밥의민족</title>
		<%@include file="../common/head.jsp" %>
		<link rel="stylesheet" href="/css/mypage/review-content.css">
	</head>
	
	<body>
		<input type="hidden" id="reviewCount" value="">
		
		<!-- 내 리뷰 내용 -->
		<c:if test="${ true }">
			<ul class="myreview_list">
				<!-- 내가 작성한 댓글을 돌면서 -->
				<%-- <c:forEach> --%>
					<li>
						<div class="myreview_title">
							<c:if test="${ true }">
								<a href="/recipe/viewRecipe.do">마법의 카레 가루</a>
							</c:if>
						</div>
						<div class="myreview">
							<img class="review_avatar" src="/images/common/test.png">
							<div class="review_inner">
								<!-- 리뷰 정보 -->
								<div class="review_info">
									<div class="review_writer">
										<span>아잠만</span>
										<span>2025.02.11</span>
									</div>
									<c:if test="${ true }">
										<div class="review_action">
											<a href="">수정</a>
											<a href="">삭제</a>
										</div>
									</c:if>
								</div>
								<!-- 리뷰 별점 -->
								<div class="review_score">
									<div class="star">
										<%-- <c:forEach> --%>
											<img src="/images/recipe/star_full.png">
										<%-- </c:forEach> --%>
										<%-- <c:forEach> --%>
											<img src="/images/recipe/star_empty.png">
										<%-- </c:forEach> --%>
									</div>
									<p>3</p>
								</div>
								<!-- 리뷰 내용 -->
								<p class="review_content">
									녹차 아이스크림은 배스킨라빈스가 최고입니다
								</p>
							</div>
						</div>
					</li>
				<%-- </c:forEach> --%>
			</ul>
		</c:if>
		
	</body>
</html>