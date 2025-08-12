/**
 * 댓글 수정 폼을 실시간으로 검증하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const form = document.getElementById('editCommentForm');
	
	// 내용 실시간 검사
	form.content.addEventListener('input', function() {
		const isContentEmpty = this.value.trim() === '';
		this.classList.toggle('danger', isContentEmpty);
		document.querySelector('.content_field p').style.display = isContentEmpty ? 'block' : 'none';
	});
});




/**
 * 댓글을 수정하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	const form = document.getElementById('editCommentForm');
	form.addEventListener('submit', async function(event) {
		event.preventDefault();
		
		if (form.content.value.trim() === '') {
			form.content.classList.add('danger');
			document.querySelector('.content_field p').style.display = 'block';
			form.content.focus();
			return;
		}
		
		try {
			const token = localStorage.getItem('accessToken');

			const headers = {
				'Content-Type': 'application/json',
				'Authorization': `Bearer ${token}`
			}

			const id = form.id.value;
			
			const data = {
				id: id,
				content: form.content.value
			}
			
			const response = await instance.patch(`/comments/${id}`, data, {
				headers: headers
			});

			if (response.data.code === 'COMMENT_UPDATE_SUCCESS') {
				// *************** 이거 나중에 reload 말고 비동기로 변경하기 ****
				location.reload();
				alertPrimary('성공함');
			}
		}
		catch (error) {
			// ******* 여기에 ㅈㄴ 추가
		}
	});
});
