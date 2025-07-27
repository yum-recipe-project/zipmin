<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>집밥의민족</title>
		<%@include file="../common/head.jsp" %>
		<link rel="stylesheet" href="/css/cooking/view-class.css">
		<script src="/js/cooking/view-class.js"></script>
		<script src="/js/modal/apply-class-modal.js"></script>
	</head>
	<body>
		<%@include file="../common/header.jsp" %>
		<main id="container">
			<div class="content">
			
				<!-- 네비게이션 바 -->
				<div class="nav_wrap">
					<a href="/"><span>홈</span></a>
					<a href="/cooking/listClass.do">
						<img src="/images/cooking/arrow_right.png">
						<span>쿠킹클래스</span>
					</a>
					<a class="active" href="/cooking/listClass.do">
						<img src="/images/cooking/arrow_right.png">
						<span>쿠킹클래스 상세</span>
					</a>
				</div>
				
				<div class="class_content">
					<!-- 클래스 정보 -->			
					<div class="class_info">
						<div class="thumbnail">
							<img src="/images/common/test.png">
						</div>
						<div class="tab">
							<a class="active" href="#intro">클래스 소개</a>
							<a href="#curriculum">커리큘럼</a>
						</div>
						<!-- 소개 -->
						<section id="intro" class="intro_wrap">
							<h3>클래스 소개</h3>
							<div class="target"></div>
							<div class="intro">
								<h4>어떤 걸 배울까요?</h4>
								<p></p>
							</div>
						</section>
						<!-- 커리큘럼 -->
						<section id="curriculum" class="curriculum_wrap">
							<h3>커리큘럼</h3>
							<ul class="curriculum_list"></ul>
						</section>
						<!-- 강사 소개 -->
						<section class="lecturer_wrap">
							<h3>강사 소개</h3>
							<ul class="tutor_list"></ul>
						</section>
						<!-- 공지사항 -->
						<section class="notice">
							<h3>교육 신청 전 꼭 확인해주세요</h3>
							<p>집밥의민족 회원에 한해 클래스를 신청하실 수 있습니다.</p>
							<p>오프라인 교육은 신청하신 분들 중 선정 과정을 거쳐 최종 선정되신 분들만 참여 가능합니다.</p>
							<p>교육 신청 취소는 교육 신청 기간 내에만 가능하며, ‘마이페이지’에서 신청을 취소하실 수 있습니다.</p>
							<p>선정된 교육 불참 시 ‘결석’으로 처리되며, 최근 60일 내 누적 3회 결석 시 마지막 교육일 다음날부터 향후 30일간 교육 신청이 제한됩니다.</p>
						</section>
					</div>
					
					<!-- 클래스 신청 -->
					<div class="class_apply">
						<div class="apply_header">
							<h2></h2>
							<button class="btn_primary_wide" type="button" data-bs-toggle="modal" data-bs-target="#applyClassModal">
								신청하기
							</button>
						</div>
						<div class="apply_info">
							<div class="info headcount">
								<img src="/images/cooking/group.png">
								<p></p>
							</div>
							<div class="info">
								<img src="/images/cooking/announce.png">
								<p>선착순 마감</p>
							</div>
							<div class="info need">
								<img src="/images/cooking/bag.png">
								<p></p>
							</div>
						</div>
						<div class="apply_address">
							<div class="place">
								<em>장소</em>
								<span></span>
							</div>
							<div class="date">
								<span></span>
							</div>
						</div>
					</div>
				</div>
			</div>
						
			<!-- 클래스 신청 모달창 -->
			<%@include file="../modal/applyClassModal.jsp" %>
			
		</main>
		<%@include file="../common/footer.jsp" %>
	</body>
</html>