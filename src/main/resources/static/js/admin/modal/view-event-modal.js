/**
 * 서버에서 신고 목록 데이터를 가져오는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	const modal = document.getElementById('viewEventModal');
	modal.addEventListener('show.bs.modal', async function (event) {
	
		try {
			const id = event.relatedTarget?.dataset.id
			
			const response = await instance.get(`/events/${id}`, {
				headers: getAuthHeaders()
			});
			
			console.log(response);
			
			if (response.data.code === 'EVENT_READ_SUCCESS') {
				document.querySelector('.event_title').innerText = response.data.data.title;
				document.querySelector('.event_postdate').innerHTML = formatDatePeriod(response.data.data.opendate, response.data.data.closedate);
				document.querySelector('.event_content').innerHTML = response.data.data.content;
				document.querySelector('.event_image').src = response.data.data.image;
			}
		}
		catch (error) {
			/*****!!!!!!!! 에러코드 추가 */
			console.log(error);
		}
	});
	
});
