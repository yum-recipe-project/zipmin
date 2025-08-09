/**
 * 전역변수
 */
let quill;





/**
 * 에디터를 초기화하고 맞춤법 검사를 비활성화하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	// 에디터 초기화
	quill = new Quill('#editor', {
		theme: 'snow',
		placeholder: '내용을 입력하세요...',
		modules: {
			toolbar: [
				['bold', 'italic', 'underline']
			]
		}
	});
	
	// 맞춤법 검사 끄기
	document.querySelector('.ql-editor').setAttribute('spellcheck', 'false');
});





/**
 * 서버에서 매거진 데이터를 가져오는 함수
 */
document.addEventListener('DOMContentLoaded', async function() {
	
	try {
		const id = new URLSearchParams(window.location.search).get('id');
		
		const response = await fetch(`/megazines/${id}`);
		const result = await response.json();
		
		console.log(result);
		
		if (result.code === 'MEGAZINE_READ_SUCCESS') {
			const form = document.getElementById('editMegazineForm');
			form.title.value = result.data.title;
			quill.clipboard.dangerouslyPasteHTML(result.data.content);
		}
		else if (result.code === 'MEGAZINE_NOT_FOUND') {
			alert('해당 매거진을 찾을 수 없습니다.');
			location.href = '/admin/listMegazine.do';
		}
		else {
			alert('서버 내부 오류가 발생했습니다.');
			location.href = '/admin/listMegazine.do';
		}
	}
	catch (error) {
		console.log(error);
	}
	
});





/**
 * 매거진을 수정하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	if (!isLoggedIn()) {
		redirectToLogin();
	}
	
	const form = document.getElementById('editMegazineForm');
	form.addEventListener('submit', async function (event) {
		event.preventDefault();
		
		// 폼 제출시 폼값 검증
		if (form.title.value.trim() === '') {
			alert('제목을 입력하세요');
			form.title.focus();
			return;
		}
		
		if (quill.getText().trim().length === 0) {
			alert('내용을 입력하세요');
			form.content.focus();
			return;
		}
		else {
			form.content.value = quill.root.innerHTML;
		}
		
		// 매거진 작성
		try {
			const token = localStorage.getItem('accessToken');
			const id = new URLSearchParams(window.location.search).get('id');
			
			const headers = {
				'Content-Type': 'application/json',
				'Authorization': `Bearer ${token}`
			}
			
			const data = {
				id: id,
				title: form.title.value.trim(),
				content: form.content.value.trim()
			}
			
			const response = await instance.patch(`/megazines/${id}`, data, {
				headers: headers
			});
			
			console.log(response);
			
			// 성공시 동작 추가 !!!!!
			
		}
		catch (error) {
			console.log(error);
		}
	});
	
});



















