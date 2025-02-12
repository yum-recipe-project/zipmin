<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>집밥의민족</title>
		<%@include file="../common/head.jsp" %>
		<link rel="stylesheet" href="/css/recipe/view-recipe.css">
		<script src="/js/recipe/view-recipe.js"></script>
	</head>
	<body>
		<%@include file="../common/header.jsp" %>
		
		<main id="container">
			<div class="content">
				<div class="recipe_wrap">
					<div class="recipe_header">
						<!-- 제목 -->
						<h2>마법의 카레 가루</h2>
						
						<!-- 스크랩 버튼 -->
						<div class="recipe_save_btn">
							<button onclick="">
								<img src="/images/recipe/star.png"> 저장
							</button>
						</div>
						
						<!-- 레시피 정보 -->
						<div class="recipe_info">
							<div class="recipe_info_item">
								<img src="/images/recipe/level.png">
								<p>초급</p>
							</div>
							<div class="recipe_info_item">
								<img src="/images/recipe/time.png">
								<p>40분</p>
							</div>
							<div class="recipe_info_item">
								<img src="/images/recipe/spicy.png">
								<p>매움</p>
							</div>
						</div>
						
						<!-- 작성자 -->
						<div class="recipe_writer">
							<img src="/images/common/black.png">
							<p>아잠만</p>
							<c:if test="${ true }">
								<a href="">팔로우</a>
							</c:if>
						</div>
						
						<!-- 소개 -->
						<p class="recipe_introduce">
							이 요리는 영국에서 최초로 시작되어 일년에 한바퀴를 돌면서 받는 사람에게 행운을 주었고
							지금은 당신에게로 옮겨진 이 요리는 4일 안에 당신 곁을 떠나야 합니다.
							이 요리를 포함해서 7통을 행운이 필요한 사람에게 보내 주셔야 합니다.
							복사를 해도 좋습니다. 혹 미신이라 하실지 모르겠지만 사실입니다.
						</p>
						
						<!-- 카테고리 -->
						<div class="recipe_category">
							<span>#한식</span>
							<span>#크리스마스</span>
						</div>
						
						<!-- 버튼 -->
						<div class="btn_wrap">
							<button onclick="">
								<img src="/images/recipe/siren.png">
							</button>
							<button onclick="">
								<img src="/images/recipe/youtube.png">
							</button>
							<button onclick="">
								<img src="/images/recipe/print.png">
							</button>
						</div>
						
					</div>
				
				
				
				</div>
			
			
				
				
				
			</div>
		</main>
		
		<%@include file="../common/footer.jsp" %>
	</body>
</html>