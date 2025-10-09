<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<form id="supportRecipeForm" onsubmit="">
	<div class="modal" id="supportRecipeModal">
		<input type="hidden" id="fundeeIdInput">
		
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5>후원하기</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
				</div>
				<div class="modal-body">
					<table class="point">
						<thead>
							<tr>
								<th><p>보유 포인트</p></th>
								<th><p>사용할 포인트</p></th>
								<th><p>남은 포인트</p></th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td id="ownedPoint"></td>
								<td>
									<div class="form-input">
										<input id="pointInput" type="number" value="100" min="100" step="1">
									</div>
								</td>
								<td id="remainPoint"></td>
							</tr>
						</tbody>
					</table>
					<div class="form-box">
						<div class="message">
							<p>포인트 충전하기</p>
							<p>포인트를 충전하고 레시피를 후원해 보세요.</p>
						</div>
						<button type="button" data-bs-toggle="modal" data-bs-target="#topUpPointModal"
							onclick="">
							포인트 충전
						</button>
					</div>
					<div class="form-info">
						<p>
							본 사이트는 개인 포트폴리오를 위한 비상업적 웹사이트로, 연구 및 실험 목적만을 위해 운영됩니다.
							모든 기능은 수익 창출과 무관하며 후원된 금액은 자동으로 전액 환불됩니다.
						</p>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-danger" data-bs-dismiss="modal">닫기</button>
					<button type="submit" class="btn btn-primary">후원하기</button>
				</div>
			</div>
		</div>
	</div>
</form>