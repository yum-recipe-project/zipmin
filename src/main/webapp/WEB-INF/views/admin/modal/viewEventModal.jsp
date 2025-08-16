<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="modal" id="viewEventModal">
	<div class="modal-dialog modal-lg modal-dialog-scrollable">
		<div class="modal-content">
			<div class="modal-header d-flex align-items-center">
				<h5>이벤트 상세보기</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
			</div>
			<div class="modal-body">
				<div class="event_wrap">
					<!-- 이벤트 헤더 -->
					<div class="event_header">
						<span>이벤트</span>
						<h2 class="event_title"></h2>
						<div class="event_info">
							<div class="period">
								<span class="event_postdate"></span>
							</div>
							<span>댓글</span>
							<span>235개</span>
						</div>
					</div>
					
					<!-- 이벤트 내용 -->
					<p class="event_content"></p>
					<!-- 이벤트 이미지 -->
					<img src="/images/common/test.png">
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-light px-4" data-bs-dismiss="modal">닫기</button>
			</div>
		</div>
	</div>
	
</div>




