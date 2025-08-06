<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<form id="applyClassForm" method="post">
	<input type="hidden" id="applyClassId" name="id">
	<div class="modal" id="applyClassModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5>한식 입문 클래스</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<label>1. 클래스를 신청하게 된 동기와 이유를 적어주세요 (500자 이내)</label>
						<textarea class="form-control" id="applyClassReasonInput" name="reason" style="height: 90px;"></textarea>
					</div>
					<div class="form-group">
						<label>2. 강사님께 궁금한 점이 있다면 적어주세요 (선택사항)</label>
						<textarea class="form-control" id="applyClassQuestionInput" name="question" style="height: 90px;"></textarea>
					</div>
					<p class="form-notice">
						<img src="/images/cooking/error.png">
						교육 신청 전 교육 정보를 한번 더 확인해주세요!
					</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn" data-bs-dismiss="modal">닫기</button>
					<button type="submit" id="applyClassButton" class="btn btn-primary btn-disable" disabled>신청하기</button>
				</div>
			</div>
		</div>
	</div>
</form>