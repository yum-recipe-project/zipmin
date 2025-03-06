/**
 * 매거진의 댓글을 수정하는 모달창의 히든 폼에 파라미터로 전달받은 데이터를 꽂아주는 함수
 */
function openEditMegazineCommentModal() {
	
}



/**
 * 모달창의 내용 입력창의 폼값을 검증하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const editMegazineCommentContentInput = document.getElementById("editMegazineCommentContentInput");
	const editMegazineCommentButton = document.getElementById("editMegazineCommentButton");
	editMegazineCommentContentInput.addEventListener("input", function() {
		const isEditMegazineCommentContentInputEmpty = editMegazineCommentContentInput.value.trim() === "";
		editMegazineCommentButton.classList.toggle("btn-disable", isEditMegazineCommentContentInputEmpty);
		editMegazineCommentButton.classList.toggle("btn-primary", !isEditMegazineCommentContentInputEmpty);
		editMegazineCommentButton.disabled = isEditMegazineCommentContentInputEmpty;
    });
});
