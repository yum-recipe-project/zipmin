<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>집밥의민족</title>
		<%@include file="../common/head.jsp" %>
		<link rel="stylesheet" href="/css/mypage/received-support.css">
		<script src="/js/mypage/received-support.js"></script>
	</head>
	
	<body>
		<%@include file="../common/header.jsp" %>
		<main id="container">
			<div class="content">
				<div class="support_wrap">

					<div class="support_header">
						<a href="/mypage.do">
							<span>
								내 정보 관리
								<img src="/images/mypage/arrow_right.png">
							</span>
						</a>
						<h2>수익 내역</h2>
					</div>
					
					<div class="support_util">
						<p class="total">총 23개</p>
					</div>
					
					<div class="support_point">
						<div class="point">
							<span>출금 가능 금액</span>
							<span>0원</span>
						</div>
						<div class="btn_wrap">
							<button class="btn_outline" data-bs-toggle="modal" data-bs-target="#changeAccountModal"
								onclick="openChangeAccountModal()">
								출금 계좌 관리
							</button>
							<button class="btn_dark" data-bs-toggle="modal" data-bs-target="#withdrawPointModal"
								onclick="openWithdrawPointModal()">
								출금하기
							</button>
						</div>
					</div>
					
					<c:if test="${ true }">
						<ul class="support_list">
							<%-- <c:forEach> --%>
								<li>
									<div class="support_info">
										<div class="sponsor">
											<p><b>다영</b> 님에게 후원받았습니다</p>
											<p>2025.04.03</p>
										</div>
										<p class="point">5,000P</p>
									</div>
									<div class="support">
										<a href="/recipe/viewRecipe.do" class="thumbnail">
											<img src="/images/common/test.png">
										</a>
										<div class="info">
											<p>레시피</p>
											<a href="/recipe/viewRecipe.do">맛돌이 김치볶음밥</a>
											<p>아잠만</p>
										</div>
									</div>
								</li>
							<%-- </c:forEach> --%>
						</ul>
					</c:if>
				</div>
				
				<div class="pagination_wrap">
					<div class="pagination">
					    <ul>
					        <li><a href="#" class="prev"><img src="/images/common/chevron_left.png"></a></li>
					        <li><a href="#" class="page active">1</a></li>
					        <li><a href="#" class="page">2</a></li>
					        <li><a href="#" class="page">3</a></li>
					        <li><a href="#" class="page">4</a></li>
					        <li><a href="#" class="page">5</a></li>
					        <li><a href="#" class="next"><img src="/images/common/chevron_right.png"></a></li>
					    </ul>
					</div>
				</div>
			</div>
			
			<!-- 출금 계좌 모달창 -->
			<%@include file="../modal/changeAccountModal.jsp" %>
			
			<!-- 출금 모달창 -->			
			<%@include file="../modal/withdrawPointModal.jsp" %>
			
		</main>
		<%@include file="../common/footer.jsp" %>
	</body>
</html>