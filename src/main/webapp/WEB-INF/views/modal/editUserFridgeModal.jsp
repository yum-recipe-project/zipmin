<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="modal" id="editUserFridgeModal">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h5>냉장고 재료 수정하기</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
			</div>
			<form id="editUserFridgeForm">
				<div class="modal-body">
						<input type="hidden" name="id">
						<div class="fridge">
							<span class="fridge_image"></span>
							<div>
								<h3 class="fridge_name">닭가슴살</h3>
								<p class="fridge_category"></p>
							</div>
						</div>
						<div class="form-group amount_field">
							<label>용량</label>
							<input type="text" name="amount" placeholder="예) 300g" class="form-control">
							<p class="danger" id="editUserFridgeAmountHint1">소비기한을 입력해주세요</p>
							<p class="danger" id="editUserFridgeAmountHint2">입력 형식이 올바르지 않습니다</p>
						</div>
						<div class="form-group expdate_field">
							<label>소비기한</label>
							<input type="date" name="expdate" class="form-control">
							<p class="danger">소비기한을 입력해주세요</p>
						</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-danger" data-bs-dismiss="modal">닫기</button>
					<button type="submit" class="btn btn-primary">수정하기</button>
				</div>
			</form>
		</div>
	</div>
</div>