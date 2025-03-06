/**
 * 레시피의 댓글을 수정하는 모달창의 히든 폼에 파라미터로 전달받은 데이터를 꽂아주는 함수
 */
function openEditGuideCommentModal() {
	
}



/**
 * 내용 입력창의 폼값을 검증하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const editCommentContentInput = document.getElementById("editGuideCommentContentInput");
	const editCommentButton = document.querySelector("#editGuideCommentForm button[type='submit']");
	editCommentContentInput.addEventListener("input", function() {
		const isCommentEmpty = editCommentContentInput.value.trim() === "";
		editCommentButton.classList.toggle("disabled", isCommentEmpty);
	});
});