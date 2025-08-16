/**
 * 매거진 수정 폼을 실시간으로 검증하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	// 제목 실시간 검사
	const title = document.getElementById('editMegazineTitleInput');
	title.addEventListener('input', function() {
		const isTitleEmpty = this.value.trim() === '';
		title.classList.toggle('is-invalid', isTitleEmpty);
		document.getElementById('editMegazineTitleHint').style.display = isTitleEmpty ? 'block' : 'none';
	});
	
	// 내용 실시간 검사
	const content = document.getElementById('editMegazineContentInput');
	content.addEventListener('input', function() {
		const isContentEmpty = this.value.trim() === '';
		content.classList.toggle('is-invalid', isContentEmpty);
		document.getElementById('editMegazineContentHint').style.display = isContentEmpty ? 'block' : 'none';
	});
});





/**
 * 모달이 닫히면 폼을 초기화하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	const modal = document.getElementById('editMegazineModal');
	modal.addEventListener('hidden.bs.modal', function () {
		
	    const form = document.getElementById('editMegazineForm');
		
	    if (form) {
	        form.reset();
	    }
		
		modal.querySelectorAll('.is-invalid').forEach(el => {
			el.classList.remove('is-invalid');
		});
		
	    const textarea = modal.querySelector('textarea');
	    if (textarea) {
	        textarea.value = '';
	    }
	});

});





/**
 * 매거진을 수정하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	const form = document.getElementById('editMegazineForm');
	form.addEventListener('submit', async function(event) {
		event.preventDefault();
		let isValid = true;
		
		const content = document.getElementById('editMegazineContentInput');
		if (content.value.trim() === '') {
			content.classList.add('is-invalid');
			document.getElementById('editMegazineContentHint').style.display = 'block';
			content.focus();
			isValid = false;
		}
		
		const title = document.getElementById('editMegazineTitleInput');
		if (title.value.trim() === '') {
			title.classList.add('is-invalid');
			document.getElementById('editMegazineTitleHint').style.display = 'block';
			title.focus();
			isValid = false;
		}
		
		if (isValid) {
			try {
				const id = document.getElementById('editMegazineId').value;
				
				const data = {
					id: id,
					title: title.value.trim(),
					content: content.value.trim(),
				};

				const response = await instance.patch(`/megazines/${id}`, data, {
					headers: getAuthHeaders()
				});

				console.log(response);

				if (response.data.code === 'MEGAZINE_UPDATE_SUCCESS') {
					alertPrimary('매거진 수정에 성공했습니다.');
					
					const modal = bootstrap.Modal.getInstance(document.getElementById('editMegazineModal'));
					if (modal) modal.hide();
					
					fetchChompList();
				}
			}
			catch (error) {
				const code = error?.response?.data?.code;
				
				//***************** 여기 ㅈㄴ 추가하기 */
				
				if (code === 'MEGAZINE_UPDATE_FAIL') {
					alertDanger('매거진 수정에 실패했습니다.');
				}
				
				console.log(error);
			}
		}
		
	});
	
});










