<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

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