<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<form id="addMemoForm" onsubmit="return validateAddMemoForm();">
	<div class="modal" id="addMemoModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5>장보기메모에 재료 담기</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
				</div>
				<div class="modal-body">
					<table>
						<thead>
							<tr>
								<th width="43%">재료</th>
								<th width="43%">용량</th>
								<th width="14%">선택</th>
							</tr>
						</thead>
						<tbody id="memo"></tbody>
					</table>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-danger" data-bs-dismiss="modal">닫기</button>
					<button type="submit" class="btn btn-primary">재료 담기</button>
				</div>
			</div>
		</div>
	</div>
</form>