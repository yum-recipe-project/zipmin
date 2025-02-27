/**
 * 모달창의 내용 입력창의 폼값을 검증하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const writeVoteSubcommentContentInput = document.getElementById("writeVoteSubcommentContentInput");
	const writeVoteSubcommentButton = document.getElementById("writeVoteSubcommentButton");
	writeVoteSubcommentContentInput.addEventListener("input", function() {
		const isWriteVoteSubcommentContentInputEmpty = writeVoteSubcommentContentInput.value.trim() === "";
		writeVoteSubcommentButton.classList.toggle("btn-disable", isWriteVoteSubcommentContentInputEmpty);
		writeVoteSubcommentButton.classList.toggle("btn-primary", !isWriteVoteSubcommentContentInputEmpty);
		writeVoteSubcommentButton.disabled = isWriteVoteSubcommentContentInputEmpty;
    });
});