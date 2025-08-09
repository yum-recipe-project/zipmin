/**
 * 서버에서 매거진 상세 데이터를 가져오는 함수
 */
document.addEventListener('DOMContentLoaded', async function() {
	
	// 매거진 정보 조회
	try {
		const id = new URLSearchParams(window.location.search).get('id');
		
		const response = await fetch(`/megazines/${id}`);
		const result = await response.json();
		
		console.log(result.data);
		
		if (result.code === 'MEGAZINE_READ_SUCCESS') {
			document.querySelector('.title').innerText = result.data.title;
			document.querySelector('.content').innerHTML = result.data.content;
			document.querySelector('.postdate').innerText = formatDate(result.data.postdate);
		}
	}
	catch (error) {
		console.log(error);
	}
	
});





/**
 * 수정과 삭제 버튼 클릭이벤트를 처리하는 함수
 */
/*
document.addEventListener('DOMContentLoaded', function() {
	
	const id = new URLSearchParams(window.location.search).get('id');
	
	// 수정
	document.getElementById('editMegazineBtn').addEventListener('click', function(event) {
		event.preventDefault();
		location.href = `/admin/editMegazine.do?id=${id}`;
	});
	
	// 삭제
	document.getElementById('deleteMegazineBtn').addEventListener('click', async function(event) {
		event.preventDefault();
		if (confirm('매거진을 삭제하시겠습니까?')) {
			try {
				const token = localStorage.getItem('accessToken');

				const headers = {
					'Content-Type': 'application/json',
					'Authorization': `Bearer ${token}`
				}

				const data = {
					id: id
				};

				const response = await instance.delete(`/megazines/${id}`, {
					data: data,
					headers: headers
				});
				
				if (response.data.code === 'MEGAZINE_DELETE_SUCCESS') {
					alert('매거진이 성공적으로 삭제되었습니다.');
					location.href = '/admin/listMegazine.do';
				}
			}
			catch (error) {
				const code = error?.response?.data?.code;
				
				if (code === 'MEGAZINE_DELETE_FAIL') {
					alert('매거진 삭제에 실패했습니다');
				}
				if (code === 'CHOMP_DELETE_FAIL') {
					alert('쩝쩝박사 게시물 삭제에 실패했습니다');
				}
				else if (code === 'MEGAZINE_INVALID_INPUT') {
					alert('입력값이 유효하지 않습니다.');
				}
				else if (code === 'MEGAZINE_UNAUTHORIZED') {
					alert('로그인되지 않은 사용자입니다.');
				}
				else if (code === 'MEGAZINE_FORBIDDEN') {
					alert('접근 권한이 없습니다.');
				}
				else if (code === 'MEGAZINE_NOT_FOUND') {
					alert('해당 매거진을 찾을 수 없습니다.');
				}
				else if (code === 'INTERNAL_SERVER_ERROR') {
					alert('서버 내부 오류가 발생했습니다.');
				}
				else {
					console.log(error);
				}
			}
		}
	});
	
});
*/