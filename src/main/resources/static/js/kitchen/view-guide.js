/**
 * 
 */
var commentOffset = 0;
var commentLimit = 10;
var commentCount = 0;
$(document).ready(function() {
	
	// 초기 페이지 로딩 시 데이터 로드
	loadGuideCommentContent();
	
	// 댓글 더보기 버튼 클릭 시 추가 데이터 로드
	$('.more_comment_btn').click(function(event) {
		event.preventDefault();
		loadGuideCommentContent();
	}) 
})



/**
 * 키친가이드의 댓글 목록 데이터를 로드하는 함수
 */
function loadGuideCommentContent() {
	$.ajax({
		url: '/kitchen/viewGuide/comment.do',
		type: 'GET',
		data: {
			offset: commentOffset,
			limit: commentLimit
		},
		success: function (response) {
			// 응답 데이터를 리스트에 추가
			$('#guideCommentContent').append(response);
			
			// 서버에서 총 데이터의 개수를 가져와서 설정
			commentCount = parseInt('#commentCount').val();
			commentOffset += commentLimit;
			
			// 불러올 데이터가 없으면 더보기 버튼 숨김
			if (commentOffset >= commentCount) {
				$('.more_comment_btn').hide();
			} else {
				$('.more_comment_btn').show();
			}
		},
		error: function () {
			$('.more_comment_btn').hide();
		}
	});
}





/**
 * 탭 메뉴 클릭 시 탭 메뉴를 활성화하고 해당하는 내용을 표시하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const tabItems = document.querySelectorAll('.comment_order button');
	const contentItems = document.querySelectorAll('.comment_list');
	
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



/**
 * 댓글 작성 폼의 focus 여부에 따라 입력창을 활성화하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const commentInput = document.getElementById("commentInput");
	const commentInputBorder = document.querySelector('.comment_input');
	commentInput.addEventListener('focus', function() {
		commentInputBorder.classList.add('focus');
	});
	commentInput.addEventListener('blur', function() {
		commentInputBorder.classList.remove('focus');
	});
});

/**
 * 댓글 작성 폼값을 검증하는 함수 (댓글 내용 미작성 시 버튼 비활성화)
 */
document.addEventListener('DOMContentLoaded', function() {
	const commentButton = document.getElementById("commentButton");
	commentInput.addEventListener("input", function() {
		const isCommentEmpty = commentInput.value.trim() === "";
		commentButton.classList.toggle("disable", isCommentEmpty);
		commentButton.disabled = isCommentEmpty;
	});
});







