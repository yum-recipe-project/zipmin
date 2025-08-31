<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<form id="addUserFridgeForm">
	<div class="modal" id="addUserFridgeModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5>냉장고 채우기</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
				</div>
				
				<div class="modal-body pb-0">
				
					<!-- 검색창 -->
					<div class="search_box" data-type="fridge">
						<input type="text" class="search_word" placeholder="재료를 검색하세요">
						<button type="button" class="search_btn">
							<img src="/images/common/search.png">
						</button>
					</div>
					
					<!-- 탭 메뉴 버튼 -->
					<div class="tab_button_wrap">
						<button class="tab_button active" data-tab="like-fridge">즐겨찾기</button>
						<button class="tab_button" data-tab="add-fridge">직접 등록</button>
					</div>
					
				</div>
				<div class="modal-body body-wide">
					<div class="tab_content">
						<div class="fridge_util">
							<p class="fridge_total"></p>
							<button type="button" class="btn btn-dark" data-bs-toggle="modal" data-bs-target="#addFridgeModal">재료 등록</button>
						</div>
						<ul class="fridge_list"></ul>
					</div>
				</div>
				
				<div class="modal-footer footer-wide"></div>
			</div>
		</div>
	</div>
</form>