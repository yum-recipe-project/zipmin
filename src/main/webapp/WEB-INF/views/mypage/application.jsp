<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>집밥의민족</title>
		<%@include file="../common/head.jsp" %>
		<link rel="stylesheet" href="/css/mypage/application.css">
		<script src="/js/mypage/application.js"></script>
	</head>
	<body>
		<%@include file="../common/header.jsp" %>
		<main id="container">
			<div class="content">
				<div class="apply_wrap">
					<!-- 지원서 헤더 -->
					<div class="apply_header">
						<a href="/mypage.do">
							<span>
								마이페이지
								<img src="/images/mypage/arrow_right.png">
							</span>
						</a>
						<h2>한식 입문 클래스 신청서</h2>
					</div>
					
					<!-- 지원서 유틸 -->
					<div class="apply_util">
						<p class="total">총 23개</p>
						<div class="apply_sort">
							<button class="btn_sort active">전체</button>
							<button class="btn_sort">선정</button>
							<button class="btn_sort">대기</button>
						</div>
					</div>
					
					<ul class="apply_list">
						<li>
							<table>
								<colgroup>
									<col width="100px">
									<col width="*">
								</colgroup>
								<tbody>
									<tr>
										<th scope="col">이름</th>
										<td>정하림</td>
									</tr>
									<tr>
										<th scope="col">상태</th>
										<td>대기중</td>
									</tr>
									<tr>
										<th scope="col">신청동기</th>
										<td>그냥 신청해봤어요</td>
									</tr>
									<tr>
										<th scope="col">질문</th>
										<td>알이 먼저인가요 닭이 먼저인가요?</td>
									</tr>
								</tbody>
							</table>
							<div class="attend_btn">
								<button onclick="" class="active">출석</button>
								<button onclick="">결석</button>
							</div>
						</li>					
					</ul>
				</div>
			</div>
		</main>
		<%@include file="../common/footer.jsp" %>
	</body>
</html>