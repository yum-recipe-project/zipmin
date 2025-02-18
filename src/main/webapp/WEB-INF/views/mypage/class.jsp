<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>집밥의민족</title>
		<%@include file="../common/head.jsp" %>
		<link rel="stylesheet" href="/css/mypage/class.css">
		<script src="/js/mypage/class.js"></script>
	</head>
	<body>
		<%@include file="../common/header.jsp" %>
		
		<main id="container">
			<div class="content">
				<div class="class_wrap">
					
					<div class="class_header">
						<a href="/mypage.do">
							<span>
								마이페이지
								<img src="/images/mypage/arrow_right.png">
							</span>
						</a>
						<h2>나의 클래스</h2>
					</div>
					
					<div class="class_util">
						<p class="total">총 23개</p>
						<div class="class_sort">
							<button class="btn_sort active">전체</button>
							<button class="btn_sort">진행중</button>
							<button class="btn_sort">마감</button>
						</div>
					</div>
					
					<c:if test="${ true }">
						<ul class="class_list">
							<%-- <c:forEach> --%>
								<li>
									<c:if test="${ true }">
										<div class="status">
											선정 진행 중이에요
										</div>
									</c:if>
									<c:if test="${ false }">
										<div class="status ">
											신청 완료
										</div>
									</c:if>
									<c:if test="${ false }">
										<div class="status ">
											선정 되지 않았어요
										</div>
									</c:if>
									<div class="class">
										<a href="/cooking/viewClass.do" class="thumbnail">
											<img src="/images/common/test.png">
										</a>
										<div class="info">
											<a href="/cooking/viewClass.do">한식 입문 클래스</a>
											<p>
												<span><em>수업일정</em>2025.04.05(수) 15:00-17:00</span>
												<span><em>선정발표</em>2025.03.15(월) 12:00</span>
											</p>
										</div>
										<div class="cancel_btn">
											<button class="btn_outline">신청서 보기</button>
										</div>
									</div>
								</li>
							<%-- </c:forEach> --%>
						</ul>
					</c:if>
				</div>
			</div>
		</main>
		
		<%@include file="../common/footer.jsp" %>
	</body>
</html>