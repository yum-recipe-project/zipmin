<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<form id="topUpPointForm" onsubmit="">
	<input type="hidden" id="ownedPoint" >
	<div class="modal" id="topUpPointModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5>충전하기</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
				</div>
				<div class="modal-body">
					<label>충전할 포인트</label>
					<div class="topup">
						<div class="point">
							<div class="form-radio">
								<div>
									<input type="radio" id="point1" name="point" value="1000">
									<label for="point1">1,000P</label>
								</div>
								<p>1,000원</p>
							</div>
							<div class="form-radio">
								<div class="radio">
									<input type="radio" id="point2" name="point" value="3000">
									<label for="point2">3,000P</label>
								</div>
								<p>3,000원</p>
							</div>
							<div class="form-radio">
								<div>
									<input type="radio" id="point3" name="point" value="5000">
									<label for="point3">5,000P</label>
								</div>
								<p>5,000원</p>
							</div>
							<div class="form-radio">
								<div>
									<input type="radio" id="point4" name="point" value="10000">
									<label for="point4">10,000P</label>
								</div>
								<p>10,000원</p>
							</div>
						</div>
						<div class="form-result">
							<p>충전 후 예상 보유 포인트</p>
							<p><em id="totalPoint"></em></p>
						</div>
						<div class="form-info">
							<p>
								본 사이트는 개인 포트폴리오를 위한 비상업적 웹사이트로, 연구 및 실험 목적만을 위해 운영됩니다.
								모든 기능은 수익 창출과 무관하며 결제된 금액은 자동으로 전액 환불됩니다.
							</p>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-danger" data-bs-dismiss="modal">닫기</button>
					<button type="submit" class="btn btn-primary disabled">충전하기</button>
				</div>
			</div>
		</div>
	</div>
</form>