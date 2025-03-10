/**
 * 댓글 정렬 방법 탭 메뉴 클릭 시 탭 메뉴를 활성화하고 해당하는 내용을 표시하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const tabCommentItems = document.querySelectorAll('.comment_order button');
	const contentCommentItems = document.querySelectorAll('.comment_list');
	// 탭 클릭 이벤트 설정
	tabCommentItems.forEach((item, index) => {
	    item.addEventListener("click", function(event) {
	        event.preventDefault();
	        
	        tabCommentItems.forEach(button => button.classList.remove('active'));
	        this.classList.add('active');
	        
	        contentCommentItems.forEach(content => content.style.display = 'none');
	        contentCommentItems[index].style.display = 'block';
	    });
	});
	// 기본으로 첫번째 활성화
	tabCommentItems.forEach(button => button.classList.remove('active'));
	contentCommentItems.forEach(content => content.style.display = 'none');
	tabCommentItems[0].classList.add('active');
	contentCommentItems[0].style.display = 'block';
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



/**
 * 댓글을 수정하는 모달창의 폼값을 검증하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const editCommentContent = document.getElementById("editCommentContent");
	const editCommentButton = document.querySelector("#editCommentForm button[type='submit']");
	editCommentContent.addEventListener("input", function() {
		const isCommentEmpty = editCommentContent.value.trim() === "";
		editCommentButton.classList.toggle("disabled", isCommentEmpty);
	});
});



/**
 * 댓글을 신고하는 모달창의 신고 사유를 선택했는지 검증하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const reasonRadio = document.querySelectorAll("#reportCommentForm input[name='reason']");
	const submitButton = document.querySelector("#reportCommentForm button[type='submit']");

	reasonRadio.forEach(function(radio) {
        radio.addEventListener("change", function() {
			const isChecked = Array.from(reasonRadio).some(radio => radio.checked);
			submitButton.classList.toggle("disabled", !isChecked);
        });
    });
});



/**
 * 대댓글을 입력하는 모달창의 내용 입력창의 폼값을 검증하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const writeSubcommentContent = document.getElementById("writeSubcommentContent");
	const writeSubcommentButton = document.querySelector("#writeSubcommentForm button[type='submit]");
	writeSubcommentContent.addEventListener("input", function() {
		const isWriteSubcommentContentEmpty = writeSubcommentContent.value.trim() === "";
		writeSubcommentButton.classList.toggle("btn-disable", isWriteSubcommentContentEmpty);
		writeSubcommentButton.classList.toggle("btn-primary", !isWriteSubcommentContentEmpty);
		writeSubcommentButton.disabled = isWriteSubcommentContentEmpty;
    });
});