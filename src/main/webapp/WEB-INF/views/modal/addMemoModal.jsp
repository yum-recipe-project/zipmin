<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!-- <form id="addMemoForm" onsubmit="return validateAddMemoForm();"> -->
<form id="addMemoForm">
	<div class="modal" id="addMemoModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5>장보기메모에 재료 담기</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
				</div>
				<div class="modal-body">
					<table class="memo">
						<thead>
							<tr>
								<th width="43%">재료</th>
								<th width="43%">용량</th>
								<th width="14%">선택</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td><input type="text" name="name" placeholder="재료"></td>
								<td><input type="text" name="amount" placeholder="단위가 포함된 양"></td>
								<td><input type="text" name="note" placeholder="비고 (선택사항)"></td>
							</tr>
						</tbody>
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