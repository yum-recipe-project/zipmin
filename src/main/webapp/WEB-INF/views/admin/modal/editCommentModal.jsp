<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<form id="editCommentForm" method="post" >
	<input type="hidden" name="id">
	<div class="modal" id="editCommentModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5>수정하기</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<label>내용</label>
						<textarea class="form-control" name="content" style="height: 90px;"></textarea>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-light px-4" data-bs-dismiss="modal">취소</button>
					<button type="submit" class="btn btn-info px-4 ms-6">수정하기</button>
				</div>
			</div>
		</div>
	</div>
</form>