<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
	<head>
		<meta charset="UTF-8">
		<title>집밥의민족</title>
		<%@include file="../common/head.jsp" %>
		<link rel="stylesheet" href="/css/kitchen/view-guide.css">
		<link rel="stylesheet" href="/css/common/comment.css">
		<script src="/js/kitchen/write-guide.js"></script>
	</head>
	
	<body>
		<%@include file="../common/header.jsp" %>
		
		<main id="container">
			<div class="content">
				<!-- 가이드 작성 -->
				<div class="guide_wrap">
					<h2>키친가이드 작성</h2>
					
					<form id="writeGuideForm" action="/guides/write.do" method="post">
						<!-- 소제목 -->
						<div class="form_group">
							<label for="subtitle">소제목</label>
							<input type="text" id="subtitle" name="subtitle" required>
						</div>

						<!-- 제목 -->
						<div class="form_group">
							<label for="title">제목</label>
							<input type="text" id="title" name="title" required>
						</div>

						<!-- 카테고리 -->
						<div class="form_group">
							<label for="category">카테고리</label>
							<select id="category" name="category" required>
								<option value="">-- 선택하세요 --</option>
								<option value="preparation">손질법</option>
								<option value="storage">보관법</option>
								<option value="cooking">요리 정보</option>
								<option value="etc">기타 정보</option>
							</select>
						</div>

						<!-- 본문 -->
						<div class="form_group">
							<label for="content">내용</label>
							<textarea id="content" name="content" rows="10" required></textarea>
						</div>

						<!-- 버튼 -->
						<div class="form_actions">
							<button type="submit" class="btn_primary">저장하기</button>
							<button type="button" class="btn_outline" onclick="location.href='/kitchen/listGuide.do'">취소</button>
						</div>
					</form>
				</div>
			</div>
		</main>
		
		
		
		<%@include file="../common/footer.jsp" %>
	</body>
</html>