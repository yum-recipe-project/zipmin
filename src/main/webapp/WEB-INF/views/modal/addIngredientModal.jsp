<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<form id="addIngredientForm" onsubmit="return validateAddMemoForm();">
	<div class="modal" id="addIngredientModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5>재료 추가하기</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
				</div>
				<div class="modal-body">
				
					<!-- 음식 이미지 -->
					<div class="category_img">
						<span class="title">이미지</span>
						<div class="img_select">
							<span class="ingredient_img"></span>
							<button class="add_btn" id="ingredientAddBtn" type="button">
							    <p id="ingredientState">이미지 선택하기</p>
							    <img id="ingredientIcon" src="/images/common/arrow_down.png" alt="수정">
							</button>
						</div>
					</div>
					
					<!-- 이미지 선택 -->
					<div class="img_select_wrap" id="imageSelectWrap">
						<div class="img_wrap">
							<span class="title">육류</span>
							<ul class="img_list">
								<li>
									<button class="img_btn">
										<img src="/images/fridge/chicken.png">
									</button>
								</li>
								<li>
									<button class="img_btn">
										<img src="/images/fridge/pig.png">
									</button>
								</li>
								<li>
									<button class="img_btn">
										<img src="/images/fridge/cow.png">
									</button>
								</li>
							</ul>
						</div>
						<div class="img_wrap">
							<span class="title">채소류</span>
							<ul class="img_list">
								<li>
									<button class="img_btn">
										<img src="/images/fridge/grass.png">
									</button>
								</li>
								<li>
									<button class="img_btn">
										<img src="/images/fridge/carrot.png">
									</button>
								</li>
								<li>
									<button class="img_btn">
										<img src="/images/fridge/onion.png">
									</button>
								</li>
								<li>
									<button class="img_btn">
										<img src="/images/fridge/eggplant.png">
									</button>
								</li>
							</ul>
						</div>
						<div class="img_wrap">
							<span class="title">소스류</span>
							<ul class="img_list">
								<li>
									<button class="img_btn">
										<img src="/images/fridge/ketchup.png">
									</button>
								</li>
								<li>
									<button class="img_btn">
										<img src="/images/fridge/soysauce.png">
									</button>
								</li>
								<li>
									<button class="img_btn">
										<img src="/images/fridge/mayonnaise.png">
									</button>
								</li>
								<li>
									<button class="img_btn">
										<img src="/images/fridge/chili.png">
									</button>
								</li>
							</ul>
						</div>
					</div>
					
					<div class="input_wrap">
						<span class="title">재료명</span>
						<input type="text" id="nameInput" name="" value="" placeholder="재료명" class="form-control">
					</div>
					<div class="input_wrap">
						<span class="title">소비기한</span>
						<input type="date" id="dateInput" class="form-control">
					</div>
					<div class="input_wrap">
						<span class="title">용량</span>
						<input type="text" id="amountInput" name="" value="" placeholder="300g" class="form-control">
					</div>
					<div class="input_wrap">
						<span class="title">카테고리</span>
						<select name="category" class="form-select">
				            <option value="meat">육류</option>
				            <option value="vegetable">채소류</option>
				            <option value="sauce">소스류</option>
				            <option value="etc">기타</option>
				        </select>
					</div>
					
					
					
					
				
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-danger" data-bs-dismiss="modal">닫기</button>
					<button type="submit" class="btn btn-primary">재료 추가하기</button>
				</div>
			</div>
		</div>
	</div>
</form>