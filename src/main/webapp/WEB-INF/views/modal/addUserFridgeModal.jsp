<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<form id="addUserFridgeForm">
	<div class="modal" id="addUserFridgeModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5>재료 추가하기</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
				</div>
				
				<div class="modal-body">
				
					<!-- 검색창 -->
					<div class="search_box" data-type="fridge">
						<input type="text" class="search_word" placeholder="재료를 검색하세요">
						<button type="button" class="search_btn">
							<img src="/images/common/search.png">
						</button>
					</div>
					<button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addFridgeModal">새로운 재료 만들기</button>
					

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-danger" data-bs-dismiss="modal">닫기</button>
					<button type="submit" class="btn btn-primary">재료 추가하기</button>
				</div>
			</div>
		</div>
	</div>
</form>