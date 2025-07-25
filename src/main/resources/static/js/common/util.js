/**
 * 날짜를 포맷해주는 함수
 */
function formatDate(dateString) {
	
	const date = new Date(dateString);
	if (isNaN(date)) return '';
	
	const year = date.getFullYear();
	const month = String(date.getMonth() + 1).padStart(2, '0');
	const day = String(date.getDate()).padStart(2, '0');
	
	return `${year}년 ${month}월 ${day}일`;
	
}
