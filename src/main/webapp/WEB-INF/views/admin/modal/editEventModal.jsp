<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<form id="editEventForm">
	<input type="hidden" id="editEventId">
	<div class="modal" id="editEventModal">
		<div class="modal-dialog modal-lg modal-dialog-scrollable">
			<div class="modal-content">
				<div class="modal-header d-flex align-items-center">
					<h5>이벤트 수정하기</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<label>제목</label>
						<input type="text" id="editEventTitleInput" name="title" placeholder="제목을 입력해주세요" class="form-control">
						<p id="editEventTitleHint" class="danger">제목을 입력해주세요.</p>
					</div>
					
					<div class="form-group">
						<label>이미지</label>
						<input class="form-control" type="file" id="editEventImageInput">
					</div>
					
					<div class="form-group">
						<label>기간</label>
						<div class="d-flex gap-3">
							<input type="date" id="editEventOpendateInput" class="form-control">
							<input type="date" id="editEventClosedateInput" class="form-control">
						</div>
						<p id="editEventDateHint" class="danger">기간을 입력해주세요.</p>
					</div>
					
					<div class="form-group">
						<label>내용</label>
						<textarea class="form-control" id="editEventContentInput" name="content" style="height: 200px;" placeholder="내용을 입력해주세요"></textarea>
						<p id="editEventContentHint" class="danger">내용을 입력해주세요.</p>
					</div>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-light px-4" data-bs-dismiss="modal">닫기</button>
					<button type="submit" class="btn btn-info px-4 ms-6">수정하기</button>
				</div>
			</div>
		</div>
	</div>
</form>



