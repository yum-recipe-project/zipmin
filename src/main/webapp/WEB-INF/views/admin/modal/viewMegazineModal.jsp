<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="modal" id="viewMegazineModal">
	<div class="modal-dialog modal-lg modal-dialog-scrollable">
		<div class="modal-content">
			<div class="modal-header d-flex align-items-center">
				<h5>매거진 상세보기</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
			</div>
			<div class="modal-body">
				<div class="megazine_wrap">
					<!-- 매거진 헤더 -->
					<div class="megazine_header">
						<span class="megazine_category">매거진</span>
						<h2 class="megazine_title"></h2>
						<div class="megazine_writer">
							<img src="/images/common/test.png">
							<span><b>집밥의민족</b></span>
							<span> ・ </span>
							<span class="megazine_postdate"></span>
						</div>
					</div>
					
					<!-- 매거진 내용 -->
					<p class="megazine_content"></p>
					<!-- 매거진 이미지 -->
					<img class="megazine_image" src="/images/common/test.png">
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-light px-4" data-bs-dismiss="modal">닫기</button>
			</div>
		</div>
	</div>
	
</div>




