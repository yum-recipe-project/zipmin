/**
 * 포럼의 댓글을 수정하는 모달창의 히든 폼에 파라미터로 전달받은 데이터를 꽂아주는 함수
 */
function openEditVoteCommentModal() {
	
}



/**
 * 모달창의 내용 입력창의 폼값을 검증하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const editVoteCommentContentInput = document.getElementById("editVoteCommentContentInput");
	const editVoteCommentButton = document.getElementById("editVoteCommentButton");
	editVoteCommentContentInput.addEventListener("input", function() {
		const isEditVoteCommentContentInputEmpty = editVoteCommentContentInput.value.trim() === "";
		editVoteCommentButton.classList.toggle("btn-disable", isEditVoteCommentContentInputEmpty);
		editVoteCommentButton.classList.toggle("btn-primary", !isEditVoteCommentContentInputEmpty);
		editVoteCommentButton.disabled = isEditVoteCommentContentInputEmpty;
    });
});
