<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="modal" id="memoToFridgeModal">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h5>장보기 완료한 재료 나의 냉장고에 담기</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
			</div>
			<form id="memoToFridgeForm">
				<div class="modal-body">
						<table>
							<thead>
								<tr>
									<th width="20%">재료</th>
									<th width="12%">용량</th>
									<th width="23%">카테고리</th>
									<th width="23%">보관</th>
									<th width="20%">소비기한</th>
								</tr>
							</thead>
							<tbody class="complete_memo_list"></tbody>
						</table>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-danger" data-bs-dismiss="modal">닫기</button>
					<button type="submit" class="btn btn-primary">재료 담기</button>
				</div>
			</form>
		</div>
	</div>
</div>