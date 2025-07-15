<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>집밥의민족</title>
		<%@include file="../common/head.jsp" %>
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.css"/>
		<script src="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.js"></script>

		<link rel="stylesheet" href="/css/fridge/view-fridge.css">
		<script src="/js/fridge/view-fridge.js"></script>
		<script src="/js/modal/add-ingredient-modal.js"></script>
	</head>
	
	<body>
		<%@include file="../common/header.jsp" %>
		
		<main id="container">
			<div class="content">
				
				<!-- 냉장고 -->
				<div class="fridge_wrap">
					<div class="fridge_header">
						<h2>나의 냉장고</h2>
					</div>
					
					<div class="fridge_content">
					
						<div id="ingredient_swiper_container" class="fridge"></div>
						
						<button class="btn_primary" data-bs-toggle="modal" data-bs-target="#addIngredientModal">냉장고 채우기</button>

						
								
						<!-- 재료 표 -->
						<table class="ingredient_list">
						    <thead>
						        <tr>
						            <th width="330px">재료</th>
						            <th width="216px">용량</th>
						            <th width="216px">소비기한</th>
						            <th width="122px">종류</th>
						            <th width="60px"></th>
						        </tr>
						    </thead>
						    <tbody class="ingredient_body"></tbody>
						</table>
						
					</div>
				</div>
				
				<!-- 냉장고 파먹기 -->
				<div class="clear_wrap">
					<div class="clear_header">
						<h2>냉장고 파먹기</h2>
					</div>
					
					<div class="clear_content">
					
						<!-- 레시피 추천 -->
						<div class="clear_recipe">
							<p class="pick">냉장고 속 재료로 만들 수 있는 요리</p>
							<ul>
								<li>
									<div class="recipe">
										<span>1</span>
										<p>김치볶음밥</p>
										<p class="flag">90% 일치</p>
									</div>
								</li>
								
								<li>
									<div class="recipe">
										<span>2</span>
										<p>김치볶음밥</p>
									</div>
								</li>
								<li>
									<div class="recipe">
										<span>3</span>
										<p>김치볶음밥</p>
									</div>
								</li>
								<li>
									<div class="recipe">
										<span>4</span>
										<p> 김치볶음밥</p>
									</div>
								</li>
								<li>
									<div class="recipe">
										<span>5</span>
										<p> 김치볶음밥</p>
									</div>
								</li>
								
							</ul>
						</div>
					</div>
				</div>
				
				
			</div>
			
			<!-- 냉장고 채우기 모달 -->
			<%@include file="../modal/addIngredientModal.jsp" %>
		
		</main>
		
		
		<%@include file="../common/footer.jsp" %>
	</body>
</html>