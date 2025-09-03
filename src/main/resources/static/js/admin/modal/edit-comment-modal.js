/**
 * 댓글 수정 폼을 실시간으로 검증하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const form = document.getElementById('editCommentForm');
	
	// 내용 실시간 검사
	form.content.addEventListener('input', function() {
		const isContentEmpty = this.value.trim() === '';
		this.classList.toggle('is-invalid', isContentEmpty);
		document.getElementById('editCommentContentHint').style.display = isContentEmpty ? 'block' : 'none';
	});
});





/**
 * 모달이 닫히면 폼을 초기화하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	const modal = document.getElementById('editCommentModal');
	modal.addEventListener('hidden.bs.modal', function () {
		
	    const form = document.getElementById('editCommentForm');
		
	    if (form) {
	        form.reset();
	    }
		
		modal.querySelectorAll('.is-invalid').forEach(el => {
			el.classList.remove('is-invalid');
		});
		modal.querySelectorAll('p[id$="Hint"]').forEach(p => {
			p.style.display = 'none';
		});
		
	    const textarea = modal.querySelector('textarea');
	    if (textarea) {
	        textarea.value = '';
	    }
	});

});





/**
 * 댓글을 수정하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	const form = document.getElementById('editCommentForm');
	form.addEventListener('submit', async function(event) {
		event.preventDefault();
		let isValid = true;
		
		// 폼값 검사
		if (form.content.value.trim() === '') {
			form.content.classList.add('danger');
			document.getElementById('editCommentForm').style.display = 'block';
			form.content.focus();
			isValid = false;
		}
		
		// 댓글 수정
		if (isValid) {
			try {
				const id = document.getElementById('editCommentId').value;
				
				const data = {
					id: id,
					content: form.content.value
				}
				
				const response = await instance.patch(`/comments/${id}`, data, {
					headers: getAuthHeaders()
				});
	
				if (response.data.code === 'COMMENT_UPDATE_SUCCESS') {
					alertPrimary('댓글이 성공적으로 수정되었습니다.');
					bootstrap.Modal.getInstance(document.getElementById('editCommentModal'))?.hide();
					fetchCommentList(false);
				}
			}
			catch (error) {
				const code = error?.response?.data?.code;
								
				if (code === 'COMMENT_UPDATE_FAIL') {
					alertDanger('댓글 수정에 실패했습니다.');
				}
				else if (code === 'COMMENT_INVALID_INPUT') {
					alertDanger('입력값이 유효하지 않습니다.');
				}
				else if (code === 'USER_INVALID_INPUT') {
					alertDanger('입력값이 유효하지 않습니다.');
				}
				else if (code === 'COMMENT_UNAUTHORIZED_ACCESS') {
					alertDanger('로그인되지 않은 사용자입니다.');
				}
				else if (code === 'COMMENT_FORBIDDEN') {
					alertDanger('접근 권한이 없습니다.');
				}
				else if (code === 'COMMENT_NOT_FOUND') {
					alertDanger('해당 매거진을 찾을 수 없습니다.');
				}
				else if (code === 'USER_NOT_FOUND') {
					alertDanger('해당 사용자를 찾을 수 없습니다.');
				}
				else if (code === 'INTERNAL_SERVER_ERROR') {
					alertDanger('서버 내부 오류가 발생했습니다.');
				}
				else {
					console.log(error);
				}
			}
		}
	});
});
