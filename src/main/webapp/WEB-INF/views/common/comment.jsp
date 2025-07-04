<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="comment_wrap">
	<!-- 댓글 헤더 -->
	<div class="comment_header">
		<div class="comment_count">
			<span>댓글</span>
			<span></span>
		</div>
		<div class="comment_order">
			<button class="btn_sort_small active" data-sort="new">최신순</button>
			<button class="btn_sort_small" data-sort="old">오래된순</button>
			<button class="btn_sort_small" data-sort="hot">인기순</button>
		</div>
	</div>
	
	<!-- 댓글 작성 -->
	<form id="writeCommentForm" class="comment_write">
		<!-- 로그인 하지 않은 경우 -->
		<div id="logout_state">
			<a href="/user/login.do">
				<span>댓글 작성을 위해 로그인을 해주세요.</span>
				<span>400</span>
			</a>
		</div>
		<!-- 로그인 한 경우 -->
		<div id="login_state">
			<div class="login_user">
				<img src="/images/common/test.png">
				<span></span>
			</div>
			<div class="comment_input">
				<textarea id="writeCommentContent" rows="2" maxlength="400" placeholder="욕설, 비방, 허위 정보 및 부적절한 댓글은 사전 경고 없이 삭제될 수 있습니다."></textarea>
				<span>400</span>
			</div>
			<div class="write_btn">
				<button class="btn_primary disable" type="submit" class="disable" disabled>작성하기</button>
			</div>
		</div>
	</form>
	
	<!-- 댓글 목록 -->
	<ul class="comment_list"></ul>
	
	<!-- 댓글 더보기 버튼 -->
	<div class="more_comment_btn">
		<button class="btn_more">
			<span>더보기</span>
			<img src="/images/common/arrow_down.png">
		</button>
	</div>
</div>