/**
 * 접근 권한을 설정하는 함수
 */
document.addEventListener('DOMContentLoaded', async function() {
	fetchGuideList();
	
	try {
		await instance.get('/dummy');
	}
	catch (error) {
		redirectToAdminLogin('/');
	}
	
});


