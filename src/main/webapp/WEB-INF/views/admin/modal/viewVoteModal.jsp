<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="modal" id="viewVoteModal">
	<div class="modal-dialog modal-lg modal-dialog-scrollable">
		<div class="modal-content">
			<div class="modal-header d-flex align-items-center">
				<h5>투표 상세보기</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
			</div>
			<div class="modal-body">
				<div class="vote_wrap">
					<div class="vote_header">
						<span>아이스크림</span>
						<h2 class="vote_title"></h2>
						<div class="vote_info">
							<div class="period">
								<span class="vote_postdate"></span>
							</div>
							<span>참여</span>
							<span class="vote_total"></span>
						</div>
					</div>
					
					<!-- 투표 결과 -->
					<div class="vote_result">
						<ul class="record_list"></ul>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-light px-4" data-bs-dismiss="modal">닫기</button>
			</div>
		</div>
	</div>
	
</div>




