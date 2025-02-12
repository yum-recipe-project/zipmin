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
 * 재료 추가하기 버튼 클릭 시 재료 입력폼을 추가하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	document.getElementById("addIngredientBtn").addEventListener('click', function(event) {
        event.preventDefault();
		
		const ul = document.querySelector('.ingredient_field ul');
		const li = document.createElement('li');
		
		li.innerHTML = `
		<input type="text" id="ingredientInput" name="" value="" placeholder="재료">
			<img src="/images/recipe/commit.png">
			<input type="text" id="amountInput" name="" value="" placeholder="단위가 포함된 양">
			<img src="/images/recipe/commit.png">
			<input type="text" id="noteInput" name="" value="" placeholder="비고 (선택사항)">
			<div class="remove_btn">
				<a class="removeIngredientBtn">
					<img src="/images/recipe/close.png">지우기
				</a>
			</div>
		`;
		ul.append(li);
		
		// 삭제 기능
		li.querySelector(".removeIngredientBtn").addEventListener('click', function(event) {
			event.preventDefault();
			li.remove();
		});
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




