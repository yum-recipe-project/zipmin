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
			
			if (response.data.code === 'EVENT_READ_SUCCESS') {
				document.querySelector('.event_title').innerText = response.data.data.title;
				document.querySelector('.event_postdate').innerHTML = formatDatePeriod(response.data.data.opendate, response.data.data.closedate);
				document.querySelector('.event_content').innerHTML = response.data.data.content;
				document.querySelector('.event_image').src = response.data.data.image;
			}
		}
		catch (error) {
			const code = error?.response?.data?.code;

			if (code === 'EVENT_READ_FAIL') {
				alertDanger('이벤트 조회에 실패했습니다.');
			}
			else if (code === 'EVENT_NOT_FOUND') {
				alertDanger('해당 이벤트를 찾을 수 없습니다.');
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
