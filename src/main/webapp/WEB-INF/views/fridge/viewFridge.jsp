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
					
						<div class="fridge">
						
							<!-- 육류 -->
							<div class="ingredient_swiper">
								<div class="category">
									<div class="ingredient_img">
										<img src="/images/fridge/meat.png">
									</div>
									<span>육류</span>
								</div>
								
								<div class="swiper">
								  <div class="swiper-wrapper">
								  	<div class="swiper-slide">
								    	<div class="ingredient">
											<div class="ingredient_img">
												<img src="/images/fridge/chicken.png">
											</div>
											<span>닭가슴살</span>
											<p>2025.03.27</p>
										</div>
								    </div>
								    <div class="swiper-slide">
								    	<div class="ingredient">
											<div class="ingredient_img">
												<img src="/images/fridge/pig.png">
											</div>
											<span>닭가슴살</span>
											<p>2025.03.27</p>
										</div>
								    </div>
								    <div class="swiper-slide">
								    	<div class="ingredient">
											<div class="ingredient_img">
												<img src="/images/fridge/cow.png">
											</div>
											<span>닭가슴살</span>
											<p>2025.03.27</p>
										</div>
								    </div>
								    <div class="swiper-slide">
								    	<div class="ingredient">
											<div class="ingredient_img">
												<img src="/images/fridge/chicken.png">
											</div>
											<span>닭가슴살</span>
											<p>2025.03.27</p>
										</div>
								    </div>
								    <div class="swiper-slide">
								    	<div class="ingredient">
											<div class="ingredient_img">
												<img src="/images/fridge/pig.png">
											</div>
											<span>닭가슴살</span>
											<p>2025.03.27</p>
										</div>
								    </div>
								    <div class="swiper-slide">
								    	<div class="ingredient">
											<div class="ingredient_img">
												<img src="/images/fridge/cow.png">
											</div>
											<span>닭가슴살</span>
											<p>2025.03.27</p>
										</div>
								    </div>
								    <div class="swiper-slide">
								    	<div class="ingredient">
											<div class="ingredient_img">
												<img src="/images/fridge/chicken.png">
											</div>
											<span>닭가슴살</span>
											<p>2025.03.27</p>
										</div>
								    </div>
								  </div>
								</div>
							</div>
							
							<!-- 채소류 -->
							<div class="ingredient_swiper">
								<div class="category">
									<div class="ingredient_img">
										<img src="/images/fridge/vegetables.png">
									</div>
									<span>채소류</span>
								</div>
								
								<div class="swiper">
								  <div class="swiper-wrapper">
								  	<div class="swiper-slide">
								    	<div class="ingredient">
											<div class="ingredient_img">
												<img src="/images/fridge/grass.png">
											</div>
											<span>상추</span>
											<p>2025.03.27</p>
										</div>
								    </div>
								    <div class="swiper-slide">
								    	<div class="ingredient">
											<div class="ingredient_img">
												<img src="/images/fridge/carrot.png">
											</div>
											<span>당근</span>
											<p>2025.03.27</p>
										</div>
								    </div>
								    <div class="swiper-slide">
								    	<div class="ingredient">
											<div class="ingredient_img">
												<img src="/images/fridge/onion.png">
											</div>
											<span>양파</span>
											<p>2025.03.27</p>
										</div>
								    </div>
								    <div class="swiper-slide">
								    	<div class="ingredient">
											<div class="ingredient_img">
												<img src="/images/fridge/eggplant.png">
											</div>
											<span>가지</span>
											<p>2025.03.27</p>
										</div>
								    </div>
								    <div class="swiper-slide">
								    	<div class="ingredient">
											<div class="ingredient_img">
												<img src="/images/fridge/grass.png">
											</div>
											<span>상추</span>
											<p>2025.03.27</p>
										</div>
								    </div>
								    <div class="swiper-slide">
								    	<div class="ingredient">
											<div class="ingredient_img">
												<img src="/images/fridge/carrot.png">
											</div>
											<span>당근</span>
											<p>2025.03.27</p>
										</div>
								    </div>
								    <div class="swiper-slide">
								    	<div class="ingredient">
											<div class="ingredient_img">
												<img src="/images/fridge/onion.png">
											</div>
											<span>양파</span>
											<p>2025.03.27</p>
										</div>
								    </div>
								    <div class="swiper-slide">
								    	<div class="ingredient">
											<div class="ingredient_img">
												<img src="/images/fridge/eggplant.png">
											</div>
											<span>가지</span>
											<p>2025.03.27</p>
										</div>
								    </div>
								  </div>
								</div>
							</div>
							
							<!-- 소스류 -->
							<div class="ingredient_swiper">
								<div class="category">
									<div class="ingredient_img">
										<img src="/images/fridge/sauce.png">
									</div>
									<span>소스류</span>
								</div>
								
								<div class="swiper">
								  <div class="swiper-wrapper">
								  	<div class="swiper-slide">
								    	<div class="ingredient">
											<div class="ingredient_img">
												<img src="/images/fridge/ketchup.png">
											</div>
											<span>케찹</span>
											<p>2025.03.27</p>
										</div>
								    </div>
								    <div class="swiper-slide">
								    	<div class="ingredient">
											<div class="ingredient_img">
												<img src="/images/fridge/soysauce.png">
											</div>
											<span>간장</span>
											<p>2025.03.27</p>
										</div>
								    </div>
								    <div class="swiper-slide">
								    	<div class="ingredient">
											<div class="ingredient_img">
												<img src="/images/fridge/mayonnaise.png">
											</div>
											<span>마요네즈</span>
											<p>2025.03.27</p>
										</div>
								    </div>
								    <div class="swiper-slide">
								    	<div class="ingredient">
											<div class="ingredient_img">
												<img src="/images/fridge/chili.png">
											</div>
											<span>타바스코</span>
											<p>2025.03.27</p>
										</div>
								    </div>
								    <div class="swiper-slide">
								    	<div class="ingredient">
											<div class="ingredient_img">
												<img src="/images/fridge/ketchup.png">
											</div>
											<span>케찹</span>
											<p>2025.03.27</p>
										</div>
								    </div>
								    <div class="swiper-slide">
								    	<div class="ingredient">
											<div class="ingredient_img">
												<img src="/images/fridge/soysauce.png">
											</div>
											<span>간장</span>
											<p>2025.03.27</p>
										</div>
								    </div>
								    <div class="swiper-slide">
								    	<div class="ingredient">
											<div class="ingredient_img">
												<img src="/images/fridge/mayonnaise.png">
											</div>
											<span>마요네즈</span>
											<p>2025.03.27</p>
										</div>
								    </div>
								    <div class="swiper-slide">
								    	<div class="ingredient">
											<div class="ingredient_img">
												<img src="/images/fridge/chili.png">
											</div>
											<span>타바스코</span>
											<p>2025.03.27</p>
										</div>
								    </div>
								  </div>
								</div>
							</div>
						</div>
						
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
						    <tbody>
						        <tr>
						            <td>마라탕 소스</td>
						            <td>100g</td>
						            <td>2025.01.02</td>
						            <td>육류</td>
						            <td>
						            	<button class="delete_btn">
						            		<img src="/images/fridge/close.png">
						            	</button>
						            </td> 
						        </tr>
						        <tr>
						            <td>상하이 목장 치즈</td>
						            <td>10ea</td>
						            <td>2025.01.02</td>
						            <td>채소류</td>
						             <td>
						            	<button class="delete_btn">
						            		<img src="/images/fridge/close.png">
						            	</button>
						            </td>
						        </tr>
						        <tr>
						            <td>제로콜라</td>
						            <td>2L</td>
						            <td>2025.01.02</td>
						            <td>소스류</td>
						             <td>
						            	<button class="delete_btn">
						            		<img src="/images/fridge/close.png">
						            	</button>
						            </td>
						        </tr>
						    </tbody>
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