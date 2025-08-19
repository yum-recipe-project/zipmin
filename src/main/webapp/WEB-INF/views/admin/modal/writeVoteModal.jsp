<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<form id="writeVoteForm">
	<div class="modal" id="writeVoteModal">
		<div class="modal-dialog modal-lg modal-dialog-scrollable">
			<div class="modal-content">
				<div class="modal-header d-flex align-items-center">
					<h5>투표 작성하기</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<label>제목</label>
						<input type="text" id="writeVoteTitleInput" name="title" placeholder="제목을 입력해주세요" class="form-control">
						<p id="writeVoteTitleHint" class="danger">제목을 입력해주세요.</p>
					</div>
					
					<div class="form-group">
						<label>기간</label>
						<div class="d-flex gap-3">
							<input type="date" id="writeVoteOpendateInput" class="form-control">
							<input type="date" id="writeVoteClosedateInput" class="form-control">
						</div>
						<p id="writeVoteDateHint" class="danger">기간을 입력해주세요.</p>
					</div>
					
					<div class="form-group">
						<label>옵션</label>
						<div id="writeVoteChoiceList" class="d-flex flex-column gap-2">
							<input type="text" name="choice" class="form-control" placeholder="옵션을 입력해주세요" data-fixed="true">
							<input type="text" name="choice" class="form-control" placeholder="옵션을 입력해주세요" data-fixed="true">
							<button type="button" id="addWriteVoteChoiceBtn" class="btn btn-outline-secondary btn-sm d-block w-100 mt-2">선택지 추가</button>
						</div>
						<p id="writeVoteChoiceHint" class="danger">선택지는 최소 2개 이상, 공백 및 중복은 불가합니다.</p>
					</div>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-light px-4" data-bs-dismiss="modal">닫기</button>
					<button type="submit" class="btn btn-info px-4 ms-6">작성하기</button>
				</div>
			</div>
		</div>
	</div>
</form>

