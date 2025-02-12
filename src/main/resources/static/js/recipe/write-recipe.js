/**
 * 제목 폼의 focuse 여부에 따라 입력창을 활성화하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
  const textarea = document.querySelector('.title_input textarea');
  const titleInput = document.querySelector('.title_input');

  textarea.addEventListener('focus', function() {
      titleInput.classList.add('focus');
    });
	
    textarea.addEventListener('blur', function() {
      titleInput.classList.remove('focus');
    });
});



/**
 * 레시피 작성폼 입력 검증 함수
 */
document.addEventListener("DOMContentLoaded", function () {
    const form = document.querySelector("form");
	const titleField = document.querySelector(".title_input");
    const titleInput = document.getElementById("titleInput");
    const titleHint = document.getElementById("titleHint");

	// 제목 실시간 검사
	titleInput.addEventListener("input", function() {
		if (titleInput.value.trim() === "") {
			titleField.classList.add("danger");
			titleHint.style.display = "block";
		}
		else {
			titleField.classList.remove("danger");
			titleHint.style.display = "none";
		}
	});
	
    // 폼 제출 시 최종 검사
    form.addEventListener("submit", function (event) {
		if (titleInput.value.trim() === "") {
			event.preventDefault();
			titleField.classList.add("danger");
			titleHint.style.display = "block";
			titleInput.focus();
		}
    });
});













/**
 * 수정해야함 버튼 동작
 */
document.addEventListener("DOMContentLoaded", function () {
    const buttons = document.querySelectorAll(".difficulty-btn");
    const difficultyInput = document.getElementById("difficultyInput");

    buttons.forEach(button => {
        button.addEventListener("click", function () {
            // 모든 버튼에서 active 클래스 제거
            buttons.forEach(btn => btn.classList.remove("active"));

            // 클릭한 버튼에 active 클래스 추가
            this.classList.add("active");

            // 선택된 값 hidden input에 저장
            difficultyInput.value = this.getAttribute("data-value");
        });
    });
});




