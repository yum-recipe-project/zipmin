/**
 * 
 */
var classOffset = 0;
var classLimit = 10;
var classCount = 0;
$(document).ready(function() {
	// 초기 페이지 로딩 시 데이터 로드
	loadClassContent();
	
	// 댓글 더보기 클릭 시 추가 데이터 로드
	$('.more_class_btn').click(function(event) {
		event.preventDefault();
		loadClassContent();
	});
});



/**
 * 클래스의 목록 데이터를 로드하는 함수
 */
function loadClassContent() {
	$.ajax({
		url: '/cooking/listClass/class.do',
		type: 'GET',
		data: {
			offset: classOffset,
			limit: classLimit
		},
		success: function(response) {
			// 응답 데이터를 리스트에 추가
			$('#classContent').append(response);
			
			// 서버에서 총 데이터의 개수를 가져와서 설정
			classCount = parseInt($('#classCount').val());
			classOffset += classLimit;
			
			// 불러올 데이터가 없으면 더보기 버튼 숨김
			if (classOffset >= classCount) {
				$('.more_class_btn').hide();
			}
			else {
				$('.more_class_btn').show();
			}
		}
	});
}



/**
 * 탭 메뉴 클릭시 탭 메뉴를 활성화하고 해당 내용을 표시하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const tabItems = document.querySelectorAll('.class_sort button');
	const contentItems = document.querySelectorAll('.class_list');
	// 탭 클릭 이벤트 설정
	tabItems.forEach((item, index) => {
	    item.addEventListener("click", function(event) {
	        event.preventDefault();
	        
	        tabItems.forEach(button => button.classList.remove('active'));
	        this.classList.add('active');
	        
	        contentItems.forEach(content => content.style.display = 'none');
	        contentItems[index].style.display = 'block';
	    });
	});
	// 기본으로 첫번째 활성화
	tabItems.forEach(button => button.classList.remove('active'));
	contentItems.forEach(content => content.style.display = 'none');
	tabItems[0].classList.add('active');
	contentItems[0].style.display = 'block';
});
















