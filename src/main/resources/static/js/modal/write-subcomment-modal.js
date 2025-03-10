/**
 * 모달창의 내용 입력창의 폼값을 검증하는 함수
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