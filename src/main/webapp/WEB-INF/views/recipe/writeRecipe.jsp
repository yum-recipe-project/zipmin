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
				<form name="writeRecipeForm" method="post" enctype="multipart/form-data" action="" onsubmit="">
					<div class="recipe_info_wrap">
						<h2>맛있는 한 끼를 위한<br/>레시피를 소개해주세요!</h2>
	
						<!-- 대표 이미지 -->
						<div class="thumbnail_field">
							<label>대표 이미지</label>
							<input type="file" id="thumbnailInput" name="image" accept="image/*">
							<p id="titleHint">대표 이미지를 첨부해주세요.</p>
						</div>
						
					
						<!-- 제목 -->
						<div class="title_field">
							<label>제목</label>
							<input type="text" id="titleInput" name="" value="" placeholder="요리 제목을 작성해주세요.">
							<p id="titleHint">제목을 작성해주세요.</p>
						</div>
						
						<!-- 소개 -->
						<div class="introduce_field">
							<label>소개</label>
							<input type="text" id="introduceInput" name="" value="" placeholder="소개를 작성해주세요.">
							<p id="introduceHint">소개를 작성해주세요.</p>
						</div>
						
						<!-- 난이도 -->
						<div class="level_field">
							<label>난이도</label>
							<div class="level_btn_group">
								<button type="button" class="level_btn" data-value="entry">입문</button>
								<button type="button" class="level_btn" data-value="easy">초급</button>
								<button type="button" class="level_btn" data-value="medium">중급</button>
								<button type="button" class="level_btn" data-value="hard">고급</button>
							</div>
							<input type="hidden" name="" id="levelInput">
							<p id="levelHint">난이도를 선택해주세요.</p>
							<div class="notice">
								<p>・&nbsp;&nbsp;입문 : 요리를 처음 해보는 사람들을 위한 단계</p>
								<p>・&nbsp;&nbsp;초급 : 기본적인 칼질과 간단한 조리법을 익힌 단계</p>
								<p>・&nbsp;&nbsp;중급 : 조리와 불 조절을 능숙하게 할 수 있는 단계</p>
								<p>・&nbsp;&nbsp;고급 : 맛 조절과 재료 조합을 잘 이해하여 고급 요리 테크닉을 활용할 수 있는 단계</p>
							</div>
						</div>
						
						<!-- 조리시간 -->
						<div class="time_field">
							<label>조리시간</label>
							<select id="timeInput" name="">
								<option value="">조리시간을 선택하세요</option>
								<option value="time1">10분</option>
								<option value="time2">30분</option>
								<option value="time3">1시간</option>
								<option value="time4">1시간 30분</option>
								<option value="time5">2시간 이상</option>
							</select>
							<p id="timeHint">조리시간을 선택해주세요.</p>
						</div>
						
						<!-- 맵기정도 -->
						<div class="spicy_field">
							<label>맵기 정도</label>
							<div class="spicy_btn_group">
								<button type="button" class="spicy_btn" data-value="mildest">아주 안매움</button>
								<button type="button" class="spicy_btn" data-value="mild">안매움</button>
								<button type="button" class="spicy_btn" data-value="medium">보통</button>
								<button type="button" class="spicy_btn" data-value="spicy">매움</button>
								<button type="button" class="spicy_btn" data-value="very_spicy">아주 매움</button>
							</div>
							<input type="hidden" name="" id="spicyInput">
							<p id="spicyHint">맵기 정도를 선택해주세요.</p>
							<div class="notice">
								<p>・&nbsp;&nbsp;아주 안매움 : 매운맛이 전혀 없어 아이들도 먹을 수 있는 단계</p>
								<p>・&nbsp;&nbsp;안매움 : 고춧가루가 살짝 들어갔지만 맵지 않은 단계</p>
								<p>・&nbsp;&nbsp;보통 : 신라면 정도로, 매운맛 초보도 큰 부담 없이 즐길 수 있는 단계</p>
								<p>・&nbsp;&nbsp;매움 : 불닭볶음면 정도로, 매운 음식 마니아들이 즐기는 단계</p>
								<p>・&nbsp;&nbsp;아주 매움 : 한 입 먹자마자 강렬한 불맛, 맵부심이 도전할 만한 단계</p>
							</div>
						</div>
						
						
						<!-- 카테고리 -->
						<div class="category_field">
							<label>카테고리</label>
							<div>
								<label for="categoryTypeInput" class="sub_label">종류별</label>
								<select id="categoryTypeInput" name="">
									<option value="">선택안함</option>
									<option value="type1">종류1</option>
									<option value="type2">종류2</option>
								</select>
							</div>
							<div>
								<label for="categoryCaseInput" class="sub_label">상황별</label>
								<select id="categoryCaseInput" name="">
									<option value="">선택안함</option>
									<option value="case1">상황1</option>
									<option value="case2">상황2</option>
								</select>
							</div>
							<div>
								<label for="categoryIngredientInput" class="sub_label">재료별</label>
								<select id="categoryIngredientInput" name="">
									<option value="">선택안함</option>
									<option value="ingredient1">재료1</option>
									<option value="ingredient2">재료2</option>
								</select>
							</div>
							<div>
								<label for="categoryWayInput" class="sub_label">방법별</label>
								<select id="categoryWayInput" name="">
									<option value="">선택안함</option>
									<option value="way1">방법1</option>
									<option value="way2">방법2</option>
								</select>
							</div>
						</div>
					</div>
					
					<div class="ingredient_wrap">
						<h2>재료</h2>
						
						<!-- 조리 양 -->
						<div class="serving_field">
							<label>조리 양</label>
							<select id="servingInput" name="">
								<option value="serving1">1인분</option>
								<option value="serving2">2인분</option>
								<option value="serving3">3인분</option>
								<option value="serving4">4인분</option>
								<option value="serving5">5인분</option>
							</select>
							<p id="servingHint">양을 선택해주세요.</p>
						</div>
						
						<!-- 재료 -->
						<div class="ingredient_field">
							<label>재료</label>
							<ul>
								<li>
									<input type="text" id="ingredientInput" name="" value="" placeholder="재료">
									<img src="/images/recipe/commit.png">
									<input type="text" id="amountInput" name="" value="" placeholder="단위가 포함된 양">
									<img src="/images/recipe/commit.png">
									<input type="text" id="noteInput" name="" value="" placeholder="비고 (선택사항)">
								</li>
							</ul>
							<div class="add_btn">
								<a id="addIngredientBtn" href="">재료 추가하기</a>
							</div>
						</div>
					</div>
					
					<div class="step_wrap">
						<h2>조리 순서</h2>
					</div>
					
					<div class="tip_wrap">
						<h2>팁 및 주의사항</h2>
					</div>
					
				</form>
			</div>
		</main>
		
		<%@include file="../common/footer.jsp" %>
	</body>
</html>