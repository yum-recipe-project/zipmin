<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
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
						<div class="save_recipe_btn">
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
							<a class="nickname" href="">
								<img src="/images/common/black.png">
								아잠만
							</a>
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
						<div class="icon_btn_wrap">
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
					
					<div class="recipe_ingredient">
						<!-- 제목 -->
						<h3>재료</h3>
						
						<!-- 장보기메모에 재료 담기 버튼 -->
						<div class="save_ingredient_btn">
							<button onclick="">
								<img src="/images/recipe/sell.png"> 장보기메모에 재료 담기
							</button>
						</div>
						
						<!-- 양 -->
						<div class="recipe_serving">
							<select id="servingInput" name="">
								<option value="serving1">1인분</option>
								<option value="serving2">2인분</option>
								<option value="serving3">3인분</option>
								<option value="serving4">4인분</option>
								<option value="serving5">5인분</option>
							</select>
							<p>레시피의 용량을 단순히 인분에 맞춰 계산한 것이므로, 실제 조리 시 정확하지 않을 수 있습니다.</p>
						</div>
						
						<!-- 재료 표 -->
						<table class="ingredient_list">
							<thead>
								<tr>
									<th width="236px">재료</th>
									<th width="236px">용량</th>
									<th width="472px">비고</th>
								</tr>
							</thead>
							<tbody>
								<%-- <c:forEach> --%>
									<tr>
										<td>마늘</td>
										<td>5쪽</td>
										<td>슬라이스</td>
									</tr>
									<tr>
										<td>카레 가루</td>
										<td>2큰술</td>
										<td></td>
									</tr>
									<tr>
										<td>치킨스탁</td>
										<td>7ea</td>
										<td>태태락치킨다시다 50배 희석</td>
									</tr>
								<%-- </c:forEach> --%>
							</tbody>
						
						</table>
					</div>
					
					<div class="recipe_step">
						<!-- 제목 -->
						<h3>조리 순서</h3>
						<ul class="step_list">
							<%-- <c:forEach> --%>
								<li>
									<div class="description">
										<h5>STEP1</h5>
										<p>카레가루를 냄비에 부어버립니다</p>
									</div>
									<c:if test="${ true }">
										<div class="image">
											<img src="/images/common/black.png">
										</div>
									</c:if>
								</li>
							<%-- </c:forEach> --%>
						</ul>
						
					</div>
					
					<!-- 요리팁 -->
					<div class="recipe_tip">
						<!-- 제목 -->
						<h3>주의사항</h3>
						<p>마법의 카레 가루는 이틀 이내에 모두 먹어야 합니다.</p>
					</div>
					
					<!-- 구독 및 후원 -->
					<div class="recipe_util">
						<div class="profile">
							<img src="/images/common/black.png">
							<div>
								<h5>아잠만</h5>
								<p>구독자 45명</p>
							</div>
						</div>
						<div class="btn_wrap">
							<!-- 여기서 모달창 열어야겠지 -->
							<button type="button" data-bs-toggle="modal" data-bs-target="#supportRecipeModal"
								onclick="openRecipeSupportModal();">
								레시피 후원하기
							</button>
							
							<c:if test="${ true }">
								<button class="follow_btn" type="submit" onclick="">구독</button>
							</c:if>
							<c:if test="${ true }">
								<button class="unfollow_btn" type="submit" onclick="">구독 중</button>
							</c:if>
						</div>
					</div>
				</div>
				
				<!-- 탭 메뉴 버튼 -->
				<div class="tab_button_wrap">
					<div class="tab_button">
						<button class="active">리뷰 (3,432)</button>
						<button>댓글 (27)</button>
					</div>
				</div>
				
				<!-- 리뷰 -->
				<div class="tab_content">
					<div id="recipeReviewContent"></div>
				</div>
				
				<!-- 댓글 -->
				<div class="tab_content">
					<div id="recipeCommentContent"></div>
				</div>
				
			</div>
		</main>
		
		<%@include file="../common/footer.jsp" %>
		
		<!-- 후원 모달창 -->
		<form id="supportRecipeForm" onsubmit="">
			<!-- 히든폼 필요하다면 이곳에 추가 -->
			<div class="modal" id="supportRecipeModal">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<h5>제목입력</h5>
							<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
						</div>
						<div class="modal-body">
							
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-danger" data-bs-dismiss="modal">닫기</button>
							<button type="submit" class="btn btn-primary">작성하기</button>
						</div>
					</div>
				</div>
			</div>
		</form>
	</body>
</html>