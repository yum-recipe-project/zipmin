<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<form id="reportReviewForm" onsubmit="">
	<div class="modal" id="reportReviewModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5>신고하기</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
				</div>
				<div class="modal-body">
					<label>키친 한마디 신고 사유</label>
					<div class="report">
						<div class="reason">
							<div class="form-radio">
								<div>
									<input type="radio" id="reportReviewReason1" name="reason" value="">
									<label for="reportReviewReason1">정당/정치인 비하 및 선거운동</label>
								</div>
							</div>
							<div class="form-radio">
								<div>
									<input type="radio" id="reportReviewReason2" name="reason" value="">
									<label for="reportReviewReason2">유출/사칭/사기</label>
								</div>
							</div>
							<div class="form-radio">
								<div>
									<input type="radio" id="reportReviewReason3" name="reason" value="">
									<label for="reportReviewReason3">욕설/비하</label>
								</div>
							</div>
							<div class="form-radio">
								<div>
									<input type="radio" id="reportReviewReason4" name="reason" value="">
									<label for="reportReviewReason4">낚시/놀람/도배</label>
								</div>
							</div>
							<div class="form-radio">
								<div>
									<input type="radio" id="reportReviewReason5" name="reason" value="">
									<label for="reportReviewReason5">상업적 광고 및 판매</label>
								</div>
							</div>
							<div class="form-radio">
								<div>
									<input type="radio" id="reportReviewReason6" name="reason" value="">
									<label for="reportReviewReason6">불법촬영물 등의 유통</label>
								</div>
							</div>
						</div>
					</div>
					<div class="form-info">
						<p>
							신고는 반대의견을 나타내는 기능이 아닙니다.
							신고 사유에 맞지 않는 신고를 했을 경우, 해당 신고는 처리되지 않습니다.
						</p>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-danger" data-bs-dismiss="modal">닫기</button>
					<button type="submit" class="btn btn-primary disabled">신고하기</button>
				</div>
			</div>
		</div>
	</div>
</form>