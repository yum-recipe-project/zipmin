<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>집밥의민족</title>
		<%@include file="../common/head.jsp" %>
		<link rel="stylesheet" href="/css/cooking/list-class.css">
		<script src="/js/cooking/list-class.js"></script>
	</head>
	<body>
		<%@include file="../common/header.jsp" %>
		
		<main id="container">
			<div class="content">
				<div class="class_wrap">
					<div class="class_header">
						<h2>쿠킹클래스</h2>
						<div class="tab">
							<a class="btn_tab active" href="">
								<span>전체</span>
							</a>
							<a class="btn_tab" href="">
								<span>전체</span>
							</a>
							<a class="btn_tab" href="">
								<span>전체</span>
							</a>
						</div>
					</div>
				
					<div class="class_content">
						<div class="class_util">
							<p class="total">총 23개</p>
							<div class="class_sort">
								<button class="btn_sort active">전체</button>
								<button class="btn_sort">진행중</button>
								<button class="btn_sort">마감</button>
							</div>
						</div>
						
						<!-- 목록 -->
						<c:if test="${ true }">
							<ul class="class_list">
								<%-- <c:foreach> --%>
									<li class="class">
										<a href="/cooking/viewClass.do">
											<div class="class_thumbnail">
												<img src="/images/common/test.png">
											</div>
											<div class="class_info">
												<h5>한식 입문 클래스</h5>
												<p class="flag">모집중</p>
												<p class="date">2025.03.04 - 2025.04.03</p>
											</div>
										</a>
									</li>
								<%-- </c:foreach> --%>
							</ul>
						</c:if>
						
						<!-- 클래스 더보기 버튼 -->
						<div class="more_class_btn">
							<button class="btn_more">
								<span>더보기</span>
								<img src="/images/kitchen/arrow_down.png">
							</button>
						</div>
					</div>
				</div>
			</div>
		</main>
		
		<%@include file="../common/footer.jsp" %>
	</body>
</html>