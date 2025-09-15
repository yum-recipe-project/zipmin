<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<form id="editUserForm" method="post" >
	<input type="hidden" id="editUserId" name="id">
	<div class="modal" id="editUserModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5>회원 정보 수정</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
				</div>
				
				<div class="modal-body">
					<div class="form-group username_field">
						<label>아이디</label>
						<input type="text" name="username" placeholder="영문 혹은 영문 + 숫자, 4-20자" class="form-control">
						<p class="danger">아이디를 입력해주세요.</p>
					</div>
					
					<div class="form-group name_field">
						<label>이름</label>
						<input type="text" name="name" placeholder="이름 입력" class="form-control">
						<p class="danger">이름을 입력해주세요.</p>
					</div>
					
					<div class="form-group nickname_field">
						<label>닉네임</label>
						<input type="text" name="nickname" placeholder="닉네임 입력" class="form-control">
						<p class="danger">닉네임을 입력해주세요.</p>
					</div>
					
					<div class="form-group tel_field">
						<label>휴대폰 번호</label>
						<input type="tel" name="tel" placeholder="-없이 휴대폰 번호 입력" class="form-control">
					</div>
					
					<div class="form-group email_field">
						<label>이메일</label>
						<input type="email" name="email" placeholder="이메일 입력" class="form-control">
					</div>
				</div>
				<div class="modal-footer">
					<div class="text-end">
						<button type="button" class="btn btn-light px-4" data-bs-dismiss="modal">취소</button>
						<button type="submit" class="btn btn-info px-4 ms-6">수정하기</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</form>