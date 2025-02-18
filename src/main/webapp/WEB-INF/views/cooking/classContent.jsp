<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>집밥의민족</title>
		<%@include file="../common/head.jsp" %>
		<link rel="stylesheet" href="/css/cooking/class-content.css">
	</head>
	<body>
		<input type="hidden" id="classCount" value="">
			
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
		
	</body>
</html>