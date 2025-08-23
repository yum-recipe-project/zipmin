/**
 * 매거진 작성 폼을 실시간으로 검증하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	// 제목 실시간 검사
	const title = document.getElementById('writeMegazineTitleInput');
	title.addEventListener('input', function() {
		const isTitleEmpty = this.value.trim() === '';
		title.classList.toggle('is-invalid', isTitleEmpty);
		document.getElementById('writeMegazineTitleHint').style.display = isTitleEmpty ? 'block' : 'none';
	});
	
	// 이미지 실시간 검사
	const image = document.getElementById('writeMegazineImageInput');
	image.addEventListener('input', function() {
		const isImageEmpty = this.value.trim() === '';
		image.classList.toggle('is-invalid', isImageEmpty);
		document.getElementById('writeMegazineImageHint').style.display = isImageEmpty ? 'block' : 'none';
	});
	
	// 내용 실시간 검사
	const content = document.getElementById('writeMegazineContentInput');
	content.addEventListener('input', function() {
		const isContentEmpty = this.value.trim() === '';
		content.classList.toggle('is-invalid', isContentEmpty);
		document.getElementById('writeMegazineContentHint').style.display = isContentEmpty ? 'block' : 'none';
	});
});





/**
 * 모달이 닫히면 폼을 초기화하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	const modal = document.getElementById('writeMegazineModal');
	modal.addEventListener('hidden.bs.modal', function() {
		
	    const form = document.getElementById('writeMegazineForm');
		
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
 * 매거진을 생성하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	const form = document.getElementById('writeMegazineForm');
	form.addEventListener('submit', async function(event) {
		event.preventDefault();
		let isValid = true;
		
		const content = document.getElementById('writeMegazineContentInput');
		if (content.value.trim() === '') {
			content.classList.add('is-invalid');
			document.getElementById('writeMegazineContentHint').style.display = 'block';
			content.focus();
			isValid = false;
		}
		
		const image = document.getElementById('writeMegazineImageInput');
		if (image.value.trim() === '') {
			image.classList.add('is-invalid');
			document.getElementById('writeMegazineImageHint').style.display = 'block';
			image.focus();
			isValid = false;
		}
		
		const title = document.getElementById('writeMegazineTitleInput');
		if (title.value.trim() === '') {
			title.classList.add('is-invalid');
			document.getElementById('writeMegazineTitleHint').style.display = 'block';
			title.focus();
			isValid = false;
		}
		
		if (isValid) {
			try {
				const formdata = new FormData();
				formdata.append('megazineRequestDto', new Blob([JSON.stringify({
					title: title.value.trim(),
					content: content.value.trim(),
					category: 'megazine'
				})], { type: 'application/json' }));
				
				
				const file = document.getElementById('writeMegazineImageInput'); 
			    if (file.files.length > 0) {
			        formdata.append('file', file.files[0]);
			    }

				const response = await instance.post('/megazines', formdata, {
					headers: {
						...getAuthHeaders(),
						'Content-Type': undefined
					}
				});

				if (response.data.code === 'MEGAZINE_CREATE_SUCCESS') {
					alertPrimary('매거진 작성에 성공했습니다.');
					bootstrap.Modal.getInstance(document.getElementById('writeMegazineModal'))?.hide();
					fetchChompList();
				}
			}
			catch (error) {
				const code = error?.response?.data?.code;
				
				if (code === 'MEGAZINE_CREATE_FAIL') {
					alertDanger('매거진 작성에 실패했습니다.');
				}
				else if (code === 'MEGAZINE_INVALID_INPUT') {
					alertDanger('입력값이 유효하지 않습니다.');
				}
				else if (code === 'MEGAZINE_UNAUTHORIZED_ACCESS') {
					alertDanger('로그인되지 않은 사용자입니다.');
				}
				else if (code === 'MEGAZINE_FORBIDDEN') {
					alertDanger('접근 권한이 없습니다.');
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