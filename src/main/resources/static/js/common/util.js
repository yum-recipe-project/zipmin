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




/**
 * 날짜를 요일과 함께 포맷해주는 함수
 */
function formatDateWithDay(isoString) {
	
	const date = new Date(isoString);

	const year = date.getFullYear();
	const month = String(date.getMonth() + 1).padStart(2, '0');
	const day = String(date.getDate()).padStart(2, '0');

	const weekdays = ['일', '월', '화', '수', '목', '금', '토'];
	const dayOfWeek = weekdays[date.getDay()];

	return `${year}.${month}.${day}(${dayOfWeek})`;
	
}





function formatTime(timeStr) {
	
	const date = new Date(timeStr);
	return date.toTimeString().substring(0, 5);
	
}






/**
 * 첫 문자만 대문자로 변경하는 함수
 */
function capitalizeFirst(str) {
	
  if (!str) return '';
  return str.charAt(0).toUpperCase() + str.slice(1);
  
}

