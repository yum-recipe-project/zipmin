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
		<script src="/js/support/support-recipe.js"></script>
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
			
			<!-- 신고 모달창 -->
			<form id="reportRecipeForm" onsubmit="return validateReportRecipeForm();">
				<div class="modal" id="reportRecipeModal">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<h5>신고하기</h5>
								<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
							</div>
							<div class="modal-body">
								<label>레시피 신고 사유</label>
								<div class="report">
									<div class="reason">
										<div class="form-radio">
											<div>
												<input type="radio" id="reason1" name="reason" value="">
												<label for="reason1">정당/정치인 비하 및 선거운동</label>
											</div>
										</div>
										<div class="form-radio">
											<div>
												<input type="radio" id="reason2" name="reason" value="">
												<label for="reason2">유출/사칭/사기</label>
											</div>
										</div>
										<div class="form-radio">
											<div>
												<input type="radio" id="reason3" name="reason" value="">
												<label for="reason3">욕설/비하</label>
											</div>
										</div>
										<div class="form-radio">
											<div>
												<input type="radio" id="reason4" name="reason" value="">
												<label for="reason4">낚시/놀람/도배</label>
											</div>
										</div>
										<div class="form-radio">
											<div>
												<input type="radio" id="reason5" name="reason" value="">
												<label for="reason5">상업적 광고 및 판매</label>
											</div>
										</div>
										<div class="form-radio">
											<div>
												<input type="radio" id="reason6" name="reason" value="">
												<label for="reason6">불법촬영물 등의 유통</label>
											</div>
										</div>
									</div>
								</div>
								<div class="form-info">
									<p>
										신고는 반대의견을 나타내는 기능이 아닙니다.
										신고 사유에 맞지 않는 신고를 했을 경우, 해당 신고는 처리되지 않습니다.
									</p>
								</div>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-danger" data-bs-dismiss="modal">닫기</button>
								<button type="submit" class="btn btn-primary">신고하기</button>
							</div>
						</div>
					</div>
				</div>
			</form>
			
			<!-- 장보기메모에 담기 모달창 -->
			<form id="addMemoForm" onsubmit="return validateAddMemoForm();">
				<div class="modal" id="addMemoModal">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<h5>장보기메모에 재료 담기</h5>
								<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
							</div>
							<div class="modal-body">
								<table>
									<thead>
										<tr>
											<th width="43%">재료</th>
											<th width="43%">용량</th>
											<th width="14%">선택</th>
										</tr>
									</thead>
									<tbody id="memo"></tbody>
								</table>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-danger" data-bs-dismiss="modal">닫기</button>
								<button type="submit" class="btn btn-primary">재료 담기</button>
							</div>
						</div>
					</div>
				</div>
			</form>
			
			<!-- 후원 모달창 -->
			<!-- 후원 관련 모달창 JS는 support-recipe.js에 있음 !!! 참고!! -->
			<form id="supportRecipeForm" onsubmit="">
				<div class="modal" id="supportRecipeModal">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<h5>후원하기</h5>
								<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
							</div>
							<div class="modal-body">
								<table class="point">
									<thead>
										<tr>
											<th><p>보유 포인트</p></th>
											<th><p>사용할 포인트</p></th>
											<th><p>남은 포인트</p></th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td id="ownedPoint"></td>
											<td>
												<div class="form-input">
													<input id="pointInput" type="number" value="100" min="100" step="1">
												</div>
											</td>
											<td id="remainPoint">-100</td>
										</tr>
									</tbody>
								</table>
								<div class="form-box">
									<div class="message">
										<p>포인트 충전하기</p>
										<p>포인트를 충전하고 레시피를 후원해 보세요.</p>
									</div>
									<button type="button" data-bs-toggle="modal" data-bs-target="#topUpPointModal"
										onclick="">
										포인트 충전
									</button>
								</div>
								<div class="form-info">
									<p>
										본 사이트는 개인 포트폴리오를 위한 비상업적 웹사이트로, 연구 및 실험 목적만을 위해 운영됩니다.
										모든 기능은 수익 창출과 무관하며 후원된 금액은 자동으로 전액 환불됩니다.
									</p>
								</div>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-danger" data-bs-dismiss="modal">닫기</button>
								<button type="submit" class="btn btn-primary">후원하기</button>
							</div>
						</div>
					</div>
				</div>
			</form>
			
			<form id="topUpPointForm" onsubmit="">
				<input type="hidden" id="ownedPoint" >
				<div class="modal" id="topUpPointModal">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<h5>충전하기</h5>
								<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
							</div>
							<div class="modal-body">
								<label>충전할 포인트</label>
								<div class="topup">
									<div class="point">
										<div class="form-radio">
											<div>
												<input type="radio" id="point1" name="point" value="1000">
												<label for="point1">1,000P</label>
											</div>
											<p>1,000원</p>
										</div>
										<div class="form-radio">
											<div class="radio">
												<input type="radio" id="point2" name="point" value="3000">
												<label for="point2">3,000P</label>
											</div>
											<p>3,000원</p>
										</div>
										<div class="form-radio">
											<div>
												<input type="radio" id="point3" name="point" value="5000">
												<label for="point3">5,000P</label>
											</div>
											<p>5,000원</p>
										</div>
										<div class="form-radio">
											<div>
												<input type="radio" id="point4" name="point" value="10000">
												<label for="point4">10,000P</label>
											</div>
											<p>10,000원</p>
										</div>
									</div>
									<div class="form-result">
										<p>충전 후 예상 보유 포인트</p>
										<p><em id="totalPoint"></em></p>
									</div>
									<div class="form-info">
										<p>
											본 사이트는 개인 포트폴리오를 위한 비상업적 웹사이트로, 연구 및 실험 목적만을 위해 운영됩니다.
											모든 기능은 수익 창출과 무관하며 결제된 금액은 자동으로 전액 환불됩니다.
										</p>
									</div>
								</div>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-danger" data-bs-dismiss="modal">닫기</button>
								<button type="submit" class="btn btn-primary">충전하기</button>
							</div>
						</div>
					</div>
				</div>
			</form>
		</main>
		
		<%@include file="../common/footer.jsp" %>
	</body>
</html>