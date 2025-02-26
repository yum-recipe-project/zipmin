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
 * 내용 입력창의 폼값을 검증하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const editCommentContentInput = document.getElementById("editGuideCommentContentInput");
	const editCommentButton = document.getElementById("editGuideCommentButton");
	editCommentContentInput.addEventListener("input", function() {
	    if (editCommentContentInput.value.trim() !== "") {
	    	editCommentButton.classList.remove("btn-disable");
	    	editCommentButton.classList.add("btn-primary");
	    	editCommentButton.removeAttribute("disabled");
	    }
	    else {
	    	editCommentButton.classList.remove("btn-primary");
	    	editCommentButton.classList.add("btn-disable");
	    	editCommentButton.setAttribute("disabled", "true");
	    }
	});
});


/**
 * 레시피의 댓글을 수정하는 모달창의 히든 폼에 파라미터로 전달받은 데이터를 꽂아주는 함수
 */
function openEditGuideCommentModal() {
	
}

