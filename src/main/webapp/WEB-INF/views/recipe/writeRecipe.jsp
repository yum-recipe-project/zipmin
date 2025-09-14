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
				<form id="writeRecipeForm" enctype="multipart/form-data">
					<div class="recipe_basic_wrap">
						<h2>맛있는 한 끼를 위한<br/>레시피를 소개해주세요!</h2>
	
						<!-- 대표 이미지 -->
						<div class="form-image image_field">
							<label>대표 이미지</label>
							<input type="file" name="image" accept="image/*">
							<p>대표 이미지를 첨부해주세요.</p>
						</div>
					
						<!-- 제목 -->
						<div class="form-group title_field">
							<label>제목</label>
							<input type="text" name="title" placeholder="요리 제목을 작성해주세요">
							<p>제목을 작성해주세요.</p>
						</div>
						
						<!-- 소개 -->
						<div class="form-group introduce_field">
							<label>소개</label>
							<input type="text" name="introduce" placeholder="소개를 작성해주세요">
							<p>소개를 작성해주세요.</p>
						</div>
						
						<!--  난이도 -->
						<div class="form-group level_field">
							<label>난이도<img src="/images/recipe/help_666.png"></label>
							<div class="form-radio">
								<div>
									<input type="radio" id="writeRecipeCooklevel1" name="cooklevel" value="입문">
									<label for="writeRecipeCooklevel1">입문</label>
								</div>
								<div>
									<input type="radio" id="writeRecipeCooklevel2" name="cooklevel" value="초급">
									<label for="writeRecipeCooklevel2">초급</label>
								</div>
								<div>
									<input type="radio" id="writeRecipeCooklevel3" name="cooklevel" value="중급">
									<label for="writeRecipeCooklevel3">중급</label>
								</div>
								<div>
									<input type="radio" id="writeRecipeCooklevel4" name="cooklevel" value="고급">
									<label for="writeRecipeCooklevel4">고급</label>
								</div>
							</div>
							<input type="hidden" name="cooklevel">
							<p>난이도를 선택해주세요.</p>
							<!-- TODO : Tooltip으로 변경 -->
							<!-- <div class="notice">
								<p>・&nbsp;&nbsp;입문 : 요리를 처음 해보는 사람들을 위한 단계</p>
								<p>・&nbsp;&nbsp;초급 : 기본적인 칼질과 간단한 조리법을 익힌 단계</p>
								<p>・&nbsp;&nbsp;중급 : 조리와 불 조절을 능숙하게 할 수 있는 단계</p>
								<p>・&nbsp;&nbsp;고급 : 맛 조절과 재료 조합을 잘 이해하여 고급 요리 테크닉을 활용할 수 있는 단계</p>
							</div> -->
						</div>
						
						<!-- 조리시간 -->
						<div class="form-group time_field">
							<label>조리시간</label>
							<div class="form-radio">
								<div>
									<input type="radio" id="writeRecipeCooktime1" name="cooktime" value="10분">
									<label for="writeRecipeCooktime1">10분</label>
								</div>
								<div>
									<input type="radio" id="writeRecipeCooktime2" name="cooktime" value="30분">
									<label for="writeRecipeCooktime2">30분</label>
								</div>
								<div>
									<input type="radio" id="writeRecipeCooktime3" name="cooktime" value="1시간">
									<label for="writeRecipeCooktime3">1시간</label>
								</div>
								<div>
									<input type="radio" id="writeRecipeCooktime4" name="cooktime" value="1시간 30분">
									<label for="writeRecipeCooktime4">1시간 30분</label>
								</div>
								<div>
									<input type="radio" id="writeRecipeCooktime5" name="cooktime" value="2시간 이상">
									<label for="writeRecipeCooktime5">2시간 이상</label>
								</div>
							</div>
							<input type="hidden" name="cooktime">
							<p>조리시간을 선택해주세요.</p>
						</div>
						
						<!-- 맵기정도 -->
						<div class="form-group spicy_field">
							<label>맵기 정도<img src="/images/recipe/help_666.png"></label>
							<div class="form-radio">
								<div>
									<input type="radio" id="writeRecipeSpicy1" name="spicy" value="아주 안매움">
									<label for="writeRecipeSpicy1">아주 안매움</label>
								</div>
								<div>
									<input type="radio" id="writeRecipeSpicy2" name="spicy" value="안매움">
									<label for="writeRecipeSpicy2">안매움</label>
								</div>
								<div>
									<input type="radio" id="writeRecipeSpicy3" name="spicy" value="보통">
									<label for="writeRecipeSpicy3">보통</label>
								</div>
								<div>
									<input type="radio" id="writeRecipeSpicy4" name="spicy" value="매움">
									<label for="writeRecipeSpicy4">매움</label>
								</div>
								<div>
									<input type="radio" id="writeRecipeSpicy5" name="spicy" value="아주 매움">
									<label for="writeRecipeSpicy5">아주 매움</label>
								</div>
							</div>
							<input type="hidden" name="spicy">
							<p>맵기 정도를 선택해주세요.</p>
							<!-- TODO : Tooltip으로 변경 -->
							<!-- <div class="notice">
								<p>・&nbsp;&nbsp;아주 안매움 : 매운맛이 전혀 없어 아이들도 먹을 수 있는 단계</p>
								<p>・&nbsp;&nbsp;안매움 : 고춧가루가 살짝 들어갔지만 맵지 않은 단계</p>
								<p>・&nbsp;&nbsp;보통 : 신라면 정도로, 매운맛 초보도 큰 부담 없이 즐길 수 있는 단계</p>
								<p>・&nbsp;&nbsp;매움 : 불닭볶음면 정도로, 매운 음식 마니아들이 즐기는 단계</p>
								<p>・&nbsp;&nbsp;아주 매움 : 한 입 먹자마자 강렬한 불맛, 맵부심이 도전할 만한 단계</p>
							</div> -->
						</div>
						
						<!-- 카테고리 -->
						<div class="form-group category_field">
							<label>카테고리</label>
							<div class="select-group">
								<select class="form-select">
									<option value="" disabled selected hidden="true">종류별</option>
									<option value="밑반찬">밑반찬</option>
									<option value="메인반찬">메인반찬</option>
									<option value="국/탕">국/탕</option>
									<option value="찌개">찌개</option>
									<option value="디저트">디저트</option>
									<option value="면/만두">면/만두</option>
									<option value="밥/죽/떡">밥/죽/떡</option>
									<option value="퓨전">퓨전</option>
									<option value="김치/젓갈/장류">김치/젓갈/장류</option>
									<option value="양념/소스/잼">양념/소스/잼</option>
									<option value="양식">양식</option>
									<option value="중식">중식</option>
									<option value="샐러드">샐러드</option>
									<option value="스프">스프</option>
									<option value="빵">빵</option>
									<option value="과자">과자</option>
									<option value="차/음료/술">차/음료/술</option>
									<option value="기타">기타</option>
								</select>
								<input type="hidden" name="categoryType">
								<select class="form-select" id="categoryCaseSelectbox">
									<option value="" disabled selected hidden="true">상황별</option>
									<option value="일상">일상</option>
									<option value="초스피드">초스피드</option>
									<option value="손님접대">손님접대</option>
									<option value="술안주">술안주</option>
									<option value="다이어트">다이어트</option>
									<option value="축하">축하</option>
									<option value="도시락">도시락</option>
									<option value="영양식">영양식</option>
									<option value="간식">간식</option>
									<option value="야식">야식</option>
									<option value="푸드스타일링">푸드스타일링</option>
									<option value="해장">해장</option>
									<option value="명절">명절</option>
									<option value="이유식">이유식</option>
									<option value="기타">기타</option>
								</select>
								<input type="hidden" name="categoryCase">
								<select class="form-select" id="categoryIngredientSelectbox">
									<option value="" disabled selected hidden="true">재료별</option>
									<option value="소고기">소고기</option>
									<option value="돼지고기">돼지고기</option>
									<option value="닭고기">닭고기</option>
									<option value="육류">육류</option>
									<option value="채소류">채소류</option>
									<option value="해물류">해물류</option>
									<option value="달걀/유제품">달걀/유제품</option>
									<option value="가공식품류">가공식품류</option>
									<option value="쌀">쌀</option>
									<option value="밀가루">밀가루</option>
									<option value="건어물류">건어물류</option>
									<option value="버섯류">버섯류</option>
									<option value="과일류">과일류</option>
									<option value="콩/견과류">콩/견과류</option>
									<option value="곡류">곡류</option>
									<option value="기타">기타</option>
								</select>
								<input type="hidden" name="categoryIngredient">
								<select class="form-select" id="categoryWaySelectbox">
									<option value="" disabled selected hidden="true">방법별</option>
									<option value="볶음">볶음</option>
									<option value="끓이기">끓이기</option>
									<option value="부침">부침</option>
									<option value="조림">조림</option>
									<option value="비빔">비빔</option>
									<option value="찜">찜</option>
									<option value="절임">절임</option>
									<option value="튀김">튀김</option>
									<option value="삶기">삶기</option>
									<option value="굽기">굽기</option>
									<option value="데치기">데치기</option>
									<option value="회">회</option>
									<option value="기타">기타</option>
								</select>
								<input type="hidden" name="categoryWay">
							</div>
							<p>카테고리를 선택해주세요.</p>
						</div>
						
						<!-- 제목 -->
						<div class="form-group tip_field">
							<label>팁 및 주의사항 (선택사항)</label>
							<input type="text" name="tip" placeholder="요리팁이나 주의사항을 작성해주세요">
						</div>
					</div>
					
					<div class="ingredient_wrap">
						<h2>재료</h2>
						
						<!-- 조리 양 -->
						<div class="serving_field">
							<label>조리 양</label>
							<select>
								<option value="">조리 양을 선택하세요</option>
								<option value="1인분">1인분</option>
								<option value="2인분">2인분</option>
								<option value="3인분">3인분</option>
								<option value="4인분">4인분</option>
								<option value="5인분">5인분</option>
							</select>
							<input type="hidden" name="serving">
							<p>양을 선택해주세요.</p>
						</div>
						
						<!-- 재료 -->
						<div class="ingredient_field">
							<label>재료</label>
							<ul>
								<li class="ingredient_row">
									<div class="ingredient_flex">
										<input type="text" class="ingredient_input" name="" value="" placeholder="재료">
										<img src="/images/recipe/commit.png">
										<input type="text" class="ingredient_amount_input" name="" value="" placeholder="단위가 포함된 양">
										<img src="/images/recipe/commit.png">
										<input type="text" class="ingredient_note_input" name="" value="" placeholder="비고 (선택사항)">
									</div>
									<p class="ingredient_hint">재료를 입력해주세요.</p>
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
							<li class="step_row">
								<div class="step_image_field">
									<label>STEP 1</label>
									<input type="file" class="step_image_input" name="image" accept="image/*">
								</div>
								<div class="step_field">
									<input type="text" class="step_input" name="" value="" placeholder="조리 방법을 자세히 작성해주세요">
								</div>
								<p class="step_hint">조리 순서를 입력해주세요.</p>
							</li>
						</ul>
						<div class="add_btn">
							<a id="addStepBtn" href="">다음 단계 작성하기</a>
						</div>
					</div>
					
					<div class="tip_wrap">
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
								<input type="checkbox" id="acceptNotice" name="notice">
								<label for="acceptNotice">유의사항을 모두 확인했으며, 동의합니다.</label>
							</div>
						</div>
					</div>
					
					<!-- 업로드 버튼 -->
					<div class="btn_wrap">
						<button class="btn_outline" onclick="">취소하기</button>
						<button class="btn_primary" type="submit" onclick="">업로드하기</button>
					</div>
				</form>
			</div>
		</main>
		
		<%@include file="../common/footer.jsp" %>
	</body>
</html>