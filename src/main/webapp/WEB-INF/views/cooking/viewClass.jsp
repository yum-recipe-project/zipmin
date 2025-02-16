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
					<div class="class_apply">
						<div class="apply_header">
							<h2>어쩌구 저쩌구 클래스</h2>
							<button class="btn_primary_wide">신청하기</button>
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
					</div>
					
					<div class="class_info">
						<div class="thumbnail">
							<img src="/images/common/test.png">
						</div>
						<div class="tab">
							<a class="active" href="#intro">클래스 소개</a>
							<a href="#intro">커리큘럼</a>
						</div>
						
						<section id="intro" class="intro_wrap">
							<h3>클래스 소개</h3>
							<div class="intro">
								<h4>이런 분께 추천해요</h4>
								<p>내용 입력</p>
							</div>
							<div class="intro">
								<h4>어떤걸 배울까요</h4>
								<p>내용 입력</p>
							</div>
						</section>
						<section id="curriculum" class="curriculum_wrap">
							<h3>커리큘럼</h3>
							<ul class="curriculum_list">
								<li>
									<div class="meta">
										<span class="badge">내용</span>
										<span>jdkljsl</span>
									</div>
									<div class="title">
										<strong>1교시 수업 제목</strong>
										<span></span>
									</div>
								</li>
							</ul>
						</section>
						<section class="lecturer_wrap">
							<h3>강사 소개</h3>
						</section>
						<section class="notice">
							<h3>교육 신청 전 꼭 확인해주세요.</h3>
						</section>
						
						
					</div>
				</div>
			
			
			</div>
		</main>
		<%@include file="../common/footer.jsp" %>
	</body>
</html>