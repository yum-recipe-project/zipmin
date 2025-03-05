/**
 * 모달창의 내용 입력창의 폼값을 검증하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const writeMegazineSubcommentContentInput = document.getElementById("writeMegazineSubcommentContentInput");
	const writeMegazineSubcommentButton = document.getElementById("writeMegazineSubcommentButton");
	writeMegazineSubcommentContentInput.addEventListener("input", function() {
		const isWriteMegazineSubcommentContentInputEmpty = writeMegazineSubcommentContentInput.value.trim() === "";
		writeMegazineSubcommentButton.classList.toggle("btn-disable", isWriteMegazineSubcommentContentInputEmpty);
		writeMegazineSubcommentButton.classList.toggle("btn-primary", !isWriteMegazineSubcommentContentInputEmpty);
		writeMegazineSubcommentButton.disabled = isWriteMegazineSubcommentContentInputEmpty;
    });
});