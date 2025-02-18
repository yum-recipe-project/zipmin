<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>집밥의민족</title>
		<%@include file="../common/head.jsp" %>
		<link rel="stylesheet" href="/css/mypage/review.css">
		<script src="/js/mypage/review.js"></script>
	</head>
	<body>
		<%@include file="../common/header.jsp" %>
		
		<main id="container">
			<div class="content">
				<div class="myreview_wrap">
					<!-- 내 리뷰 헤더 -->
					<div class="myreview_header">
						<a href="/mypage.do">
							<span>
								내 정보 관리
								<img src="/images/mypage/arrow_right.png">
							</span>
						</a>
						<h2>내 요리 후기</h2>
					</div>
					
					<!-- 내 리뷰 개수 -->
					<div class="myreview_count">
						<span>4개</span>
					</div>
					
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
										<img src="/images/common/black.png">
										<div class="myreview_info">
											<!-- 리뷰 헤더 -->
											<div class="myreview_header">
												<div class="myreview_writer">
													<span>아잠만</span>
													<span>2025.02.11</span>
												</div>
												<c:if test="${ true }">
													<div class="myreview_action">
														<a href="">수정</a>
														<a href="">삭제</a>
													</div>
												</c:if>
											</div>
											<!-- 리뷰 별점 -->
											<div class="myreview_score">
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
											<p class="myreview_content">
												녹차 아이스크림은 배스킨라빈스가 최고입니다
											</p>
										</div>
									</div>
								</li>
							<%-- </c:forEach> --%>
						</ul>
					</c:if>
					
					<!-- 더보기 버튼 -->
					<div class="more_btn">
						<button>
							<span>더보기</span>
							<img src="/images/mypage/arrow_down.png">
						</button>
					</div>
					
					
					
					
				</div>
			</div>
		</main>
		
		<%@include file="../common/footer.jsp" %>
	</body>
</html>