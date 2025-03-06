/**
 * 포인트를 충전하는 모달창에 데이터를 꽂는 함수
 */
document.addEventListener("DOMContentLoaded", function() {
	const ownedPoint = document.getElementById("ownedPoint");
	const totalPoint = document.getElementById("totalPoint");
	const pointRadio = document.getElementsByName("point");
	
	// 패치해와서 hidden 폼에 보유한 포인트 꽂기 + 토탈 포인트 기본 초기값
	// 일단 하드코딩 해두었음
	ownedPoint.innerText = 200;
	totalPoint.innerText = 200 + "P";
	
	pointRadio.forEach(radio => {
		radio.addEventListener("change", function() {
			totalPoint.innerText = parseInt(ownedPoint.innerText) + parseInt(radio.value) + "P";
		});
	});
});



/**
 * 포인트를 충전하는 모달창의 충전할 포인트를 선택했는지 검증하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const pointRadio = document.querySelectorAll("input[name='point']");
	const submitButton = document.querySelector("#topUpPointForm button[type='submit']");
	
	pointRadio.forEach(function(radio) {
        radio.addEventListener("change", function() {
			const isChecked = Array.from(pointRadio).some(radio => radio.checked);
			submitButton.classList.toggle("disabled", !isChecked);
        });
    });
});