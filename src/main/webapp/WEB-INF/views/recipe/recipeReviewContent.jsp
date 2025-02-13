<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>집밥의민족</title>
		<link rel="stylesheet" href="/css/recipe/recipe-review-content.css">
	</head>
	
	<body>
		<input type="hidden" id="review_count" value="">
		
		<div class="review_wrap">
			<!-- 리뷰 헤더 -->
			<div class="review_header">
				<div class="review_count">
					<span>리뷰</span>
					<span>7</span>
				</div>
				<div class="review_order">
					<button class="active">최신순</button>
					<button>도움순</button>
				</div>
			</div>
			
			<!-- 리뷰 작성 -->
			<div class="review_write">
				<!-- 로그인 하지 않은 경우 -->
				<c:if test="${ false }">
					<a href="/member/login.do">
						<span>리뷰 작성을 위해 로그인을 해주세요.</span>
						<span>400</span>
					</a>
				</c:if>
				<!-- 로그인 한 경우 -->
				<c:if test="${ true }">
					<form>
						<div class="login_user">
							<img src="/images/common/black.png">
							<span>아잠만</span>
						</div>
						<div class="rating">
							<span class="star" data-value="5">★</span>
							<span class="star" data-value="4">★</span>
							<span class="star" data-value="3">★</span>
							<span class="star" data-value="2">★</span>
							<span class="star" data-value="1">★</span>
							<input type="hidden" id="ratingValue" name="rating" value="0">
						</div>
						<div class="review_input">
							<textarea id="reviewInput" rows="2" maxlength="400" placeholder="욕설, 비방, 허위 정보 및 부적절한 댓글은 사전 경고 없이 삭제될 수 있습니다."></textarea>
							<span>400</span>
						</div>
						<div class="write_btn">
							<button type="submit" id="reviewButton" class="disable" onclick="" disabled>작성하기</button>
						</div>
					</form>
				</c:if>
			</div>
			
			<!-- 리뷰 목록 -->
			<c:if test="${ true }">
				<ul class="review_list">
					<!-- 리뷰를 돌면서 original idx랑 comment idx랑 일치하면 -->
					<%-- <c:foreach> --%>
						<c:if test="${ true }">
							<li>
								<img src="/images/common/black.png">
								<div class="review">
									<!-- 리뷰 헤더 -->
									<div class="review_header">
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
									<!-- 리뷰 좋아요 버튼 -->
									<div class="like_review_btn">
										<button onclick="">
											<c:if test="${ false }">
												<img src="/images/recipe/thumb_up_full.png">
											</c:if>
											<c:if test="${ true }">
												<img src="/images/recipe/thumb_up_empty.png">
											</c:if>
											<p>5</p>
										</button>
										<p>이 리뷰가 도움이 되었다면 꾹!</p>
									</div>
								</div>
							</li>
						</c:if>
					<%-- </c:foreach> --%>
				</ul>
			</c:if>
			
			<div class="more_review_btn">
				<button>
					<span>더보기</span>
					<img src="/images/recipe/arrow_down_black.png">
				</button>
			</div>
		</div>
	</body>
</html>