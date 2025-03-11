<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>집밥의민족</title>
		<%@include file="../common/head.jsp" %>
		<link rel="stylesheet" href="/css/mypage/saved-recipe.css">
		<script src="/js/mypage/saved-recipe.js"></script>
	</head>
	<body>
		<%@include file="../common/header.jsp" %>
		
		<main id="container">
			<div class="content">
				<div class="recipe_wrap">
				
					<!-- 레시피 제목 -->
					<div class="recipe_title">
						<a href="/mypage.do">
							<span>
								마이페이지
								<img src="/images/mypage/arrow_right.png">
							</span>
						</a>
						<h2>저장한 레시피</h2>
					</div>
					
					<!-- 레시피 목록 상단 -->
					<div class="recipe_etc">
						<!-- 게시글 수 -->
						<div class="recipe_count">
							<span>2개</span>
						</div>
						
						<!-- 게시글 진행상태 -->
						<ul class="recipe_tap">
							<li class="all active">전체</li>
							<li class="latest ">최신순</li>
							<li class="like">추천순</li>
						</ul>
					</div>
					
					<!-- 레시피 목록 -->
					<div class="tab_content">
					    <!-- 전체 게시글 -->
					    <div class="recipe_content all active">
					    	<ul class="recipe_all">
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
					    </div>
					    
					    <div class="recipe_content latest">
					        <p>최신순 게시글 내용</p>
					    </div>
					    
					    <div class="recipe_content like">
					        <p>추천순 게시글 내용</p>
					    </div>
					    
					</div>
					
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