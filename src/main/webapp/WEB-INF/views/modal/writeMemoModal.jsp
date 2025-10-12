<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<form id="writeMemoForm">
	<div class="modal" id="writeMemoModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5>장보기메모에 재료 담기</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<table>
							<thead>
								<tr>
									<th width="30%">재료명</th>
									<th width="30%">용량</th>
									<th width="40%">비고</th>
								</tr>
							</thead>
							<tbody class="memo_tbody" id="memo">
								<tr>
									<td><input type="text" name="name" placeholder="재료명" /></th>
									<td><input type="text" name="amount" placeholder="단위를 포함한 양" /></th>
									<td><input type="text" name="note" placeholder="비고(선택사항)" /></th>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-danger" data-bs-dismiss="modal">닫기</button>
					<button type="submit" class="btn btn-primary">재료 담기</button>
				</div>
			</div>
		</div>
	</div>
</form>