/**
 * 
 */
document.addEventListener('DOMContentLoaded', function() {
	const params = new URLSearchParams(window.location.search);
	const megazineId = params.get('megazineId');
	// 데이터 패치
	fetch(`http://localhost:8586/megazines/${megazineId}`, {
		method: "GET"
	})
	.then(response => response.json())
	.then(data => {
		document.querySelector(".megazine_category").innerText = data.chomp_dto.category;
		document.querySelector(".megazine_title").innerText = data.chomp_dto.title;
		document.querySelector(".megazine_content").innerText = data.content;
		const date = new Date(data.postdate);
		const formatDate = `${date.getFullYear()}년 ${String(date.getMonth()+1).padStart(2, '0')}월 ${String(date.getDate()).padStart(2, '0')}일`;
		document.querySelector(".megazine_postdate").innerText = formatDate;
	})
	.catch(error => console.log(error));
});