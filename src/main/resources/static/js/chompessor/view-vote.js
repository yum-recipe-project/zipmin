/**
 * 투표
 */
document.addEventListener("DOMContentLoaded", function() {
	
	document.querySelectorAll(".checkbox_group").forEach((checkbox) => {
		checkbox.addEventListener("change", function() {
			// 체크되면 모든 체크박스를 disabled 처리
			if (this.checked) {
				document.querySelectorAll(".checkbox_group").forEach((cb) => {
					if (cb !== this) {
						cb.disabled = true;
						cb.classList.add("disable");
					}
				});
			}
			// 하나도 체크되지 않으면 다시 활성화
			else {
				document.querySelectorAll(".checkbox_group").forEach((cb) => {
					cb.disabled = false;
					cb.classList.remove("disable");
				});
			}
		});
	});
});



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
	const commentButton = document.getElementById("commentButton")
	commentInput.addEventListener("input", function() {
		const isCommentEmpty = commentInput.value.trim() === "";
		commentButton.classList.toggle("disable", isCommentEmpty);
		commentButton.disabled = isCommentEmpty;
	});
});








