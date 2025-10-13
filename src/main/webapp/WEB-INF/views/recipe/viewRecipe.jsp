<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>집밥의민족</title>
		<%@include file="../common/head.jsp" %>
		<link rel="stylesheet" href="/css/recipe/view-recipe.css">
		<link rel="stylesheet" href="/css/common/comment.css">
		<link rel="stylesheet" href="/css/common/review.css">
		<script src="/js/recipe/view-recipe.js"></script>
		<script src="/js/modal/report-recipe-modal.js"></script>
		<script src="/js/modal/view-recipe-stock-modal.js"></script>
		<script src="/js/modal/support-recipe-modal.js"></script>
		<script src="/js/modal/top-up-point-modal.js"></script>
		
		
		<script src="/js/common/comment.js"></script>
		<script src="/js/common/review.js"></script>
	</head>
	<body>
		<%@include file="../common/header.jsp" %>
		
		<main id="container">
			<div class="content">
				<div class="recipe_wrap">
					<div id="viewRecipeBasicWrap" class="recipe_header">
						<!-- 제목 -->
						<h2 class="recipe_title"></h2>
						
						<!-- 유틸 버튼 -->
						<div class="btn_wrap">
							<button class="btn_icon like">
								<img src="/images/recipe/star_empty_181a1c.png">
							</button>
							<button class="btn_icon print" onclick="window.print();">
								<img src="/images/recipe/print.png">
							</button>
							<button class="btn_icon btn_report_modal" data-bs-toggle="modal" data-bs-target="#reportRecipeModal">
								<img src="/images/recipe/siren.png">
							</button>
						</div>
						
						<!-- 레시피 정보 -->
						<div class="recipe_info">
							<div class="info">
								<img src="/images/recipe/level.png">
								<p class="recipe_cooklevel"></p>
							</div>
							<div class="info">
								<img src="/images/recipe/time.png">
								<p class="recipe_cooktime"></p>
							</div>
							<div class="info">
								<img src="/images/recipe/spicy.png">
								<p class="recipe_spicy"></p>
							</div>
						</div>
						
						<!-- 작성자 -->
						<div class="recipe_writer">
							<button type="button" class="btn_nickname">
								<img src="/images/common/test.png">
								<span>알 수 없는 사용자</span>
							</button>
							<button type="button" class="btn_text">팔로우</button>
						</div>
						
						<!-- 소개 -->
						<p class="recipe_introduce"></p>
						
						<!-- 카테고리 -->
						<div class="recipe_category"></div>
					</div>
					
					<div id="viewRecipeStockWrap" class="recipe_stock">
						<h3>재료</h3>
						
						<!-- 장보기메모에 재료 담기 버튼 -->
						<button class="btn_stock_modal btn_tool" data-bs-toggle="modal" data-bs-target="#viewRecipeStockModal">
							<img src="/images/recipe/pen.png"> 장보기메모에 재료 담기
						</button>
						
						<!-- 양 -->
						<div class="recipe_portion">
							<select>
								<option value="1인분">1인분</option>
								<option value="2인분">2인분</option>
								<option value="3인분">3인분</option>
								<option value="4인분">4인분</option>
								<option value="5인분">5인분</option>
							</select>
							<p>레시피의 용량을 단순히 인분에 맞춰 계산한 것이므로, 실제 조리 시 정확하지 않을 수 있습니다.</p>
						</div>
						
						<!-- 재료 표 -->
						<table class="stock_list">
							<thead>
								<tr>
									<th width="33%">재료</th>
									<th width="33%">용량</th>
									<th width="33%">비고</th>
								</tr>
							</thead>
							<tbody class="stock"></tbody>
						</table>
					</div>
					
					<div id="viewRecipeStepWrap" class="recipe_step">
						<!-- 제목 -->
						<h3>조리 순서</h3>
						
						<!-- 장보기메모에 재료 담기 버튼 -->
						<button id="togglePhotoButton" class="btn_tool"><img src="/images/recipe/photo_999.png">사진 숨기기</button>
						
						<!-- 조리 과정 목록 -->
						<ul class="step_list"></ul>
					</div>
					
					<!-- 요리팁 -->
					<div id="viewRecipeTipWrap">
						<div class="recipe_tip">
							<h3>주의사항</h3>
							<p></p>
						</div>
					</div>
					
					<!-- 후원 -->
					<div id="viewRecipeSupportWrap" class="recipe_support">
						<div class="recipe_writer">
							<img src="/images/common/test.png">
							<div>
								<h5>알 수 없는 사용자</h5>
								<p>구독자 0명</p>
							</div>
						</div>
						<div class="btn_wrap">
							<button type="button" class="btn_outline" data-bs-toggle="modal" data-bs-target="#supportRecipeModal">레시피 후원하기</button>
							<button type="button" class="btn_dark" onclick="javascript:redirectToLogin();" >구독</button>
						</div>
					</div>
				</div>
				
				<!-- 탭 메뉴 버튼 -->
				<div id="viewRecipeReviewCommentWrap" class="tab_button_wrap">
					<div class="tab_button">
						<button class="active">리뷰 (<em class="review_count" data-id="1"></em>)</button>
						<button>댓글 (<em class="comment_count" data-id="1"></em>)</button>
					</div>
				</div>
				
				<!-- 리뷰 -->
				<div class="tab_content">
					<%@include file="../common/review.jsp" %>
				</div>
				
				<!-- 댓글 -->
				<div class="tab_content">
					<%@include file="../common/comment.jsp" %>
				</div>
			</div>
			
			<!-- 레시피 신고 모달창 -->
			<%@include file="../modal/reportRecipeModal.jsp" %>
			
			<!-- 장보기메모에 담기 모달창 -->
			<%@include file="../modal/viewRecipeStockModal.jsp" %>
			
			<!-- 후원 모달창 -->
			<%@include file="../modal/supportRecipeModal.jsp" %>
			
			<!-- 포인트 충전 -->
			<%@include file="../modal/topUpPointModal.jsp" %>
			
			<!-- 댓글 수정 모달창 -->
			<%@include file="../modal/editCommentModal.jsp" %>
			
			<!-- 댓글 신고 모달창 -->
			<%@include file="../modal/reportCommentModal.jsp" %>
			
			<!-- 대댓글 작성 모달창 -->
			<%@include file="../modal/writeSubcommentModal.jsp" %>
			
			<!-- 리뷰 수정 모달창 -->
			<%@include file="../modal/editReviewModal.jsp" %>
			
			<!-- 리뷰 신고 모달창 -->
			<%@include file="../modal/reportReviewModal.jsp" %>
		</main>
		
		<%@include file="../common/footer.jsp" %>
	</body>
</html>