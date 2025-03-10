<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<form method="post" action="" onsubmit="">
	<div class="modal" id="editReviewModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5>수정하기</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
				</div>
				<div class="modal-body">
					<div class="form-group">
    					<label>별점</label>
						<div id="editStarGroup" class="star_group">
							<img src="/images/recipe/star_full.png" class="star" data-value="1">
							<img src="/images/recipe/star_outline.png" class="star" data-value="2">
							<img src="/images/recipe/star_outline.png" class="star" data-value="3">
							<img src="/images/recipe/star_outline.png" class="star" data-value="4">
							<img src="/images/recipe/star_outline.png" class="star" data-value="5">
							<input type="hidden" id="editReviewStar" name="rating" value="1">
						</div>
					</div>
					<div class="form-group">
						<label>내용</label>
						<textarea class="form-control" id="editReviewContent" name="content" style="height: 90px;"></textarea>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn" data-bs-dismiss="modal">닫기</button>
					<button type="submit" class="btn btn-disable" disabled>작성하기</button>
				</div>
			</div>
		</div>
	</div>
</form>