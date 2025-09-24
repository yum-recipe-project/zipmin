/**
 * 접근 권한을 설정하는 함수
 */
document.addEventListener('DOMContentLoaded', async function() {

	try {
		await instance.get('/dummy');
	}
	catch (error) {
		redirectToLogin();
	}
	
});





/**
 * 
 */
document.addEventListener('DOMContentLoaded', function() {
	
	fetchLikedUserList();
	
});





/**
 * 사용자가 좋아요한 사용자 목록 데이터를 가져오는 함수
 */
async function fetchLikedUserList() {
	
	try {
		const id = parseJwt(localStorage.getItem('accessToken')).id;
				
		const response = await instance.get(`/users/${id}/like-users`, {
			headers: getAuthHeaders()
		});
		
		console.log(response);
		
		
		
	}
	catch(error) {
		console.log(error);
	}
	
}