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
		<script src="/js/recipe/view-recipe.js"></script>
		<script src="/js/modal/report-recipe-modal.js"></script>
		<script src="/js/modal/add-memo-modal.js"></script>
		<script src="/js/modal/support-recipe-modal.js"></script>
		<script src="/js/modal/top-up-point-modal.js"></script>
	</head>
	<body>
		<%@include file="../common/header.jsp" %>
		
		<main id="container">
			<div class="content">
				<div class="recipe_wrap">
					<div class="recipe_header">
						<!-- 제목 -->
						<h2 id="title"></h2>
						
						<!-- 스크랩 버튼 -->
						<div class="save_recipe_btn">
							<button class="btn_tool" onclick="">
								<img src="/images/recipe/bookmark.png"> 저장
							</button>
						</div>
						
						<!-- 레시피 정보 -->
						<div class="recipe_info">
							<div class="recipe_info_item">
								<img src="/images/recipe/level.png">
								<p id="level"></p>
							</div>
							<div class="recipe_info_item">
								<img src="/images/recipe/time.png">
								<p id="time"></p>
							</div>
							<div class="recipe_info_item">
								<img src="/images/recipe/spicy.png">
								<p id="spicy"></p>
							</div>
						</div>
						
						<!-- 작성자 -->
						<div class="recipe_writer">
							<a class="nickname" href="">
								<img src="/images/common/test.png">
								<span class="nickname" data-id="1"></span>
							</a>
							<c:if test="${ true }">
								<a href="">팔로우</a>
							</c:if>
						</div>
						
						<!-- 소개 -->
						<p id="introduce" class="recipe_introduce"></p>
						
						<!-- 카테고리 -->
						<div id="category" class="recipe_category">
						</div>
						
						<!-- 버튼 -->
						<div class="btn_wrap">
							<button class="btn_icon" data-bs-toggle="modal" data-bs-target="#reportRecipeModal"
								onclick="openReportRecipeModal();">
								<img src="/images/recipe/siren.png">
							</button>
							<button class="btn_icon" onclick="">
								<img src="/images/recipe/youtube.png">
							</button>
							<button class="btn_icon print" onclick="window.print();">
								<img src="/images/recipe/print.png">
							</button>
						</div>
					</div>
					
					<div class="recipe_ingredient">
						<!-- 제목 -->
						<h3>재료</h3>
						<!-- 장보기메모에 재료 담기 버튼 -->
						<div class="save_ingredient_btn">
							<button class="btn_tool" data-bs-toggle="modal" data-bs-target="#addMemoModal"
								onclick="">
								<img src="/images/recipe/pen.png"> 장보기메모에 재료 담기
							</button>
						</div>
						
						<!-- 양 -->
						<div class="recipe_serving">
							<select id="servingInput" name="">
								<option value="1">1인분</option>
								<option value="2">2인분</option>
								<option value="3">3인분</option>
								<option value="4">4인분</option>
								<option value="5">5인분</option>
							</select>
							<p>레시피의 용량을 단순히 인분에 맞춰 계산한 것이므로, 실제 조리 시 정확하지 않을 수 있습니다.</p>
						</div>
						
						<!-- 재료 표 -->
						<table class="ingredient_list">
							<thead>
								<tr>
									<th width="25%">재료</th>
									<th width="25%">용량</th>
									<th width="50%">비고</th>
								</tr>
							</thead>
							<tbody id="ingredient"></tbody>
						</table>
					</div>
					
					<div class="recipe_step">
						<!-- 제목 -->
						<h3>조리 순서</h3>
						<ul class="step_list" id="step"></ul>
					</div>
					
					<!-- 요리팁 -->
					<div class="recipe_tip">
						<h3>주의사항</h3>
						<p id="tip"></p>
					</div>
					
					<!-- 구독 및 후원 -->
					<div class="recipe_util">
						<div class="profile">
							<img src="/images/common/test.png">
							<div>
								<h5 class="nickname" data-id="2"></h5>
								<p>구독자 <em id="follower">45</em>명</p>
							</div>
						</div>
						<div class="btn_wrap">
							<button class="btn_outline" type="button" data-bs-toggle="modal" data-bs-target="#supportRecipeModal"
								onclick="openSupportRecipeModal();">
								레시피 후원하기
							</button>
							
							<button id="followButton" class="btn_dark" type="submit" onclick="">구독</button>
						</div>
					</div>
				</div>
				
				<!-- 탭 메뉴 버튼 -->
				<div class="tab_button_wrap">
					<div class="tab_button">
						<button class="active">리뷰 (<em class="review_count" data-id="1"></em>)</button>
						<button>댓글 (<em class="comment_count" data-id="1"></em>)</button>
					</div>
				</div>
				
				<!-- 리뷰 -->
				<div class="tab_content">
					<div class="review_wrap">
						<!-- 리뷰 헤더 -->
						<div class="review_header">
							<div class="review_count">
								<span>리뷰</span>
								<span class="review_count" data-id="2"></span>
							</div>
							<div class="review_order">
								<button class="btn_sort_small active">최신순</button>
								<button class="btn_sort_small">오래된순</button>
								<button class="btn_sort_small">도움순</button>
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
										<img src="/images/common/test.png">
										<span class="nickname" data-id="3"></span>
									</div>
									<!-- 별점 -->
									<div id="starGroup" class="star_group">
										<img src="/images/recipe/star_full.png" class="star" data-value="1">
										<img src="/images/recipe/star_outline.png" class="star" data-value="2">
										<img src="/images/recipe/star_outline.png" class="star" data-value="3">
										<img src="/images/recipe/star_outline.png" class="star" data-value="4">
										<img src="/images/recipe/star_outline.png" class="star" data-value="5">
										<input type="hidden" id="starInput" name="rating" value="1">
									</div>
									<!-- 리뷰 입력창 -->
									<div class="review_input">
										<textarea id="reviewInput" rows="2" maxlength="400" placeholder="욕설, 비방, 허위 정보 및 부적절한 댓글은 사전 경고 없이 삭제될 수 있습니다."></textarea>
										<span>400</span>
									</div>
									<div class="write_btn">
										<button class="btn_primary disable" type="submit" id="reviewButton" onclick="" disabled>작성하기</button>
									</div>
								</form>
							</c:if>
						</div>
						<!-- 리뷰 목록 -->
						<div id="recipeReviewContent">
							<ul id="reviewList" class="review_list"></ul>
						</div>
						
						<!-- 더보기 버튼 -->
						<div class="more_review_btn">
							<button class="btn_more">
								<span>더보기</span>
								<img src="/images/recipe/arrow_down_black.png">
							</button>
						</div>
					</div>
				</div>
				
				<!-- 댓글 -->
				<div class="tab_content">
					<div class="comment_wrap">
						<!-- 댓글 헤더 -->
						<div class="comment_header">
							<div class="comment_count">
								<span>댓글</span>
								<span class="comment_count" data-id="2">7</span>
							</div>
							<div class="comment_order">
								<button class="btn_sort_small active">최신순</button>
								<button class="btn_sort_small">오래된순</button>
								<button class="btn_sort_small">인기순</button>
							</div>
						</div>
						<!-- 댓글 작성 -->
						<div class="comment_write">
							<!-- 로그인 하지 않은 경우 -->
							<c:if test="${ false }">
								<a href="/member/login.do">
									<span>댓글 작성을 위해 로그인을 해주세요.</span>
									<span>400</span>
								</a>
							</c:if>
							<!-- 로그인 한 경우 -->
							<c:if test="${ true }">
								<form>
									<div class="login_user">
										<img src="/images/common/test.png">
										<span class="nickname" data-id="4"></span>
									</div>
									<div class="comment_input">
										<textarea id="commentInput" rows="2" maxlength="400" placeholder="욕설, 비방, 허위 정보 및 부적절한 댓글은 사전 경고 없이 삭제될 수 있습니다."></textarea>
										<span>400</span>
									</div>
									<div class="write_btn">
										<button class="btn_primary disable" type="submit" id="commentButton" onclick="" disabled>작성하기</button>
									</div>
								</form>
							</c:if>
						</div>
						<!-- 댓글 목록 -->
						<div id="recipeCommentContent"></div>
						<!-- 댓글 더보기 버튼 -->
						<div class="more_comment_btn">
							<button class="btn_more">
								<span>더보기</span>
								<img src="/images/recipe/arrow_down_black.png">
							</button>
						</div>
					</div>
				</div>
			</div>
			
			<!-- 레시피 신고 모달창 -->
			<%@include file="../modal/reportRecipeModal.jsp" %>
			
			<!-- 장보기메모에 담기 모달창 -->
			<%@include file="../modal/addMemoModal.jsp" %>
			
			<!-- 후원 모달창 -->
			<%@include file="../modal/supportRecipeModal.jsp" %>
			
			<!-- 포인트 충전 -->
			<%@include file="../modal/topUpPointModal.jsp" %>
		</main>
		
		<%@include file="../common/footer.jsp" %>
	</body>
</html>