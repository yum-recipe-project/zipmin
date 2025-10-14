<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<form id="editGuideForm">
	<input type="hidden" id="editGuideId">
	<div class="modal" id="editGuideModal">
		<div class="modal-dialog modal-lg modal-dialog-scrollable">
			<div class="modal-content">
				<div class="modal-header d-flex align-items-center">
					<h5>키친가이드  수정하기</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
				</div>
				<div class="modal-body">
				
					<!-- 소제목 -->
					<div class="form-group">
						<label>소제목</label>
						<input type="text" id="editGuideSubtitleInput" name="subtitle" placeholder="소제목을 입력해주세요" class="form-control">
						<p id="editGuideSubtitleHint" class="danger">소제목을 입력해주세요.</p>
					</div>
					
					<!-- 제목 -->					
					<div class="form-group">
						<label>제목</label>
						<input type="text" id="editGuideTitleInput" name="title" placeholder="제목을 입력해주세요" class="form-control">
						<p id="editGuideTitleHint" class="danger">제목을 입력해주세요.</p>
					</div>
					
					<!-- 카테고리 -->
					<div class="form-group">
						<label>카테고리</label>
						<select id="editGuideCategorySelect" name="category" class="form-control">
							<option value="">-- 선택하세요 --</option>
							<option value="preparation">손질법</option>
							<option value="storage">보관법</option>
							<option value="cooking">요리 정보</option>
							<option value="etc">기타 정보</option>
						</select>
						<p id="editGuideCategoryHint" class="danger">카테고리를 선택해주세요.</p>
					</div>
					
					<!-- 본문 -->
					<div class="form-group">
						<label>내용</label>
						<textarea class="form-control" id="editGuideContentInput" name="content" style="height: 200px;" placeholder="내용을 입력해주세요"></textarea>
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



