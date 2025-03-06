/**
 * 매거진의 대댓글을 수정하는 모달창의 히든 폼에 파라미터로 전달받은 데이터를 꽂아주는 함수
 */
function openEditMegazineSubcommentModal() {
}



/**
 * 모달창의 내용 입력창의 폼값을 검증하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {		
	const editMegazineSubcommentContentInput = document.getElementById("editMegazineSubcommentContentInput");
	const editMegazineSubcommentButton = document.getElementById("editMegazineSubcommentButton");
	editMegazineSubcommentContentInput.addEventListener("input", function() {
		const isEditMegazineSubcommentContentInputEmpty = editMegazineSubcommentContentInput.value.trim() === "";
		editMegazineSubcommentButton.classList.toggle("btn-disable", isEditMegazineSubcommentContentInputEmpty);
		editMegazineSubcommentButton.classList.toggle("btn-primary", !isEditMegazineSubcommentContentInputEmpty);
		editMegazineSubcommentButton.disabled = isEditMegazineSubcommentContentInputEmpty;
	});

});