/**
 * 이벤트 수정 폼을 실시간으로 검증하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	// 제목 실시간 검사
	const title = document.getElementById('editEventTitleInput');
	title.addEventListener('input', function() {
		const isTitleEmpty = this.value.trim() === '';
		title.classList.toggle('is-invalid', isTitleEmpty);
		document.getElementById('editEventTitleHint').style.display = isTitleEmpty ? 'block' : 'none';
	});

	// 날짜 실시간 검사
	const opendate = document.getElementById('editEventOpendateInput');
	const closedate = document.getElementById('editEventClosedateInput');
	opendate.addEventListener('input', function() {
		const isOpendateEmpty = this.value.trim() ==='';
		opendate.classList.toggle('is-invalid', isOpendateEmpty);
		if (closedate.value.trim() !== '') {
			document.getElementById('editEventDateHint').style.display = isOpendateEmpty ? 'block' : 'none';
		}
	});
	closedate.addEventListener('input', function() {
		const isClosedateEmpty = this.value.trim() ==='';
		closedate.classList.toggle('is-invalid', isClosedateEmpty);
		if (opendate.value.trim() !== '') {
			document.getElementById('editEventDateHint').style.display = isClosedateEmpty ? 'block' : 'none';
		}
	});
	opendate.addEventListener('change', function () {
		closedate.min = opendate.value;
	});
	closedate.addEventListener('change', function () {
		opendate.max = closedate.value;
	});
	
	// 내용 실시간 검사
	const content = document.getElementById('editEventContentInput');
	content.addEventListener('input', function() {
		const isContentEmpty = this.value.trim() === '';
		content.classList.toggle('is-invalid', isContentEmpty);
		document.getElementById('editEventContentHint').style.display = isContentEmpty ? 'block' : 'none';
	});

});





/**
 * 모달이 닫히면 폼을 초기화하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	const modal = document.getElementById('editEventModal');
	modal.addEventListener('hidden.bs.modal', function () {
		
	    const form = document.getElementById('editEventForm');
		
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
	
	const form = document.getElementById('editEventForm');
	form.addEventListener('submit', async function(event) {
		event.preventDefault();
		let isValid = true;
		
		const content = document.getElementById('editEventContentInput');
		if (content.value.trim() === '') {
			content.classList.add('is-invalid');
			document.getElementById('editEventContentHint').style.display = 'block';
			content.focus();
			isValid = false;
		}
		
		const closedate = document.getElementById('editEventClosedateInput');
		if (closedate.value.trim() === '') {
			closedate.classList.add('is-invalid');
			document.getElementById('editEventDateHint').style.display = 'block';
			closedate.focus();
			isValid = false;
		}
		
		const opendate = document.getElementById('editEventOpendateInput');
		if (opendate.value.trim() === '') {
			opendate.classList.add('is-invalid');
			document.getElementById('editEventDateHint').style.display = 'block';
			opendate.focus();
			isValid = false;
		}
		
		const title = document.getElementById('editEventTitleInput');
		if (title.value.trim() === '') {
			title.classList.add('is-invalid');
			document.getElementById('editEventTitleHint').style.display = 'block';
			title.focus();
			isValid = false;
		}
		
		if (isValid) {
			try {
				const id = document.getElementById('editEventId').value;
				const image = document.getElementById('editEventImageInput');
				
				const formdata = new FormData();
				formdata.append('eventRequestDto', new Blob([JSON.stringify({
					id: id,
					title: title.value.trim(),
					image: image.value.trim(),
					opendate: opendate.value.trim(),
					closedate: closedate.value.trim(),
					content: content.value.trim(),
					category: 'event'
				})], { type: 'application/json' }));

			    const file = document.getElementById('editEventImageInput'); 
			    if (file.files.length > 0) {
			        formdata.append('file', file.files[0]);
			    }
				
				const response = await instance.patch(`/events/${id}`, formdata, {
					headers: {
						...getAuthHeaders(),
						'Content-Type': undefined
					}
				});
				
				console.log(response);

				if (response.data.code === 'EVENT_UPDATE_SUCCESS') {
					alertPrimary('이벤트 수정에 성공했습니다.');
					
					const modal = bootstrap.Modal.getInstance(document.getElementById('editEventModal'));
					if (modal) modal.hide();
					
					fetchChompList();
				}
			}
			catch (error) {
				const code = error?.response?.data?.code;
				
				//***************** 여기 ㅈㄴ 추가하기 */
				
				if (code === 'EVENT_UPDATE_FAIL') {
					alertDanger('이벤트 수정에 실패했습니다.');
				}
				
				console.log(error);
			}
		}
		
	});
	
});