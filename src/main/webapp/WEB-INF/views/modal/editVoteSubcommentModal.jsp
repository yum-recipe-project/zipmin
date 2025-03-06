<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<form id="editVoteSubcommentForm" method="post" action="" onsubmit="">
	<div class="modal" id="editVoteSubcommentModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5>수정하기</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<label>내용</label>
						<textarea class="form-control" id="editVoteSubcommentContentInput" name="content" style="height: 90px;"></textarea>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-danger" data-bs-dismiss="modal">취소</button>
					<button type="submit" id="editVoteSubcommentButton" class="btn btn-disable" disabled>작성하기</button>
				</div>
			</div>
		</div>
	</div>
</form>