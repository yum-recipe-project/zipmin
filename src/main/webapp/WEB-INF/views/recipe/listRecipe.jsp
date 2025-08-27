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
					
					<!-- 레시피 작성 -->
					<a href="/recipe/writeRecipe.do" class="btn_block">
						<span>맛있는 한 끼를 위한 <b>레시피 작성하기</b></span>
						<img src="/images/mypage/arrow_right.png">
					</a>
					
					<!-- 레시피 카테고리 -->
					<div class="category_menu">
						<div class="category_title">
							<div class="category_type">
								<img src="/images/recipe/menu.png">
								<p>카테고리</p>
							</div>
							<div class="divider"></div>
							<div class="tab">
								<a class="btn_tab active" href="">
									<span>전체</span>
								</a>
							</div>
							<!-- 레시피 정렬 요소 -->
							<div class="sort_wrap">
								<div class="recipe_sort">
									<button class="btn_sort active" data-sort="postdate-desc">최신순</button>
									<button class="btn_sort" data-sort="likecount-desc">인기순</button>
									<button class="btn_sort" data-sort="reviewscore-desc">별점순</button>
								</div>
							</div>
						</div>
						<div class="category_content">
							<div class="category_content_list">
								<div class="category_type">
									<p>종류별</p>
								</div>
								<div class="divider"></div>
								<ul>
									<li>밑반찬</li>
									<li>메인반찬</li>
									<li>국/탕</li>
									<li>찌개</li>
									<li>디저트</li>
									<li>면/만두</li>
									<li>밥/죽/떡</li>
									<li>퓨전</li>
									<li>김치/젓갈/장류</li>
									<li>양념/소스/잼</li>
									<li>양식</li>
									<li>중식</li>
									<li>샐러드</li>
									<li>스프</li>
									<li>빵</li>
									<li>과자</li>
									<li>차/음료/술</li>
									<li>기타</li>
								</ul>
							</div>
							<div class="category_content_list">
								<div class="category_type">
									<p>상황별</p>
								</div>
								<div class="divider"></div>
								<ul>
									<li>일상</li>
									<li>메인반찬</li>
									<li>초스피드</li>
									<li>손님접대</li>
									<li>술안주</li>
									<li>다이어트</li>
									<li>축하</li>
									<li>도시락</li>
									<li>영양식</li>
									<li>간식</li>
									<li>야식</li>
									<li>푸드스타일링</li>
									<li>해장</li>
									<li>명절</li>
									<li>이유식</li>
									<li>기타</li>
								</ul>
							</div>
							<div class="category_content_list">
								<div class="category_type">
									<p>재료별</p>
								</div>
								<div class="divider"></div>
								<ul>
									<li>소고기</li>
									<li>돼지고기</li>
									<li>닭고기</li>
									<li>육류</li>
									<li>채소류</li>
									<li>해물류</li>
									<li>달걀/유제품</li>
									<li>가공식품류</li>
									<li>쌀</li>
									<li>밀가루</li>
									<li>건어물류</li>
									<li>버섯류</li>
									<li>과일류</li>
									<li>콩/견과류</li>
									<li>곡류</li>
									<li>기타</li>
								</ul>
							</div>
							<div class="category_content_list">
								<div class="category_type">
									<p>방법별</p>
								</div>
								<div class="divider"></div>
								<ul>
									<li>볶음</li>
									<li>끓이기</li>
									<li>부침</li>
									<li>조림</li>
									<li>비빔</li>
									<li>찜</li>
									<li>절임</li>
									<li>튀김</li>
									<li>삶기</li>
									<li>굽기</li>
									<li>데치기</li>
									<li>회</li>
									<li>기타</li>
								</ul>
							</div>
						</div>
					</div>
				</div>
			
				<div class="recipe_content">
				
					<!-- 레시피 정렬 요소 -->
					<div class="recipe_util">
						<p class="total">23개</p>
					</div>
					
					<!-- 레시피 목록 -->
					<ul class="recipe_list">
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
					</ul>
					
					
				
				</div>
				
				<!-- 페이지네이션 -->
				<div class="pagination_wrap">
					<div class="pagination">
					    <ul></ul>
					</div>
				</div>
			</div>
		</main>
		
		<%@include file="../common/footer.jsp" %>
	</body>
</html>