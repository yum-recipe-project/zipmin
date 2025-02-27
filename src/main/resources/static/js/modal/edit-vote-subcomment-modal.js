/**
 * 포럼의 대댓글을 수정하는 모달창의 히든 폼에 파라미터로 전달받은 데이터를 꽂아주는 함수
 */
function openEditVoteSubcommentModal() {
}



/**
 * 모달창의 내용 입력창의 폼값을 검증하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {		
	const editVoteSubcommentContentInput = document.getElementById("editVoteSubcommentContentInput");
	const editVoteSubcommentButton = document.getElementById("editVoteSubcommentButton");
	editVoteSubcommentContentInput.addEventListener("input", function() {
		const isEditVoteSubcommentContentInputEmpty = editVoteSubcommentContentInput.value.trim() === "";
		editVoteSubcommentButton.classList.toggle("btn-disable", isEditVoteSubcommentContentInputEmpty);
		editVoteSubcommentButton.classList.toggle("btn-primary", !isEditVoteSubcommentContentInputEmpty);
		editVoteSubcommentButton.disabled = isEditVoteSubcommentContentInputEmpty;
	});

});