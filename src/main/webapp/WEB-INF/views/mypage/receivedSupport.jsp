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
			<form id="changeAccountForm" onsubmit="">
				<div class="modal" id="changeAccountModal">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<h5>출금 계좌 관리</h5>
								<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
							</div>
							<div class="modal-body">
								<!-- 출금 계좌가 있을 경우에만 출력 -->
								<h5>출금 계좌</h5>
								<div class="form-account">
									<p>국민은행 919*******0</p>
									<p>예금주 정하림</p>
								</div>
								<!-- 출금 계좌 변경 -->
								<h5>출금 계좌 변경</h5>
								<div class="form-group">
									<label>은행</label>
					                <select class="form-select" id="dropdownSelect">
					                    <option value="1">산업은행</option>
					                    <option value="2">기업은행</option>
					                    <option value="3">국민은행</option>
					                    <option value="3">수협</option>
					                    <option value="3">농협은행</option>
					                    <option value="3">지역농축협</option>
					                    <option value="3">우리은행</option>
					                    <option value="3">SC은행</option>
					                    <option value="3">시티은행</option>
					                    <option value="3">대구은행</option>
					                    <option value="3">부산은행</option>
					                    <option value="3">광주은행</option>
					                    <option value="3">제주은행</option>
					                    <option value="3">전북은행</option>
					                    <option value="3">경남은행</option>
					                    <option value="3">새마을금고</option>
					                    <option value="3">신협</option>
					                    <option value="3">우체국</option>
					                    <option value="3">하나은행</option>
					                    <option value="3">신한은행</option>
					                    <option value="3">케이뱅크</option>
					                    <option value="3">카카오뱅크</option>
					                    <option value="3">토스뱅크</option>
					                    <option value="3">KB증권</option>
					                    <option value="3">미래에셋증권</option>
					                    <option value="3">삼성증권</option>
					                    <option value="3">한국투자</option>
					                    <option value="3">NH투자증권</option>
					                    <option value="3">하나증권</option>
					                    <option value="3">신한투자증권</option>
					                    <option value="3">메리츠증권</option>
					                </select>
								</div>
								<div class="form-group">
									<label>계좌번호</label>
									<input class="form-control" id="accountNumberInput" name="" value="">
									<p>'-' 없이 숫자만 입력해 주세요. 선불전자지급수단은 사용할 수 없어요.</p>
								</div>
								<div class="form-group">
									<label>예금주명</label>
									<input class="form-control" id="accountNameInput" name="name" value="">
									<p>예금주명은 회원의 실명과 일치해야 해요.</p>
								</div>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-danger" data-bs-dismiss="modal">닫기</button>
								<button type="submit" class="btn btn-primary">저장하기</button>
							</div>
						</div>
					</div>
				</div>
			</form>
			
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
											<td id="ownedPoint"></td>
											<td>
												<div class="form-input">
													<input id="pointInput" type="number" value="10000" min="10000" step="1">
												</div>
											</td>
											<td id="remainPoint"></td>
										</tr>
									</tbody>
								</table>
								<div class="form-info">
									<p>
										본 사이트는 개인 포트폴리오를 위한 비상업적 웹사이트로, 연구 및 실험 목적만을 위해 운영됩니다.
										모든 기능은 수익 창출과 무관하며 후원된 금액은 자동으로 전액 환불되었으므로 포인트는 정산되지 않습니다.
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