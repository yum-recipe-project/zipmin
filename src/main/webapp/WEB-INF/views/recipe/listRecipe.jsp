<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>집밥의민족</title>
		<%@include file="../common/head.jsp" %>
		<link rel="stylesheet" href="/css/recipe/list-recipe.css">
		<script src="/js/recipe/list-recipe.js"></script>
	</head>
	<body>
		<%@include file="../common/header.jsp" %>
		
		<main id="container">
			<div class="content">
			
				<div class="recipe_header">
					<h2>레시피</h2>
					
					<!-- 레시피 카테고리 -->
					<div class="recipe_menu">
						<div class="category">
							<img src="/images/recipe/menu.png">
							<p>카테고리</p>
						</div>
						<div class="tab">
							<a class="btn_tab active" href="">
								<span>전체</span>
							</a>
							<a class="btn_tab" href="">
								<span>아이스크림</span>
							</a>
							<a class="btn_tab" href="">
								<span>과자</span>
							</a>
							<a class="btn_tab" href="">
								<span>음료</span>
							</a>
						</div>
					</div>
					
				</div>
			
				<div class="recipe_content">
				
					<!-- 레시피 정렬 요소 -->
					<div class="recipe_util">
						<p class="total">23개</p>
						<div class="sort_wrap">
							<div class="follower_btn">
								<button class="btn_sort">팔로우만 보기</button>
								<label class="toggle-switch follow_filter">
								    <input type="checkbox">
								    <span class="slider"></span>
								</label>
							</div>
							<div class="recipe_sort">
								<button class="btn_sort active">추천순</button>
								<button class="btn_sort">최신순</button>
								<button class="btn_sort">정확순</button>
							</div>
						</div>
					</div>
					
					<!-- 레시피 목록 -->
					<c:if test="${ true }">
						<ul class="recipe_list">
							<%-- <c:foreach> --%>
								<li class="recipe">
									<a href="/recipe/viewRecipe.do">
										<div class="recipe_thumbnail">
											<img src="/images/common/test.png">
										</div>
										<div class="recipe_info">
											<h5>마법의 카레 가루</h5>
											
											<!-- 레시피 정보 -->
											<div class="recipe_details">
												<div class="details_item">
													<img src="/images/recipe/level.png">
													<p>초급</p>
												</div>
												<div class="details_item">
													<img src="/images/recipe/time.png">
													<p>40분</p>
												</div>
												<div class="details_item">
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
									</a>
								</li>
								
								
								
								<li class="recipe">
									<a href="/chompessor/viewForum.do">
										<div class="recipe_thumbnail">
											<img src="/images/common/test.png">
										</div>
										<div class="recipe_info">
											<h5>마법의 카레 가루</h5>
											
											<!-- 레시피 정보 -->
											<div class="recipe_details">
												<div class="details_item">
													<img src="/images/recipe/level.png">
													<p>초급</p>
												</div>
												<div class="details_item">
													<img src="/images/recipe/time.png">
													<p>40분</p>
												</div>
												<div class="details_item">
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
									</a>
								</li>
								<li class="recipe">
									<a href="/chompessor/viewForum.do">
										<div class="recipe_thumbnail">
											<img src="/images/common/test.png">
										</div>
										<div class="recipe_info">
											<h5>마법의 카레 가루</h5>
											
											<!-- 레시피 정보 -->
											<div class="recipe_details">
												<div class="details_item">
													<img src="/images/recipe/level.png">
													<p>초급</p>
												</div>
												<div class="details_item">
													<img src="/images/recipe/time.png">
													<p>40분</p>
												</div>
												<div class="details_item">
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
									</a>
								</li>
								<li class="recipe">
									<a href="/chompessor/viewForum.do">
										<div class="recipe_thumbnail">
											<img src="/images/common/test.png">
										</div>
										<div class="recipe_info">
											<h5>마법의 카레 가루</h5>
											
											<!-- 레시피 정보 -->
											<div class="recipe_details">
												<div class="details_item">
													<img src="/images/recipe/level.png">
													<p>초급</p>
												</div>
												<div class="details_item">
													<img src="/images/recipe/time.png">
													<p>40분</p>
												</div>
												<div class="details_item">
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
									</a>
								</li>
								<li class="recipe">
									<a href="/chompessor/viewForum.do">
										<div class="recipe_thumbnail">
											<img src="/images/common/test.png">
										</div>
										<div class="recipe_info">
											<h5>마법의 카레 가루</h5>
											
											<!-- 레시피 정보 -->
											<div class="recipe_details">
												<div class="details_item">
													<img src="/images/recipe/level.png">
													<p>초급</p>
												</div>
												<div class="details_item">
													<img src="/images/recipe/time.png">
													<p>40분</p>
												</div>
												<div class="details_item">
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
									</a>
								</li>
								<li class="recipe">
									<a href="/chompessor/viewForum.do">
										<div class="recipe_thumbnail">
											<img src="/images/common/test.png">
										</div>
										<div class="recipe_info">
											<h5>마법의 카레 가루</h5>
											
											<!-- 레시피 정보 -->
											<div class="recipe_details">
												<div class="details_item">
													<img src="/images/recipe/level.png">
													<p>초급</p>
												</div>
												<div class="details_item">
													<img src="/images/recipe/time.png">
													<p>40분</p>
												</div>
												<div class="details_item">
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
									</a>
								</li>
								
							<%-- </c:foreach> --%>
						</ul>
					</c:if>
					
					
				
				</div>
				
				<!-- 더보기 버튼 -->
				<div class="more_wrap">
					<button class="btn_more">
						<span>레시피 더보기</span>
						<img src="/images/mypage/arrow_down.png">
					</button>
				</div>
				
			</div>
		</main>
		
		<%@include file="../common/footer.jsp" %>
	</body>
</html>