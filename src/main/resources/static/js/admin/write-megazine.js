/**
 * 
 */
document.addEventListener('DOMContentLoaded', function() {
	
	// 에디터 초기화
	const quill = new Quill('#editor', {
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
	
	// 폼 제출 시 에디터 내용을 textarea에 넣기
	const form = document.getElementById('writeMegazineForm');
	form.addEventListener('submit', function (event) {
		event.preventDefault();
		
		if (form.title.value.trim() === '') {
			alert('제목을 입력하세요');
			form.title.focus();
			return;
		}
		
		form.content.value = stripTrailingEmptyParagraphs(quill.root.innerHTML);
		if (quill.getText().trim().length === 0) {
			alert('내용을 입력하세요');
			form.content.focus();
			return;
		}
		
		createMegazine();
	});
});




function stripTrailingEmptyParagraphs(html) {
  const temp = document.createElement('div');
  temp.innerHTML = html;

  while (
    temp.lastElementChild &&
    temp.lastElementChild.tagName === 'P' &&
    temp.lastElementChild.innerHTML.trim().toUpperCase() === '<BR>'
  ) {
    temp.removeChild(temp.lastElementChild);
  }
  return temp.innerHTML;
}





/**
 * 
 */
async function createMegazine() {
	
	const form = document.getElementById('writeMegazineForm');

	try {
		const token = localStorage.getItem('accessToken');
		
		const headers = {
			'Content-Type': 'application/json',
			'Authorization': `Bearer ${token}`
		}
		
		const data = {
			title: form.title.value.trim(),
			content: form.content.value.trim()
		};
		
		const response = await instance.post('/megazines', data, {
			headers: headers
		});
		
		console.log(response);
		
		if (response.data.code === 'MEGAZINE_CREATE_SUCCESS') {
			alert('작성 성공');
		}
		
		
	}
	catch (error) {
		console.log(error);
	}
}