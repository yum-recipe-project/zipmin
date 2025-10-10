<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<form id="withdrawPointForm" onsubmit="">
	<div class="modal" id="withdrawPointModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5>출금하기</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
				</div>
				<div class="modal-body">
					<table class="point">
						<thead>
							<tr>
								<th><p>수익 포인트</p></th>
								<th><p>출금할 포인트</p></th>
								<th><p>남은 포인트</p></th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td id="ownedPoint"></td>
								<td>
									<div class="form-input">
										<input id="pointInput" type="number" value="10000" min="10000" step="1">
									</div>
								</td>
								<td id="remainPoint"></td>
							</tr>
						</tbody>
					</table>
					<div class="form-info">
						<p class="withdraw_info">* 출금은 1000포인트부터 가능합니다.</p><br/>
						<p>
							본 사이트는 개인 포트폴리오를 위한 비상업적 웹사이트로, 연구 및 실험 목적만을 위해 운영됩니다.
							모든 기능은 수익 창출과 무관하며 후원된 금액은 자동으로 전액 환불되었으므로 포인트는 정산되지 않습니다.
						</p>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-danger" data-bs-dismiss="modal">닫기</button>
					<button type="submit" class="btn btn-primary">출금하기</button>
				</div>
			</div>
		</div>
	</div>
</form>