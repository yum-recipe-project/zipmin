/**
 * 서버에서 신고 목록 데이터를 가져오는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	const modal = document.getElementById('viewMegazineModal');
	modal.addEventListener('show.bs.modal', async function (event) {
	
		try {
			const id = event.relatedTarget?.dataset.id
			
			const response = await instance.get(`/megazines/${id}`, {
				headers: getAuthHeaders()
			});
			
			if (response.data.code === 'MEGAZINE_READ_SUCCESS') {
				document.querySelector('.megazine_title').innerText = response.data.data.title;
				document.querySelector('.megazine_postdate').innerHTML = formatDate(response.data.data.closedate);
				document.querySelector('.megazine_content').innerHTML = response.data.data.content;
			}
		}
		catch (error) {
			/*****!!!!!!!! 에러코드 추가 */
			console.log(error);
		}
	});
	
});
