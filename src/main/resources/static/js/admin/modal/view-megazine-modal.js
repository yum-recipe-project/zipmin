/**
 * 서버에서 매거진 데이터를 가져오는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	const modal = document.getElementById('viewMegazineModal');
	modal.addEventListener('show.bs.modal', async function (event) {
	
		// 매거진 조회
		try {
			const id = event.relatedTarget?.dataset.id;
			
			const response = await instance.get(`/megazines/${id}`, {
				headers: getAuthHeaders()
			});
			
			if (response.data.code === 'MEGAZINE_READ_SUCCESS') {
				document.querySelector('.megazine_title').innerText = response.data.data.title;
				document.querySelector('.megazine_postdate').innerHTML = formatDate(response.data.data.closedate);
				document.querySelector('.megazine_content').innerHTML = response.data.data.content;
				document.querySelector('.megazine_image').src = response.data.data.image;
			}
		}
		catch (error) {
			const code = error?.response?.data?.code;

			if (code === 'MEGAZINE_READ_FAIL') {
				alertDanger('매거진 조회에 실패했습니다.');
			}
			else if (code === 'MEGAZINE_NOT_FOUND') {
				alertDanger('해당 매거진을 찾을 수 없습니다.');
			}
			else if (code === 'INTERNAL_SERVER_ERROR') {
				alertDanger('서버 내부 오류가 발생했습니다.');
			}
			else {
				console.log(error);
			}
		}
	});
	
});
