<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<form id="reportMegazineCommentForm" onsubmit="return validateReportMegazineCommentForm();">
	<div class="modal" id="reportMegazineCommentModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5>신고하기</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
				</div>
				<div class="modal-body">
					<label>댓글 신고 사유</label>
					<div class="report">
						<div class="reason">
							<div class="form-radio">
								<div>
									<input type="radio" id="commentReason1" name="reason" value="">
									<label for="commentReason1">정당/정치인 비하 및 선거운동</label>
								</div>
							</div>
							<div class="form-radio">
								<div>
									<input type="radio" id="commentReason2" name="reason" value="">
									<label for="commentReason2">유출/사칭/사기</label>
								</div>
							</div>
							<div class="form-radio">
								<div>
									<input type="radio" id="commentReason3" name="reason" value="">
									<label for="commentReason3">욕설/비하</label>
								</div>
							</div>
							<div class="form-radio">
								<div>
									<input type="radio" id="commentReason4" name="reason" value="">
									<label for="commentReason4">낚시/놀람/도배</label>
								</div>
							</div>
							<div class="form-radio">
								<div>
									<input type="radio" id="commentReason5" name="reason" value="">
									<label for="commentReason5">상업적 광고 및 판매</label>
								</div>
							</div>
							<div class="form-radio">
								<div>
									<input type="radio" id="commentReason6" name="reason" value="">
									<label for="commentReason6">불법촬영물 등의 유통</label>
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