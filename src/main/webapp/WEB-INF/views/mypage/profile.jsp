<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>집밥의민족</title>
		<%@include file="../common/head.jsp" %>
		<link rel="stylesheet" href="/css/mypage/profile.css">
		<script src="/js/mypage/profile.js"></script>
	</head>
	<body>
		<%@include file="../common/header.jsp" %>
		
		<main id="container">
			<div class="content">
			
				<div class="profile_wrap">
					
					<!-- 유저 정보 -->
					<div class="info_wrap">
					
						<div class="info_inner">
						
							<!-- 유저 프로필 -->
							<div class="profile">
							
								<span class="profile_img"></span>
								<span>아잠만</span>
								
								<button class="btn_primary followButton">
									<span>팔로우</span>
								</button>
								
								<div class="follow_txt">
									<a href="/mypage/follower.do">팔로워</a>
									<a href="/mypage/following.do">팔로잉</a>
								</div>
								
							</div>
						
							<!-- 등급/소개 -->
							<div class="summary">
							
								<div class="grade_field">
									<label>등급</label>
									<input type="hidden" id="nameInput" name="" value="" placeholder="이름 입력">
									<p>빨간양말</p>
								</div>
								<div class="comment_field">
									<label>소개</label>
									<input type="hidden" id="nameInput" name="" value="" placeholder="이름 입력">
									<p>안녕하세요, 저는 아잠만입니다. 저는 요즘 곱창에 빠져있어요. 곱창레시피 많이 올릴게요~</p>
								</div>
								
							</div>
							
						</div>
				
					</div>
					
					<!-- 유저 레시피 -->
					<div class="recipe_wrap">
					
						<h2>아잠만님의 레시피</h2>
						
						<!-- 레시피 개수 -->
						<div class="recipe_count">
							<span>3개</span>
						</div>
						
						<!-- 레시피 목록 -->
						<div class="list_wrap">
						    <!-- 전체 게시글 -->
					    	<ul class="list">
					    		<li>
					    			<div class="recipe_box">
					    				<img src="/images/common/hh.jpg">
					    				
					    				<!-- 레시피 디테일 -->
					    				<div class="recipe_details">
					    				
					    					<!-- 레시피 상단 -->
					    					<div class="recipe_top">
						    					<!-- 레시피 제목 -->
						    					<p>마법의 카레 가루</p>
						    					<!-- 레시피 찜 -->
											    <button class="favorite_btn"></button>
					    					</div>
					    						
						    				<!-- 레시피 정보 -->
											<div class="recipe_info">
												<div class="recipe_info_item">
													<img src="/images/recipe/level.png">
													<p>초급</p>
												</div>
												<div class="recipe_info_item">
													<img src="/images/recipe/time.png">
													<p>40분</p>
												</div>
												<div class="recipe_info_item">
													<img src="/images/recipe/spicy.png">
													<p>매움</p>
												</div>
											</div>
											
											<!-- 레시피 별점 -->
											<div class="recipe_score">
												<!-- 리뷰 별점 -->
												<div class="star">
													<%-- <c:forEach> --%>
														<img src="/images/recipe/star_full.png">
														<img src="/images/recipe/star_full.png">
														<img src="/images/recipe/star_full.png">
														<img src="/images/recipe/star_full.png">
													<%-- </c:forEach> --%>
													<%-- <c:forEach> --%>
														<img src="/images/recipe/star_empty.png">
													<%-- </c:forEach> --%>
												</div>
												<p>4.0 (30)</p>
											</div>
					    				</div>
					    				
					    			</div>
					    		</li>
					    	</ul>
					    	
					    	<!-- 더보기 버튼 -->
							<div class="more_btn">
								<button>
									<span>더보기</span>
									<img src="/images/mypage/arrow_down.png">
								</button>
							</div>
						    
						</div>
						
					</div>
					
					
					
					<!-- 유저 클래스 -->
					<c:if test="${ true }">
						<div class="class_wrap">
							<h2>아잠만님의 클래스</h2>
						</div>
					</c:if>
				
				
				
				</div>
			</div>
		</main>
		
		<%@include file="../common/footer.jsp" %>
	</body>
</html>