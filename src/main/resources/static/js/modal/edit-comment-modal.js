/**
 * 댓글을 수정하는 모달창의 히든 폼에 파라미터로 전달받은 데이터를 꽂아주는 함수
 */
function openEditCommentModal() {
	
}



/**
 * 내용 입력창의 폼값을 검증하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const editCommentContent = document.getElementById("editCommentContent");
	const editCommentButton = document.querySelector("#editCommentForm button[type='submit']");
	editCommentContent.addEventListener("input", function() {
		const isCommentEmpty = editCommentContent.value.trim() === "";
		editCommentButton.classList.toggle("disabled", isCommentEmpty);
	});
});