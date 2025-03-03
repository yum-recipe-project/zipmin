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
	</head>
	<body>
		<%@include file="../common/header.jsp" %>
		
		<main id="container">
			<div class="content">
			
				<div class="profile_wrap">
					
					<!-- 유저 프로필 -->
					<div class="info_wrap">
						<div class="info_inner">
						
							<!-- 유저 이미지 -->
							<div class="profile">
							
								<span class="profile_img"></span>
								<div class="modify_btn_wrap">
									<button class="modify_btn" id="profileModifyBtn">
									    <img id="profileIcon" src="/images/member/modify_member.png" alt="수정">
									    <p id="profileState">프로필 이미지 설정</p>
									</button>
									
									 <input type="file" id="fileInput" style="display:none;" accept="image/*">
									 
								</div>
							
							</div>
						
							<!-- 유저 정보 -->
							<div class="summary">
							
								<div class="nickname_field">
									<div class="field_top">
										<label>닉네임</label>
										<input type="hidden" id="nicknameInput" name="" value="" placeholder="이름 입력">
										<div class="modify_btn_wrap">
											<button class="modify_btn" id="nickNameModifyBtn">
											    <img id="nickNameIcon" src="/images/member/modify_member.png" alt="수정">
											    <p id="nickNameState">수정</p>
											</button>
										</div>
									</div>
									<div class="field_bottom" id="nickNameField">
										<p>아잠만</p>
										<div class="follow_txt">
											<a href="/mypage/follower.do">팔로워</a>
											<a href="/mypage/following.do">팔로잉</a>
										</div>
									</div>
									
									<!-- 닉네임 수정폼 -->
									<div id="modifyNickName" class="hidden">
										<div class="nickname_random_field">
											<span>닉네임 설정</span>
											<button class="random_wrap">
											    <span>랜덤 생성</span>
											    <img src="/images/mypage/refresh.png" alt="새로고침">
											</button>
										</div>
										<div class="modify_field">
											<input type="text" name="" value="" placeholder="닉네임 입력">
										</div>
										<p class="nickname_warning">닉네임은 노출되는 정보이므로 개인정보가 유출되지 않도록 주의해주세요.</p>
										<div class="submit_btn_wrap">
											<button type="submit" class="save_btn">저장</button>
										</div>
									</div>
									
								</div>
								
								<div class="grade_field">
									<label>등급</label>
									<input type="hidden" id="nameInput" name="" value="" placeholder="이름 입력">
									<p>빨간양말</p>
								</div>
								
								<div class="comment_field">
									<div class="field_top">
										<label>소개</label>
										<input type="hidden" id="nameInput" name="" value="" placeholder="이름 입력">
										<div class="modify_btn_wrap">
											<button class="modify_btn" id="commentModifyBtn">
											    <img id="commentIcon" src="/images/member/modify_member.png" alt="수정">
											    <p id="commentState">수정</p>
											</button>
										</div>
									</div>
									<p id="comment">안녕하세요, 저는 아잠만입니다. 저는 요즘 곱창에 빠져있어요. 곱창레시피 많이 올릴게요~</p>
									
									<!-- 소개 수정폼 -->
									<div id="modifyComment" class="hidden">
										<div class="modify_field">
											<input type="text" name="" value="" placeholder="소개 입력">
										</div>
										<div class="submit_btn_wrap">
											<button type="submit" class="save_btn">저장</button>
										</div>
									</div>
									
									
								</div>
								
							</div>
							
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
								
								<a href="/mypage/receivedSupport.do" class="info_box">
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
		</main>
		
		<%@include file="../common/footer.jsp" %>
	</body>
</html>