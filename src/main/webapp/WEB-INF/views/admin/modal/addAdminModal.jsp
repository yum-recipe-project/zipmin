<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<form id="addAdminForm" method="post" >
	<div class="modal" id="addAdminModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5>관리자 생성하기</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
				</div>
				
				<div class="modal-body">
					<div class="form-group">
						<label>아이디</label>
						<input type="text" name="username" placeholder="영문 혹은 영문 + 숫자, 4-20자" class="form-control">
						<p id="usernameHint" class="danger">아이디를 입력해주세요.</p>
					</div>
					
					<div class="form-group">
						<label>비밀번호</label>
						<input type="password" name="password1" maxlength="20" placeholder="비밀번호" class="form-control">
						<input type="password" name="password2" maxlength="20" placeholder="비밀번호 재입력" class="form-control mt-2">
						<p id="passwordHint" class="danger">비밀번호를 입력해주세요.</p>
					</div>
					
					<div class="form-group">
						<label>이름</label>
						<input type="text" name="name" placeholder="이름 입력" class="form-control">
						<p id="nameHint" class="danger">이름을 입력해주세요.</p>
					</div>
					
					<div class="form-group">
						<label>닉네임</label>
						<input type="text" name="nickname" placeholder="닉네임 입력" class="form-control">
						<p id="nicknameHint" class="danger">닉네임을 입력해주세요.</p>
					</div>
				</div>
				<div class="modal-footer">
					<div class="text-end">
						<button type="button" class="btn btn-light px-4" data-bs-dismiss="modal">취소</button>
						<button type="submit" class="btn btn-info px-4 ms-6">생성하기</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</form>