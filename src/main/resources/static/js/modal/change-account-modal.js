/**
 * 계좌를 변경하는 모달창을 여는 함수
 */
function openChangeAccountModal() {
	
}



/**
 * 계좌를 변경하는 모달창에 데이터를 꽂는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
});



/**
 * 계좌를 변경하는 모달창의 폼값을 검증하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const accountNumberInput = document.getElementById("accountNumberInput");
	const accountNameInput = document.getElementById("accountNameInput");
	const submitButton = document.querySelector("#changeAccountForm button[type='submit']");
	
	accountNumberInput.addEventListener("input", function() {
		submitButton.classList.toggle("disabled", !accountNumberInput.value || !accountNameInput.value);
	});
	accountNameInput.addEventListener("input", function() {
		submitButton.classList.toggle("disabled", !accountNumberInput.value || !accountNameInput.value);
	});
});