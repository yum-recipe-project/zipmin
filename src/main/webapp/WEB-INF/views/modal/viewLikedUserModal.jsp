<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="modal" id="viewLikedUserModal">
	<div class="modal-dialog">
				<div class="modal-content">
			<div class="modal-header">
				<h5>팔로워</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
			</div>
			
			<div class="modal-body body-scroll">
				<!-- 팔로워 수 -->
				<div class="user_count">
					<span>총 7명</span>
				</div>
			
				<!-- 팔로워 목록 -->
				<ul class="user_list"></ul>
			</div>
		</div>
	</div>
</div>