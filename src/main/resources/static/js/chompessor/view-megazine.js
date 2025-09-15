/**
 * 쩝쩝박사의 매거진 상세 데이터를 가져오는 함수
 */
document.addEventListener('DOMContentLoaded', async function() {
	
	// 매거진 정보 조회
	try {
		const id = new URLSearchParams(window.location.search).get('id');
		
		const response = await fetch(`/megazines/${id}`, {
			headers: getAuthHeaders()
		});
		
		const result = await response.json();
		
		if (result.code === 'MEGAZINE_READ_SUCCESS') {
			document.querySelector('.megazine_title').innerText = result.data.title;
			document.querySelector('.megazine_content').innerHTML = result.data.content;
			document.querySelector('.megazine_postdate').innerText = `${formatDate(result.data.closedate)}`;
		}
		else if (result.code === 'MEGAZINE_NOT_FOUND') {
			alert('해당 매거진을 찾을 수 없습니다.');
			location.href = '/chompessor/listChomp.do';
		}
		else if (result.code === 'INTERNAL_SERVER_ERROR') {
			alert('서버 내부 오류가 발생했습니다.');
			location.href = '/chompessor/listChomp.do';
		}
		else {
			console.log(error);
		}
	}
	catch(error) {
		console.log(error);
	}
	
});