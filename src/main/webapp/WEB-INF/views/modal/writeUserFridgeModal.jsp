<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="modal" id="writeUserFridgeModal">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h5>냉장고 채우기</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
			</div>
			
			<div class="modal-body pb-0">
				<!-- 검색창 -->
				<form class="search_form" data-type="fridge">
					<input type="text" class="search_word" placeholder="재료를 검색하세요">
					<button type="submit" class="search_btn">
						<img src="/images/common/search.png">
					</button>
				</form>
				
				<!-- 탭 메뉴 버튼 -->
				<div class="tab_button_wrap">
					<button class="tab_button active" data-tab="like-fridge">즐겨찾기</button>
					<button class="tab_button" data-tab="add-fridge">직접 등록</button>
				</div>
			</div>
			
			<div class="modal-body body-wide">
				<!-- 냉장고 재료 목록 -->
				<div class="tab_content">
					<div class="fridge_util">
						<p class="fridge_total"></p>
						<button type="button" class="btn btn-dark" data-bs-toggle="modal" data-bs-target="#writeFridgeModal">재료 등록</button>
					</div>
					<ul class="fridge_list"></ul>
				</div>
				
				<!-- 냉장고 재료 하단 시트 -->
				<form id="writeUserFridgeForm">
					<div class="fridge_sheet" id="fridgeSheet">
						<div class="sheet_grip"></div>
						<div class="sheet_head">
							<div class="sheet_info">
								<div class="sheet_title"></div>
								<div class="sheet_category">고기</div>
							</div>
							<button type="button" id="deleteFridgeButton" class="btn">삭제</button>
						</div>
						<div>
							<input type="hidden" name="fridgeId">
							<div class="form-group">
								<label>양</label>
								<input type="text" name="amount" class="form-control" placeholder="예: 300g">
								<p id="sheetAmountHint1" class="danger">양을 입력해주세요</p>
								<p id="sheetAmountHint2" class="danger">입력 형식이 올바르지 않습니다</p>
							</div>
							<div class="form-group expdate_field">
								<label>유통기한</label>
								<input type="date" name="expdate" class="form-control">
								<p id="sheetExpdateHint" class="danger">유통기간을 입력해주세요</p>
							</div>
							<div class="sheet_action">
								<button type="button" class="btn" id="sheetCloseBtn">닫기</button>
								<button type="submit" class="btn btn-primary">냉장고 채우기</button>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>