/**
 * 레시피를 후원하는 모달창을 여는 함수
 */
function openSupportRecipeModal() {
	
}



/**
 * 레시피를 후원하는 모달창에 데이터를 꽂는 함수
 */
document.addEventListener("DOMContentLoaded", function() {
	const ownedPoint = document.getElementById("ownedPoint");
	const remainPoint = document.getElementById("remainPoint");
	const pointInput = document.getElementById("pointInput");
	
	// 데이터 가져오기
	/*
	fetch("주소", {method: "get"})
		.then(response => response.json())
		.then(data => {
			ownedPoint.innerText = data.point;
			remainPoint = data.point - 100;
		})
		.catch(error => console.log(error));
	*/
	// 일단 하드코딩 해두었음
	ownedPoint.innerText = 200;
	remainPoint.innerText = 100;

	// 포인트가 입력되면 남은 포인트 계산 그리고 pointAlert 노출 여부
	pointInput.addEventListener("input", function() {
		remainPoint.innerText = ownedPoint.innerText - pointInput.value;
	});
});



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









