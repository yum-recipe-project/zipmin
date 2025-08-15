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
				
			}
		}
		catch (error) {
			/*****!!!!!!!! 에러코드 추가 */
			console.log(error);
		}
	});
	
});
