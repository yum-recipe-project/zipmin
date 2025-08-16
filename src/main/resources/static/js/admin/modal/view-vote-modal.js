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
			/*****!!!!!!!! 에러코드 추가 */
			console.log(error);
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
		wrap.style.setProperty('--bar-width', `${choice.rate}%`);

		const h5 = document.createElement('h5');
		h5.appendChild(document.createTextNode(choice.choice));

		const span = document.createElement('span');
		span.innerText = `${choice.count}명`;

		const h3 = document.createElement('h3');
		h3.innerText = `${choice.rate}%`;

		wrap.append(h5, span, h3);
		li.appendChild(wrap);
		listElement.appendChild(li);
	});
}