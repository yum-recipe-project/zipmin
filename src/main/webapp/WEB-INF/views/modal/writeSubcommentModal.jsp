<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<form id="writeSubcommentForm" method="post" action="" onsubmit="">
	<input type="hidden" id="writeCommentTablename" name="tablename">
	<input type="hidden" id="writeCommentRecodenum" name="recodenum">
	<div class="modal" id="writeSubcommentModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5>작성하기</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<label>내용</label>
						<textarea class="form-control" id="writeSubcommentContent" name="content" style="height: 90px;"></textarea>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-danger" data-bs-dismiss="modal">취소</button>
					<button type="submit" class="btn btn-primary disabled">작성하기</button>
				</div>
			</div>
		</div>
	</div>
</form> 