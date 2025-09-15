<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>집밥의민족</title>
		<%@include file="../common/head.jsp" %>
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.css"/>
		<link rel="stylesheet" href="/css/fridge/view-fridge.css">
		<script src="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.js"></script>
		<script src="/js/fridge/view-fridge.js"></script>
		<script src="/js/modal/write-fridge-modal.js"></script>
		<script src="/js/modal/write-user-fridge-modal.js"></script>
		<script src="/js/modal/edit-user-fridge-modal.js"></script>
	</head>
	
	<body>
		<%@include file="../common/header.jsp" %>
		
		<main id="container">
			<div class="content">
				
				<!-- 냉장고 -->
				<div class="fridge_wrap">
					<div class="fridge_header">
						<h2>나의 냉장고</h2>
						<button type="button" class="btn btn_tab" data-bs-toggle="modal" data-bs-target="#writeUserFridgeModal">냉장고 채우기</button>
					</div>
					<div class="fridge_content">
						<div class="fridge_subtitle">
							<h3></h3>
							<div class="fridge_zone">
								<p>냉장</p>
								<p>냉동</p>
								<p>실온</p>
							</div>
						</div>
						<ul class="fridge_list"></ul>
					</div>
				</div>
							
				<div class="memo_wrap">
				
					<div class="memo_header">
						<h2>장보기 메모</h2>
					</div>
				
					<div class="memo_content">
					
						<!-- 재료 표 -->
						<table class="ingredient_list">
						    <thead>
						        <tr>
						            <th width="472px">재료</th>
						            <th width="472px">용량</th>
						            <th width="60px"></th>
						        </tr>
						    </thead>
						    <tbody>
						        <tr>
						            <td>마라탕 소스</td>
						            <td>100g</td>
						            <td>
						            	<button class="delete_btn">
						            		<img src="/images/fridge/close.png">
						            	</button>
						            </td> 
						        </tr>
						        <tr>
						            <td>상하이 목장 치즈</td>
						            <td>10ea</td>
						             <td>
						            	<button class="delete_btn">
						            		<img src="/images/fridge/close.png">
						            	</button>
						            </td>
						        </tr>
						        <tr>
						            <td>제로콜라</td>
						            <td>2L</td>
						             <td>
						            	<button class="delete_btn">
						            		<img src="/images/fridge/close.png">
						            	</button>
						            </td>
						        </tr>
						    </tbody>
						</table>
						<div class="btn_wrap">
							<button class="btn_outline" onclick="location.href='/chompessor/listForum.do';">장보기 완료</button>
							<button class="btn_primary" type="submit" onclick="">추가하기</button>
						</div>
						
					</div>
					
				</div>
			
				
				<!-- 냉장고 파먹기 -->
				<div class="pick_wrap">
					<div class="pick_header">
						<h2>냉장고 파먹기</h2>
					</div>
					<div class="pick_content">
						<!-- 레시피 추천 -->
						<div class="pick_recipe">
							<p class="pick_title">냉장고 속 재료로 만들 수 있는 요리</p>
							<ul class="pick_list"></ul>
						</div>
					</div>
				</div>
				
				<!-- 내 주변 마트 -->
				<div class="mart_wrap">
					<div class="mart_header">
						<h2>내 주변 마트</h2>
						<div class="btn_wrap">
							<button class="btn_icon"><img src="/images/fridge/arrow_back.png" class="left"></button>
							<button class="btn_icon"><img src="/images/fridge/arrow_forward.png" class="right"></button>
						</div>
					</div>
					<div class="mart_content">
						<!-- 지도 -->
						<div class="map">지도</div>
						<!-- 마트 목록 -->
						<ul class="mart_list">
							<li>
								<div class="mart_box">
									<div class="mart_txt">
										<span>이마트</span>
										<div class="ping">
											<img src="/images/fridge/location.png">
											<p>0.8km</p>
										</div>
									</div>
								</div>
							</li>
							<li>
								<div class="mart_box">
									<div class="mart_txt">
										<span>이마트</span>
										<div class="ping">
											<img src="/images/fridge/location.png">
											<p>0.8km</p>
										</div>
									</div>
								</div>
							</li>
						</ul>
					</div>
				</div>
				

			
				
			</div>
			
			<!-- 냉장고 채우기 모달 -->
			<%@include file="../modal/writeFridgeModal.jsp" %>
			<%@include file="../modal/writeUserFridgeModal.jsp" %>
			<%@include file="../modal/editUserFridgeModal.jsp" %>
		
		</main>
		
		
		<%@include file="../common/footer.jsp" %>
	</body>
</html>