/**
 * 수익 내역 페이지에 데이터를 설정하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
});



/**
 * 포인트를 출금하는 모달창을 여는 함수
 */
function openWithdrawPointModal() {
	
}



/**
 * 포인트를 출금하는 모달창에 데이터를 꽂는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const ownedPoint = document.getElementById("ownedPoint");
	const remainPoint = document.getElementById("remainPoint");
	const pointInput = document.getElementById("pointInput");
	const submitButton = document.querySelector("#withdrawPointForm button[type='submit']");
	
	// 데이터 가져오기
	// 일단 하드코딩
	ownedPoint.innerText = 10000;
	remainPoint.innerText = 0;
	
	// 포인트가 입력되면 남은 포인트 계산 그리고 pointAlert 노출 여부
	pointInput.addEventListener("input", function() {
		remainPoint.innerText = ownedPoint.innerText - pointInput.value;
		submitButton.classList.toggle("disabled", remainPoint.innerText < 0);
	});
});



