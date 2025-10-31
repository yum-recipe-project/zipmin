<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<form id="reportRecipeForm" onsubmit="">
	<div class="modal" id="reportRecipeModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5>신고하기</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
				</div>
				<div class="modal-body">
					<label>레시피 신고 사유</label>
					<div class="report">
						<div class="reason">
							<div class="form-radio">
								<div>
									<input type="radio" id="reportRecipeReason1" name="reason" value="정당/정치인 비하 및 선거운동">
									<label for="reportRecipeReason1">정당/정치인 비하 및 선거운동</label>
								</div>
							</div>
							<div class="form-radio">
								<div>
									<input type="radio" id="reportRecipeReason2" name="reason" value="유출/사칭/사기">
									<label for="reportRecipeReason2">유출/사칭/사기</label>
								</div>
							</div>
							<div class="form-radio">
								<div>
									<input type="radio" id="reportRecipeReason3" name="reason" value="욕설/비하">
									<label for="reportRecipeReason3">욕설/비하</label>
								</div>
							</div>
							<div class="form-radio">
								<div>
									<input type="radio" id="reportRecipeReason4" name="reason" value="낚시/놀람/도배">
									<label for="reportRecipeReason4">낚시/놀람/도배</label>
								</div>
							</div>
							<div class="form-radio">
								<div>
									<input type="radio" id="reportRecipeReason5" name="reason" value="상업적 광고 및 판매">
									<label for="reportRecipeReason5">상업적 광고 및 판매</label>
								</div>
							</div>
							<div class="form-radio">
								<div>
									<input type="radio" id="reportRecipeReason6" name="reason" value="불법촬영물 등의 유통">
									<label for="reportRecipeReason6">불법촬영물 등의 유통</label>
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