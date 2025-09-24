<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>집밥의민족</title>
		<%@include file="../common/head.jsp" %>
		<link rel="stylesheet" href="/css/mypage/mypage.css">
		<script src="/js/mypage/mypage.js"></script>
		<script src="/js/modal/view-like-user-modal.js"></script>
		<script src="/js/modal/view-liked_user-modal.js"></script>
		<script src="/js/modal/top-up-point-modal.js"></script>
	</head>
	<body>
		<%@include file="../common/header.jsp" %>
		
		<main id="container">
			<div class="content">
				<!-- 사용자 정보 -->
				<div id="userWrap" class="user_wrap">
					<div class="user">
						<!-- 이미지 선택 영역 -->
						<div class="image_field">
							<span class="user_avatar"></span>
							<button class="edit_btn">
								<img src="/images/user/edit_user.png">
								<p>프로필 이미지 설정</p>
							</button>
						</div>
						
						<!-- 이미지 목록 영역 -->
						<form id="editUserAvatarForm">
							<ul class="image_list">
								<li><button><img src="/images/user/user1.png"></button></li>
								<li><button><img src="/images/user/user2.png"></button></li>
								<li><button><img src="/images/user/user3.png"></button></li>
								<li><button><img src="/images/user/user4.png"></button></li>
								<li><button><img src="/images/user/user5.png"></button></li>
								<li><button><img src="/images/user/user6.png"></button></li>
								<li><button><img src="/images/user/user7.png"></button></li>
								<li><button><img src="/images/user/user8.png"></button></li>
								<li><button><img src="/images/user/user9.png"></button></li>
								<li><button><img src="/images/user/user10.png"></button></li>
								<li><button><img src="/images/user/user11.png"></button></li>
							</ul>
							<input type="hidden" name="avatar">
						</form>
						
						<div class="nickname_field">
							<div class="nickname_title">
								<label>닉네임</label>
								<button class="edit_btn">
								    <img src="/images/mypage/edit_1a7ce2.png">
								    <p>수정</p>
								</button>
							</div>
							
							<div class="nickname_content">
								<p class="user_nickname"></p>
								<button type="button" data-bs-toggle="modal" data-bs-target="#viewLikedUserModal">팔로워</button>
								<button type="button" data-bs-toggle="modal" data-bs-target="#viewLikeUserModal">팔로잉</button>
							</div>
							
							<!-- 닉네임 수정폼 -->
							<form id="editUserNicknameForm" class="nickname_form">
								<div class="edit_input">
									<input type="text" name="nickname" placeholder="닉네임을 입력해주세요">
								</div>
								<p class="nickname_warning">닉네임은 노출되는 정보이므로 개인정보가 유출되지 않도록 주의해주세요.</p>
								<button type="submit" class="btn_primary_wide disable" disabled>저장</button>
							</form>
						</div>
						
						<div class="introduce_field">
							<div class="introduce_title">
								<label>소개</label>
								<button class="edit_btn">
								    <img src="/images/mypage/edit_1a7ce2.png">
								    <p>수정</p>
								</button>
							</div>
							
							<p class="user_introduce"></p>
							
							<!-- 소개 수정폼 -->
							<form id="editUserIntroduceForm" class="introduce_form">
								<div class="edit_input">
									<input type="text" name="introduce" placeholder="소개를 입력해주세요">
								</div>
								<button type="submit" class="btn_primary_wide disable" disabled>저장</button>
							</form>
						</div>
						
						<div class="point_field">
							<span>500P</span>
							<button class="btn_primary" type="button" data-bs-toggle="modal" data-bs-target="#topUpPointModal" onclick="">충전</button>
						</div>
					</div>
					
					<!-- 내 정보 관리 -->
					<div class="menu_wrap">
						<h2>내 정보 관리</h2>
						
						<!-- 회원 정보 -->
						<div class="my_info">
							<label>내 정보 관리</label>
							<div class="info">
								<a href="/user/userInfo.do" class="info_box">
									<div class="info_txt">
										<span>회원정보</span>
										<p>집밥의민족 회원정보를 조회, 수정합니다.</p>
									</div>
									<img src="/images/mypage/arrow_right.png">
								</a>
								<a href="/user/changePassword.do" class="info_box">
									<div class="info_txt">
										<span>비밀번호 변경</span>
										<p>비밀번호를 변경합니다.</p>
									</div>
									<img src="/images/mypage/arrow_right.png">
								</a>
								<a href="/mypage/revenue.do" class="info_box">
									<div class="info_txt">
										<span>수익</span>
										<p>수익을 확인합니다.</p>
									</div>
									<img src="/images/mypage/arrow_right.png">
								</a>
							</div>
						</div>
						
						<!-- 활동 관리 -->
						<div class="my_info">
							<label>활동 관리</label>
							<div class="info">
								<a href="/mypage/recipe.do" class="info_box">
									<div class="info_txt">
										<span>내 레시피</span>
										<p>작성한 레시피를 조회, 수정합니다.</p>
									</div>
									<img src="/images/mypage/arrow_right.png">
								</a>
								<a href="/mypage/review.do" class="info_box">
									<div class="info_txt">
										<span>내 요리 후기</span>
										<p>내 레시피에 작성된 후기를 조회합니다.</p>
									</div>
									<img src="/images/mypage/arrow_right.png">
								</a>
								<a href="/mypage/comment.do" class="info_box">
									<div class="info_txt">
										<span>내 댓글</span>
										<p>작성한 댓글을 조회, 수정합니다.</p>
									</div>
									<img src="/images/mypage/arrow_right.png">
								</a>
								<a href="/mypage/savedRecipe.do" class="info_box">
									<div class="info_txt">
										<span>저장한 레시피</span>
										<p>저장한 레시피를 조회합니다.</p>
									</div>
									<img src="/images/mypage/arrow_right.png">
								</a>
								<a href="/mypage/savedGuide.do" class="info_box">
									<div class="info_txt">
										<span>저장한 키친가이드</span>
										<p>저장한 키친가이드를 조회합니다.</p>
									</div>
									<img src="/images/mypage/arrow_right.png">
								</a>
							</div>
						</div>
						
						<!-- 쿠킹 클래스 -->
						<div class="my_info">
							<label>쿠킹 클래스</label>
							<div class="info">
								<a href="/mypage/appliedClass.do" class="info_box">
									<div class="info_txt">
										<span>신청 목록 관리</span>
										<p>내가 신청한 쿠킹클래스를 조회합니다.</p>
									</div>
									<img src="/images/mypage/arrow_right.png">
								</a>
								<a href="/mypage/class.do" class="info_box">
									<div class="info_txt">
										<span>내 쿠킹클래스</span>
										<p>내가 주최한 쿠킹클래스를 관리합니다.</p>
									</div>
									<img src="/images/mypage/arrow_right.png">
								</a>
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<!-- 모달 -->
			<%@include file="../modal/viewLikeUserModal.jsp" %>
			<%@include file="../modal/viewLikedUserModal.jsp" %>
			<%@include file="../modal/topUpPointModal.jsp" %>
			
			
		</main>
		
		<%@include file="../common/footer.jsp" %>
	</body>
</html>