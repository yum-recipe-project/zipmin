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
						<div class="support_sort">
							<button class="btn_sort active">전체</button>
							<button class="btn_sort">레시피</button>
						</div>
					</div>
					
					<div class="support_point">
						<div class="point">
							<span>출금 가능 금액</span>
							<span>0원</span>
						</div>
						<button class="btn_dark" data-bs-toggle="modal" data-bs-target="#withdrawPointModal"
							onclick="">
							출금하기
						</button>
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
			</div>
			
			<!-- 출금 모달창 -->			
			<form id="withdrawPointForm" onsubmit="">
				<div class="modal" id="withdrawPointModal">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<h5>출금하기</h5>
								<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
							</div>
							<div class="modal-body">
								<table class="point">
									<thead>
										<tr>
											<th><p>보유 포인트</p></th>
											<th><p>출금할 포인트</p></th>
											<th><p>남은 포인트</p></th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td id="ownedPoint">0</td>
											<td>
												<div class="form-input">
													<input id="pointInput" type="number" value="10000" min="10000" step="1">
												</div>
											</td>
											<td id="remainPoint">-10000</td>
										</tr>
									</tbody>
								</table>
								<div class="form-info">
									<p>
										본 사이트는 개인 포트폴리오를 위한 비상업적 웹사이트로, 연구 및 실험 목적만을 위해 운영됩니다.
										모든 기능은 수익 창출과 무관하며 후원된 금액은 자동으로 전액 환불됩니다.
									</p>
								</div>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-danger" data-bs-dismiss="modal">닫기</button>
								<button type="submit" class="btn btn-primary">출금하기</button>
							</div>
						</div>
					</div>
				</div>
			</form>
			
		</main>
		<%@include file="../common/footer.jsp" %>
	</body>
</html>