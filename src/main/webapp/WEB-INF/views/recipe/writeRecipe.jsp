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
							<select id="categoryTypeInput" name="">
								<option value="">종류별</option>
								<option value="type1">종류1</option>
								<option value="type2">종류2</option>
							</select>
							<select id="categoryCaseInput" name="">
								<option value="">상황별</option>
								<option value="case1">상황1</option>
								<option value="case2">상황2</option>
							</select>
							<select id="categoryIngredientInput" name="">
								<option value="">재료별</option>
								<option value="ingredient1">재료1</option>
								<option value="ingredient2">재료2</option>
							</select>
							<select id="categoryWayInput" name="">
								<option value="">방법별</option>
								<option value="way1">방법1</option>
								<option value="way2">방법2</option>
							</select>
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
						<ul>
							<li>
								<div class="image_field">
									<label>STEP 1</label>
									<input type="file" id="imageInput" name="image" accept="image/*">
								</div>
								<div class="description_field">
									<input type="text" id="descriptionInput" name="" value="" placeholder="조리 방법을 자세히 작성해주세요">
								</div>
							</li>
						</ul>
						<div class="add_btn">
							<a id="addStepBtn" href="">다음 단계 작성하기</a>
						</div>
					</div>
					
					<div class="tip_wrap">
						<h2>팁 및 주의사항</h2>
						
						<!-- 제목 -->
						<div class="tip_field">
							<label>팁 및 주의사항 (선택사항)</label>
							<input type="text" id="tipInput" name="" value="" placeholder="요리팁이나 주의사항을 작성해주세요">
						</div>
						
						<!-- 공지사항 -->
						<div class="notice_info">
							<h5>이런 경우 레시피가 삭제될 수 있어요</h5>
							<p>・&nbsp;&nbsp;존재하지 않는 재료나 조리법을 입력하는 경우</p>
							<p>・&nbsp;&nbsp;부정확한 정보로 인해 많은 사용자가 신고한 경우</p>
							<p>・&nbsp;&nbsp;레시피와 관련 없는 카테고리를 입력하는 경우</p>
							<p>・&nbsp;&nbsp;음란성, 폭력성, 혐오감을 유발하는 내용이 포함된 경우</p>
							<p>・&nbsp;&nbsp;특정 브랜드 홍보, 과도한 링크 삽입, 무관한 광고성 내용이 포함된 경우</p>
						</div>
						
						<!-- 공지사항에 대한 동의 -->
						<div class="accept_notice">
							<div class="checkbox_wrap">
								<input type="checkbox" id="acceptNotice" name="" value="">
								<label for="acceptNotice">유의사항을 모두 확인했으며, 동의합니다.</label>
							</div>
						</div>
					</div>
					
					<!-- 업로드 버튼 -->
					<div class="btn_wrap">
						<button onclick="">취소하기</button>
						<button type="submit" onclick="">업로드하기</button>
					</div>
				</form>
			</div>
		</main>
		
		<%@include file="../common/footer.jsp" %>
	</body>
</html>