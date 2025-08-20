/**
 * 서버에서 신고 목록 데이터를 가져오는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	const modal = document.getElementById('viewVoteModal');
	modal.addEventListener('show.bs.modal', async function (event) {
	
		try {
			const id = event.relatedTarget?.dataset.id
			
			const response = await instance.get(`/votes/${id}`, {
				headers: getAuthHeaders()
			});
			
			console.log(response);
			
			if (response.data.code === 'VOTE_READ_SUCCESS') {
				document.querySelector('.vote_title').innerText = response.data.data.title;
				document.querySelector('.vote_postdate').innerText = formatDatePeriod(response.data.data.opendate, response.data.data.closedate);
				document.querySelector('.vote_total').innerText = response.data.data.recordcount;
				
				renderRecord(response.data.data.choice_list);
			}
		}
		catch (error) {
			const code = error?.response?.data?.code;

			if (code === 'VOTE_READ_FAIL') {
				alertDanger('투표 조회에 실패했습니다.');
			}
			else if (code === 'VOTE__RECORD_READ_FAIL') {
				alertDanger('투표 조회에 실패했습니다.');
			}
			else if (code === 'VOTE_NOT_FOUND') {
				alertDanger('해당 투표를 찾을 수 없습니다.');
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





/**
 * 투표의 결과를 보여주는 함수
 */
function renderRecord(choiceList) {

	const listElement = document.querySelector('.record_list');
	listElement.innerHTML = '';

	choiceList.forEach(choice => {
		
		const li = document.createElement('li');
		
		const wrap = document.createElement('div');
		wrap.classList.add('vote_option_wrap');
		wrap.style.setProperty('--bar-width', `${choice.recordrate}%`);

		const h5 = document.createElement('h5');
		h5.appendChild(document.createTextNode(choice.choice));

		const span = document.createElement('span');
		span.innerText = `${choice.recordcount}명`;

		const h3 = document.createElement('h3');
		h3.innerText = `${choice.recordrate}%`;

		wrap.append(h5, span, h3);
		li.appendChild(wrap);
		listElement.appendChild(li);
	});
}