<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<form id="addFridgeForm">
	<div class="modal" id="addFridgeModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5>재료 추가하기</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
				</div>
				
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
						<p class="danger">이미지를 입력해주세요.</p>
					</div>
					
					<!-- 이미지 목록 영역 -->
					<div class="image_list">
						<ul>
							<li><button><img src="/images/fridge/chicken.png"></button></li>
							<li><button><img src="/images/fridge/pig.png"></button></li>
							<li><button><img src="/images/fridge/cow.png"></button></li>
							<li><button><img src="/images/fridge/grass.png"></button></li>
							<li><button><img src="/images/fridge/carrot.png"></button></li>
							<li><button><img src="/images/fridge/onion.png"></button></li>
							<li><button><img src="/images/fridge/eggplant.png"></button></li>
							<li><button><img src="/images/fridge/ketchup.png"></button></li>
							<li><button><img src="/images/fridge/soysauce.png"></button></li>
							<li><button><img src="/images/fridge/mayonnaise.png"></button></li>
							<li><button><img src="/images/fridge/chili.png"></button></li>
						</ul>
						<input type="hidden" name="image">
					</div>
				
					<div class="form-group name_field">
						<label>재료명</label>
						<input type="text" name="name" placeholder="재료명 입력" class="form-control">
						<p class="danger">재료명을 입력해주세요.</p>
					</div>
					
					<div class="form-group category_field">
						<label>카테고리</label>
						<select class="form-select">
				            <option value="">- 카테고리를 선택하세요 -</option>
				            <option value="육류">육류</option>
				            <option value="채소류">채소류</option>
				            <option value="소스류">소스류</option>
				            <option value="기타">기타</option>
				        </select>
				        <input type="hidden" name="category">
				        <p class="danger">카테고리를 입력해주세요.</p>
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