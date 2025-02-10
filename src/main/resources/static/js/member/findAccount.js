/**
 * 폼 입력 검증 함수
 */
document.addEventListener("DOMContentLoaded", function () {
    const form = document.querySelector("form");
    const nameInput = document.getElementById("nameInput");
    const nameHint = document.getElementById("nameHint");
    const phoneInput = document.getElementById("phoneInput");
    const phoneHint = document.getElementById("phoneHint");
    const idInput = document.getElementById("idInput");
    const idHint = document.getElementById("idHint");
    const emailInput = document.getElementById("emailInput");
    const emailHint = document.getElementById("emailHint");
	
	// 이름 실시간 검사
	nameInput.addEventListener("input", function() {
		if (nameInput.value.trim() === "") {
			nameInput.classList.add("danger");
			nameHint.style.display = "block";
		}
		else {
			nameInput.classList.remove("danger");
			nameHint.style.display = "none";
		}
	});
	
	// 휴대폰 번호 실시간 검사
	phoneInput.addEventListener("input", function() {
		if (phoneInput.value.trim() === "") {
			phoneInput.classList.add("danger");
			phoneHint.style.display = "block";
		}
		else {
			phoneInput.classList.remove("danger");
			phoneHint.style.display = "none";
		}
	});
	
	// 아이디 실시간 검사
	idInput.addEventListener("input", function() {
		if (idInput.value.trim() === "") {
			idInput.classList.add("danger");
			idHint.style.display = "block";
		}
		else {
			idInput.classList.remove("danger");
			idHint.style.display = "none";
		}
	});
	
	// 이메일 실시간 검사
	emailInput.addEventListener("input", function() {
		if (emailInput.value.trim() === "") {
			emailInput.classList.add("danger");
			emailHint.style.display = "block";
		}
		else {
			emailInput.classList.remove("danger");
			emailHint.style.display = "none";
		}
	});
	
    // 폼 제출 시 최종 검사
    form.addEventListener("submit", function (event) {
		if (phoneInput.value.trim() === "") {
			event.preventDefault();
			phoneInput.classList.add("danger");
			phoneHint.style.display = "block";
			phoneInput.focus();
		}
		
		if (nameInput.value.trim() === "") {
			event.preventDefault();
			nameInput.classList.add("danger");
			nameHint.style.display = "block";
			nameInput.focus();
		}
		
		if (idInput.value.trim() === "") {
			event.preventDefault();
			idInput.classList.add("danger");
			idHint.style.display = "block";
			idInput.focus();
		}
		
		if (emailInput.value.trim() === "") {
			event.preventDefault();
			emailInput.classList.add("danger");
			emailHint.style.display = "block";
			emailInput.focus();
		}
    });
});



/**
 * 탭 메뉴 클릭 시 탭 메뉴를 활성화하고 해당하는 내용을 표시하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const tabItems = document.querySelectorAll('.tab_button button');
	const contentItems = document.querySelectorAll('.tab_content');
	
	tabItems.forEach((item, index) => {
		item.addEventListener("click", function(event) {
			event.preventDefault();
			
			tabItems.forEach(button => button.classList.remove('active'));
			this.classList.add('active');
			
			contentItems.forEach(content => content.style.display = 'none');
			contentItems[index].style.display = 'block';
		});
	});
	
	// 첫 번째 탭과 내용을 기본으로 활성화
	tabItems[0].classList.add('active');
	contentItems.forEach(content => content.style.display = 'none');
	contentItems[0].style.display = 'block';
});







