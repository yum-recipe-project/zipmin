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
							<a href="#intro">커리큘럼</a>
						</div>
						<!-- 소개 -->
						<section id="intro" class="intro_wrap">
							<h3>클래스 소개</h3>
							<div class="intro">
								<h4>이런 분께 추천해요</h4>
								<p>한식을 제대로 배우고 싶은 분</p>
								<p>요리에 대한 기초가 부족한 분</p>
								<p>집에서 맛있는 한식을 직접 만들고 싶은 분</p>
							</div>
							<div class="intro">
								<h4>어떤 걸 배울까요?</h4>
								<p>한식의 기본 재료 손질과 조리법</p>
								<p>대표적인 한식 요리 실습 (김치, 찌개, 전 등)</p>
								<p>맛을 내는 비법과 응용 레시피</p>
							</div>
						</section>
						<!-- 커리큘럼 -->
						<section id="curriculum" class="curriculum_wrap">
							<h3>커리큘럼</h3>
							<ul class="curriculum_list">
								<li>
									<div class="meta">
										<span class="badge">1교시</span>
										<span>14:00 - 16:00</span>
									</div>
									<div class="title">
										<strong>1교시 수업 제목</strong>
										<span></span>
									</div>
								</li>
							</ul>
						</section>
						<!-- 강사 소개 -->
						<section class="lecturer_wrap">
							<h3>강사 소개</h3>
							<div class="lecturer">
								<div class="photo">
									<img src="/images/common/test.png">
								</div>
								<div class="profile">
									<h5>이집밥</h5>
									<p>한식 전문 셰프 (10년 경력)</p>
									<p>한식 조리 기능사</p>
									<button class="more" onclick="location.href='';">
										<span>프로필 자세히보기</span>
										<img src="/images/cooking/arrow_right.png">
									</button>
								</div>
							</div>
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
							<h2>한식 입문 클래스</h2>
							<button class="btn_primary_wide" type="button" data-bs-toggle="modal" data-bs-target="#applyClassModal"
								onclick="">
								신청하기
							</button>
						</div>
						<div class="apply_info">
							<div class="info">
								<img src="/images/cooking/group.png">
								<p>40명 선정</p>
							</div>
							<div class="info">
								<img src="/images/cooking/announce.png">
								<p>2025.02.05 14:00 선정 발표</p>
							</div>
							<div class="info">
								<img src="/images/cooking/bag.png">
								<p>간단한 필기도구</p>
							</div>
						</div>
						<div class="apply_address">
							<div class="place">
								<em>서울</em>
								<span>더조은아카데미 2층</span>
							</div>
							<div class="date">
								<span>2024.03.05(수) 16:00-17:00</span>
							</div>
						</div>
					</div>
				</div>
			</div>
						
			<!-- 클래스 신청 모달창 -->
			<form id="applyClassForm" method="post" action="" onsubmit="">
				<!-- 히든 폼에 사용자 정보 숨겨서 같이 보내야 함 -->
				<div class="modal" id="applyClassModal">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<h5>한식 입문 클래스</h5>
								<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
							</div>
							<div class="modal-body">
								<div class="form-group">
									<label>1. 클래스를 신청하게 된 동기와 이유를 적어주세요 (500자 이내)</label>
									<textarea class="form-control" id="applyClass어쩌구Input" name="content" style="height: 90px;"></textarea>
								</div>
								<div class="form-group">
									<label>2. 강사님께 궁금한 점이 있다면 적어주세요 (선택사항)</label>
									<textarea class="form-control" id="applyClass어쩌구Input" name="content" style="height: 90px;"></textarea>
								</div>
								<p class="form-notice">
									<img src="/images/cooking/error.png">
									교육 신청 전 교육 정보를 한번 더 확인해주세요!
								</p>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn" data-bs-dismiss="modal">닫기</button>
								<button type="submit" id="applyClassButton" class="btn btn-disable" disabled>신청하기</button>
							</div>
						</div>
					</div>
				</div>
			</form>
			
		</main>
		<%@include file="../common/footer.jsp" %>
	</body>
</html>