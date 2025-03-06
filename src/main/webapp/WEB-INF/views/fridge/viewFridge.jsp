<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>집밥의민족</title>
		<%@include file="../common/head.jsp" %>
		<link rel="stylesheet" href="/css/fridge/view-fridge.css">
		<script src="/js/fridge/view-fridge.js"></script>
	</head>
	
	<body>
		<%@include file="../common/header.jsp" %>
		
		<main id="container">
			<div class="content">
				
				<div class="fridge_wrap">
					<div class="fridge_header">
						<h2>나의 냉장고</h2>
					</div>
					
					<div class="fridge_content">
					
						<div class="fridge">
							냉장고~~
						</div>
						
						<button class="btn_primary">냉장고 채우기</button>
						
						<!-- 재료 표 -->
						<table class="ingredient_list">
						    <thead>
						        <tr>
						            <th width="452px">재료</th>
						            <th width="216px">용량</th>
						            <th width="216px">소비기한</th>
						            <th width="60px"></th>
						        </tr>
						    </thead>
						    <tbody>
						        <tr>
						            <td>마라탕 소스</td>
						            <td>100g</td>
						            <td>2025.01.02</td>
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
		</main>
		
		<%@include file="../common/footer.jsp" %>
	</body>
</html>