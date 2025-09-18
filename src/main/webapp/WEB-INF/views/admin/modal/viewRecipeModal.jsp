<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="modal" id="viewRecipeModal">
	<div class="modal-dialog modal-lg modal-dialog-scrollable">
		<div class="modal-content">
			<div class="modal-header d-flex align-items-center">
				<h5>레시피 상세보기</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
			</div>
			<div class="modal-body">
				<div class="recipe_wrap">
					<div id="viewRecipeBasicForm" class="recipe_header">
						<!-- 제목 -->
						<h2 class="recipe_title"></h2>
						
						<!-- 레시피 정보 -->
						<div class="recipe_info">
							<div class="info">
								<img src="/images/recipe/level.png">
								<p class="recipe_cooklevel"></p>
							</div>
							<div class="info">
								<img src="/images/recipe/time.png">
								<p class="recipe_cooktime"></p>
							</div>
							<div class="info">
								<img src="/images/recipe/spicy.png">
								<p class="recipe_spicy"></p>
							</div>
						</div>
						
						<!-- 작성자 -->
						<div class="recipe_writer">
							<button type="button" class="btn_nickname">
								<img src="/images/common/test.png">
								<span>알 수 없는 사용자</span>
							</button>
						</div>
						
						<!-- 소개 -->
						<p class="recipe_introduce"></p>
						
						<!-- 카테고리 -->
						<div class="recipe_category"></div>
					</div>
					
					<div id="viewRecipeStockForm" class="recipe_stock">
						<h3>재료</h3>
						
						<!-- 양 -->
						<p class="recipe_portion"></p>
						
						<!-- 재료 표 -->
						<table class="stock_list">
							<thead>
								<tr>
									<th width="33%">재료</th>
									<th width="33%">용량</th>
									<th width="33%">비고</th>
								</tr>
							</thead>
							<tbody class="stock"></tbody>
						</table>
					</div>
					
					<div id="viewRecipeStepForm" class="recipe_step">
						<!-- 제목 -->
						<h3>조리 순서</h3>
						
						<!-- 조리 과정 목록 -->
						<ul class="step_list"></ul>
					</div>
					
					<!-- 요리팁 -->
					<div id="viewRecipeTipForm">
						<div class="recipe_tip">
							<h3>주의사항</h3>
							<p></p>
						</div>
					</div>
					
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-light px-4" data-bs-dismiss="modal">닫기</button>
			</div>
		</div>
	</div>
</div>




