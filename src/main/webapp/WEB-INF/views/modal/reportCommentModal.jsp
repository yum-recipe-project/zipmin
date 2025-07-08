<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<form id="reportCommentForm" onsubmit="">
	<input type="hidden" id="reportCommentId" name="id">
	<div class="modal" id="reportCommentModal">
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
									<input type="radio" id="reportCommentReason1" name="reason" value="정당/정치인 비하 및 선거운동">
									<label for="reportCommentReason1">정당/정치인 비하 및 선거운동</label>
								</div>
							</div>
							<div class="form-radio">
								<div>
									<input type="radio" id="reportCommentReason2" name="reason" value="유출/사칭/사기">
									<label for="reportCommentReason2">유출/사칭/사기</label>
								</div>
							</div>
							<div class="form-radio">
								<div>
									<input type="radio" id="reportCommentReason3" name="reason" value="욕설/비하">
									<label for="reportCommentReason3">욕설/비하</label>
								</div>
							</div>
							<div class="form-radio">
								<div>
									<input type="radio" id="reportCommentReason4" name="reason" value="낚시/놀람/도배">
									<label for="reportCommentReason4">낚시/놀람/도배</label>
								</div>
							</div>
							<div class="form-radio">
								<div>
									<input type="radio" id="reportCommentReason5" name="reason" value="상업적 광고 및 판매">
									<label for="reportCommentReason5">상업적 광고 및 판매</label>
								</div>
							</div>
							<div class="form-radio">
								<div>
									<input type="radio" id="reportCommentReason6" name="reason" value="불법촬영물 등의 유통">
									<label for="reportCommentReason6">불법촬영물 등의 유통</label>
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