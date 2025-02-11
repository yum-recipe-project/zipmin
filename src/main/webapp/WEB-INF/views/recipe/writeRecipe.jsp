<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>집밥의민족</title>
		<%@include file="../common/head.jsp" %>
		<link rel="stylesheet" href="/css/recipe/write-recipe.css">
		<script src="/js/recipe/write-recipe.js"></script>
	</head>
	<body>
		<%@include file="../common/header.jsp" %>
		
		<main id="container">
			<div class="content">
				<div class="basic_wrap">
					<h2>맛있는 한 끼를 위한<br/>레시피를 소개해주세요!</h2>
					
					<form>
						<div class="title_field">
							<label>제목</label>
							<div class="title_input">
								<textarea id="titleInput" rows="1" maxlength="50" placeholder="요리 이름을 작성해주세요."></textarea>
								<span>50</span>
							</div>
							<p id="titleHint">제목을 작성해주세요.</p>
						</div>
						
						
					
					
					</form>
				
				</div>
			</div>
		</main>
		
		<%@include file="../common/footer.jsp" %>
	</body>
</html>