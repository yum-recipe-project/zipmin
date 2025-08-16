/**
 * 매거진 작성 폼을 실시간으로 검증하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	// 제목 실시간 검사
	const title = document.getElementById('writeEventTitleInput');
	title.addEventListener('input', function() {
		const isTitleEmpty = this.value.trim() === '';
		title.classList.toggle('is-invalid', isTitleEmpty);
		document.getElementById('writeEventTitleHint').style.display = isTitleEmpty ? 'block' : 'none';
	});
	
	// 이미지 실시간 검사
	const image = document.getElementById('writeEventImageInput');
	image.addEventListener('input', function() {
		const isImageEmpty = this.value.trim() === '';
		image.classList.toggle('is-invalid', isImageEmpty);
		document.getElementById('writeEventImageHint').style.display = isImageEmpty ? 'block' : 'none';
	});

	// 날짜 실시간 검사
	const opendate = document.getElementById('writeEventOpendateInput');
	const closedate = document.getElementById('writeEventClosedateInput');
	opendate.addEventListener('input', function() {
		const isOpendateEmpty = this.value.trim() ==='';
		opendate.classList.toggle('is-invalid', isOpendateEmpty);
		if (closedate.value.trim() !== '') {
			document.getElementById('writeEventDateHint').style.display = isOpendateEmpty ? 'block' : 'none';
		}
	});
	closedate.addEventListener('input', function() {
		const isClosedateEmpty = this.value.trim() ==='';
		closedate.classList.toggle('is-invalid', isClosedateEmpty);
		if (opendate.value.trim() !== '') {
			document.getElementById('writeEventDateHint').style.display = isClosedateEmpty ? 'block' : 'none';
		}
	});
	opendate.addEventListener('change', function () {
		closedate.min = opendate.value;
	});
	closedate.addEventListener('change', function () {
		opendate.max = closedate.value;
	});
	
	// 내용 실시간 검사
	const content = document.getElementById('writeEventContentInput');
	content.addEventListener('input', function() {
		const isContentEmpty = this.value.trim() === '';
		content.classList.toggle('is-invalid', isContentEmpty);
		document.getElementById('writeEventContentHint').style.display = isContentEmpty ? 'block' : 'none';
	});

});





/**
 * 모달이 닫히면 폼을 초기화하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	const modal = document.getElementById('writeEventModal');
	modal.addEventListener('hidden.bs.modal', function () {
		
	    const form = document.getElementById('writeEventForm');
		
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
 * 매거진을 생성하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	const form = document.getElementById('writeEventForm');
	form.addEventListener('submit', async function(event) {
		event.preventDefault();
		let isValid = true;
		
		const content = document.getElementById('writeEventContentInput');
		if (content.value.trim() === '') {
			content.classList.add('is-invalid');
			document.getElementById('writeEventContentHint').style.display = 'block';
			content.focus();
			isValid = false;
		}
		
		const closedate = document.getElementById('writeEventClosedateInput');
		if (closedate.value.trim() === '') {
			closedate.classList.add('is-invalid');
			document.getElementById('writeEventDateHint').style.display = 'block';
			closedate.focus();
			isValid = false;
		}
		
		const opendate = document.getElementById('writeEventOpendateInput');
		if (opendate.value.trim() === '') {
			opendate.classList.add('is-invalid');
			document.getElementById('writeEventDateHint').style.display = 'block';
			opendate.focus();
			isValid = false;
		}
		
		const image = document.getElementById('writeEventImageInput');
		if (image.value.trim() === '') {
			image.classList.add('is-invalid');
			document.getElementById('writeEventImageHint').style.display = 'block';
			image.focus();
			isValid = false;
		}
		
		const title = document.getElementById('writeEventTitleInput');
		if (title.value.trim() === '') {
			title.classList.add('is-invalid');
			document.getElementById('writeEventTitleHint').style.display = 'block';
			title.focus();
			isValid = false;
		}
		
		if (isValid) {
			try {

				const formdata = new FormData();
				formdata.append('eventRequestDto', new Blob([JSON.stringify({
					title: title.value.trim(),
					image: image.value.trim(),
					opendate: opendate.value.trim(),
					closedate: closedate.value.trim(),
					content: content.value.trim(),
					category: 'event'
				})], { type: 'application/json' }));

			    const file = document.getElementById('writeEventImageInput'); 
			    if (file.files.length > 0) {
			        formdata.append('file', file.files[0]);
			    }
				
				const response = await instance.post('/events', formdata, {
					headers: {
						...getAuthHeaders(),
						'Content-Type': undefined
					}
				});

				if (response.data.code === 'EVENT_CREATE_SUCCESS') {
					alertPrimary('이벤트 작성에 성공했습니다.');
					
					const modal = bootstrap.Modal.getInstance(document.getElementById('writeEventModal'));
					if (modal) modal.hide();
					
					fetchChompList();
				}
			}
			catch (error) {
				const code = error?.response?.data?.code;
				
				//***************** 여기 ㅈㄴ 추가하기 */
				
				if (code === 'EVENT_CREATE_FAIL') {
					alertDanger('이벤트 작성에 실패했습니다.');
				}
				
				console.log(error);
			}
		}
		
	});
	
});