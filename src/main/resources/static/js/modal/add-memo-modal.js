/**
 * 장보기메모에 재로 담기 모달창의 재료를 선택했는지 검증하는 함수
 */
document.addEventListener("DOMContentLoaded", function() {
    const memoElement = document.getElementById("memo");
    const submitButton = document.querySelector("#addMemoForm .modal-footer button[type='submit']");

    memoElement.addEventListener("change", function(event) {
		if (event.target.matches('input[name="ingredient"]')) {
			const isChecked = document.querySelector('input[name=ingredient]:checked');
			submitButton.classList.toggle("disabled", !isChecked);
		}
	});
});
