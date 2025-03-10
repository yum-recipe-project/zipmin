/**
 * 댓글 정렬 탭 메뉴 클릭 시 탭 메뉴를 활성화하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const tabList = document.querySelectorAll('.comment_order button');
	tabList.forEach(tabI => {
	    tabI.addEventListener("click", function(event) {
	        event.preventDefault();
	        tabList.forEach(tabJ => tabJ.classList.remove('active'));
	        this.classList.add('active');
	    });
	});
});



/**
 * 댓글 작성 폼의 포커스 여부에 따라 입력창을 활성화하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const writeCommentContent = document.getElementById("writeCommentContent");
	const commentContentBorder = document.querySelector('.comment_input');
	writeCommentContent.addEventListener('focus', function() {
		commentContentBorder.classList.add('focus');
	});
	writeCommentContent.addEventListener('blur', function() {
		commentContentBorder.classList.remove('focus');
	});
});



/**
 * 댓글 작성 폼값을 검증하고 버튼을 활성화하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const writeCommentContent = document.getElementById("writeCommentContent");
	const writeCommentButton = document.querySelector("#writeCommentForm button[type='submit']");
	writeCommentContent.addEventListener("input", function() {
		const isCommentContentEmpty = writeCommentContent.value.trim() === "";
		writeCommentButton.classList.toggle("disable", isCommentContentEmpty);
		writeCommentButton.disabled = isCommentContentEmpty;
	});
});



/**
 * 댓글을 수정하는 모달창의 폼값을 검증하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const editCommentContent = document.getElementById("editCommentContent");
	const editCommentButton = document.querySelector("#editCommentForm button[type='submit']");
	editCommentContent.addEventListener("input", function() {
		const isCommentContentEmpty = editCommentContent.value.trim() === "";
		editCommentButton.classList.toggle("disabled", isCommentContentEmpty);
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
	const writeSubcommentButton = document.querySelector("#writeSubcommentForm button[type='submit']");
	writeSubcommentContent.addEventListener("input", function() {
		const isWriteSubcommentContentEmpty = writeSubcommentContent.value.trim() === "";
		writeSubcommentButton.classList.toggle("disabled", isWriteSubcommentContentEmpty);
    });
});