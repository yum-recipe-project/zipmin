<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="modal" id="writeFridgeModal">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h5>재료 등록</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
			</div>
			
			<!-- 냉장고 작성 폼 -->
			<form id="writeFridgeForm">
				<div class="modal-body">
				
						<!-- 이미지 선택 영역 -->
						<div class="form-image image_field">
							<label>이미지</label>
							<div class="image_wrap">
								<span class="select_img"></span>
								<button type="button" class="select_btn">
									<p>이미지 선택하기</p>
									<img src="/images/common/arrow_down.png">
								</button>
							</div>
							<p class="danger">이미지를 선택해주세요.</p>
						</div>
						
						<!-- 이미지 목록 영역 -->
						<div class="image_list">
							<ul>
								<li><button><img src="/images/fridge/chicken.png"></button></li>
								<li><button><img src="/images/fridge/pig.png"></button></li>
								<li><button><img src="/images/fridge/cow.png"></button></li>
								<li><button><img src="/images/fridge/fish.png"></button></li>
								<li><button><img src="/images/fridge/apple.png"></button></li>
								<li><button><img src="/images/fridge/grass.png"></button></li>
								<li><button><img src="/images/fridge/carrot.png"></button></li>
								<li><button><img src="/images/fridge/onion.png"></button></li>
								<li><button><img src="/images/fridge/eggplant.png"></button></li>
								<li><button><img src="/images/fridge/milk.png"></button></li>
								<li><button><img src="/images/fridge/ketchup.png"></button></li>
								<li><button><img src="/images/fridge/soysauce.png"></button></li>
								<li><button><img src="/images/fridge/mayonnaise.png"></button></li>
								<li><button><img src="/images/fridge/chili.png"></button></li>
								<li><button><img src="/images/fridge/almond.png"></button></li>
								<li><button><img src="/images/fridge/dinner.png"></button></li>
								<li><button><img src="/images/fridge/cutlery.png"></button></li>
							</ul>
							<input type="hidden" name="image">
						</div>
						
						<!-- 재료명 -->			
						<div class="form-group name_field">
							<label>재료명</label>
							<input type="text" name="name" placeholder="재료명 입력" class="form-control">
							<p class="danger">재료명을 입력해주세요.</p>
						</div>
						
						<!-- 카테고리 -->
						<div class="form-group category_field">
							<label>카테고리</label>
							<select class="form-select">
					            <option value="" disabled selected hidden="true">카테고리 선택</option>
					            <option value="육류">육류</option>
					            <option value="어패류">어패류</option>
					            <option value="채소류">채소류</option>
					            <option value="과일류">과일류</option>
					            <option value="견과류">견과류</option>
					            <option value="유제품">유제품</option>
					            <option value="완제품">완제품</option>
					            <option value="소스류">소스류</option>
					            <option value="기타">기타</option>
					        </select>
					        <input type="hidden" name="category">
					        <p class="danger">카테고리를 입력해주세요.</p>
						</div>
						
						<!-- 보관 장소 -->
						<div class="form-group zone_field">
							<label>보관 장소</label>
							<div class="form-radio">
								<div>
									<input type="radio" id="writeFridgeZone1" name="zoneRadio" value="냉장">
									<label for="writeFridgeZone1">냉장</label>
								</div>
								<div>
									<input type="radio" id="writeFridgeZone2" name="zoneRadio" value="냉동">
									<label for="writeFridgeZone2">냉동</label>
								</div>
								<div>
									<input type="radio" id="writeFridgeZone3" name="zoneRadio" value="실온">
									<label for="writeFridgeZone3">실온</label>
								</div>
							</div>
							<input type="hidden" name="zone">
							<p class="danger">보관 장소를 입력해주세요.</p>
						</div>
					</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-danger" data-bs-dismiss="modal">닫기</button>
					<button type="submit" class="btn btn-primary">등록하기</button>
				</div>
			</form>
		</div>
	</div>
</div>
