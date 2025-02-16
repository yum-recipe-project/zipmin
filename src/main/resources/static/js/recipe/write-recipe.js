/**
 * 재료 추가하기 버튼 클릭 시 재료 입력폼을 추가하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	document.getElementById("addIngredientBtn").addEventListener('click', function(event) {
        event.preventDefault();
		
		const ul = document.querySelector('.ingredient_field ul');
		const li = document.createElement('li');
		li.classList.add('ingredient_row');
		
		li.innerHTML = `
			<div class="ingredient_flex">
				<input type="text" class="ingredient_input" name="" value="" placeholder="재료">
				<img src="/images/recipe/commit.png">
				<input type="text" class="ingredient_amount_input" name="" value="" placeholder="단위가 포함된 양">
				<img src="/images/recipe/commit.png">
				<input type="text" class="ingredient_note_input" name="" value="" placeholder="비고 (선택사항)">
				<div class="remove_btn">
					<a class="remove_ingredient_btn">
						<img src="/images/recipe/close.png">지우기
					</a>
				</div>
			</div>
			<p class="ingredient_hint">재료를 입력해주세요.</p>
		`;
		ul.append(li);
		
		// 삭제 기능
		li.querySelector(".remove_ingredient_btn").addEventListener('click', function(event) {
			event.preventDefault();
			li.remove();
		});
    });
});



/**
 * 다음 단계 작성하기 버튼 클릭 시 조리 순서 입력폼을 추가하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const ul = document.querySelector('.step_wrap ul');
	
	document.getElementById("addStepBtn").addEventListener('click', function(event) {
		event.preventDefault();
		
		const li = document.createElement('li');
		li.classList.add('step_row');
		const stepCount = ul.children.length + 1;
		li.innerHTML = `
			<div class="step_image_field">
				<label>STEP ${ stepCount }</label>
				<input type="file" class="step_image_input" name="image" accept="image/*">
			</div>
			<div class="step_field">
				<input type="text" class="step_input" name="" value="" placeholder="조리 방법을 자세히 작성해주세요">
				<div class="remove_btn">
					<a class="remove_step_btn">
						<img src="/images/recipe/close.png">지우기
					</a>
				</div>
			</div>
			<p class="step_hint">조리 순서를 입력해주세요.</p>
		`;
		ul.append(li);
		
		// 삭제 기능
		li.querySelector(".remove_step_btn").addEventListener('click', function(event) {
			event.preventDefault();
			li.remove();
			const steps = ul.children;
			for (let i = 0; i < steps.length; i++) {
				steps[i].querySelector("label").textContent = `STEP${ i + 1 }`;
			}
		});
	});
});



/**
 * 레시피 작성폼 입력 검증 함수
 */
document.addEventListener("DOMContentLoaded", function () {
	
	// 썸네일 실시간 검사
	const thumbnailInput = document.getElementById("thumbnailInput");
	const thumbnailHint = document.getElementById("thumbnailHint");
	thumbnailInput.addEventListener("input", function() {
		const isThumbnailEmpty = thumbnailInput.value.trim() === "";
		thumbnailInput.classList.toggle("danger", isThumbnailEmpty);
		thumbnailHint.style.display = isThumbnailEmpty ? "block" : "none";
	});
	
	// 제목 실시간 검사
	const titleInput = document.getElementById("titleInput");
	const titleHint = document.getElementById("titleHint");
	titleInput.addEventListener("input", function() {
		const isTitleEmpty = titleInput.value.trim() === "";
		titleInput.classList.toggle("danger", isTitleEmpty);
		titleHint.style.display = isTitleEmpty ? "block" : "none";
	});
	
	// 소개 실시간 검사
	const introduceInput = document.getElementById("introduceInput");
	const introduceHint = document.getElementById("introduceHint");
	introduceInput.addEventListener("input", function() {
		const isIntroduceEmpty = introduceInput.value.trim() === "";
		introduceInput.classList.toggle("danger", isIntroduceEmpty);
		introduceHint.style.display = isIntroduceEmpty ? "block" : "none";
	});
	
	// 난이도 선택 버튼 활성화 및 실시간 검사
    const levelButtons = document.querySelectorAll(".level_btn_group .level_btn");
    const levelInput = document.getElementById("levelInput");
	const levelHint = document.getElementById("levelHint");
    levelButtons.forEach(button => {
        button.addEventListener("click", function () {
            levelButtons.forEach(btn => btn.classList.remove("active"));
            this.classList.add("active");
            levelInput.value = this.getAttribute("data-value");
			const isLevelEmpty = levelInput.value.trim() === "";
			levelButtons.forEach(button => {
				button.classList.toggle("danger", isLevelEmpty);
			});
			levelHint.style.display = isLevelEmpty ? "block" : "none";
        });
    });

	// 조리 시간 실시간 검사
	const timeSelectbox = document.getElementById("timeSelectbox");
	const timeInput = document.getElementById("timeInput");
	const timeHint = document.getElementById("timeHint");
	timeSelectbox.addEventListener("change", function() {
		timeInput.value = this.value;
		const isTimeEmpty = timeInput.value.trim() === "";
		timeSelectbox.classList.toggle("danger", isTimeEmpty);
		timeHint.style.display = isTimeEmpty ? "block" : "none";
	});
	
	// 맵기 정도 선택 버튼 활성화 및 실시간 검사
	const spicyButtons =  document.querySelectorAll(".spicy_field .spicy_btn");
	const spicyInput = document.getElementById("spicyInput");
	const spicyHint = document.getElementById("spicyHint");
	spicyButtons.forEach(button => {
	    button.addEventListener("click", function () {
	        spicyButtons.forEach(btn => btn.classList.remove("active"));
	        this.classList.add("active");
	        spicyInput.value = this.getAttribute("data-value");
			const isSpicyEmpty = spicyInput.value.trim() === "";
			spicyButtons.forEach(button => {
				button.classList.toggle("danger", isSpicyEmpty);
			});
			spicyHint.style.display = isSpicyEmpty ? "block" : "none";
	    });
	});

	// 종류별 카테고리 실시간 검사
	const categoryTypeSelectbox = document.getElementById("categoryTypeSelectbox");
	const categoryTypeInput = document.getElementById("categoryTypeInput");
	const categoryHint = document.getElementById("categoryHint");
	categoryTypeSelectbox.addEventListener("change", function() {
		categoryTypeInput.value = this.value;
		const isCategoryTypeEmpty = categoryTypeInput.value.trim() === "";
		categoryTypeSelectbox.classList.toggle("danger", isCategoryTypeEmpty);
		categoryHint.style.display = isCategoryTypeEmpty ? "block" : "none";
	});

	// 상황별 카테고리 실시간 검사
	const categoryCaseSelectbox = document.getElementById("categoryCaseSelectbox");
	const categoryCaseInput = document.getElementById("categoryCaseInput");
	categoryCaseSelectbox.addEventListener("change", function() {
		categoryCaseInput.value = this.value;
		const isCategoryCaseEmpty = categoryCaseInput.value.trim() === "";
		categoryCaseSelectbox.classList.toggle("danger", isCategoryCaseEmpty);
		categoryHint.style.display = isCategoryCaseEmpty ? "block" : "none";
	});

	// 재료별 카테고리 실시간 검사
	const categoryIngredientSelectbox = document.getElementById("categoryIngredientSelectbox");
	const categoryIngredientInput = document.getElementById("categoryIngredientInput");
	categoryIngredientSelectbox.addEventListener("change", function() {
		categoryIngredientInput.value = this.value;
		const isCategoryIngredientEmpty = categoryIngredientInput.value.trim() === "";
		categoryIngredientSelectbox.classList.toggle("danger", isCategoryIngredientEmpty);
		categoryHint.style.display = isCategoryIngredientEmpty ? "block" : "none";
	});

	// 방법별 카테고리 실시간 검사
	const categoryWaySelectbox = document.getElementById("categoryWaySelectbox");
	const categoryWayInput = document.getElementById("categoryWayInput");
	categoryWaySelectbox.addEventListener("change", function() {
		categoryWayInput.value = this.value;
		const isCategoryWayEmpty = categoryWayInput.value.trim() === "";
		categoryWaySelectbox.classList.toggle("danger", isCategoryWayEmpty);
		categoryHint.style.display = isCategoryWayEmpty ? "block" : "none";
	});
	
	// 조리 양 실시간 검사
	const servingSelectbox = document.getElementById("servingSelectbox");
	const servingInput = document.getElementById("servingInput");
	const servingHint = document.getElementById("servingHint");
	servingSelectbox.addEventListener("change", function() {
		servingInput.value = this.value;
		const isServingEmpty = servingInput.value.trim() === "";
		servingSelectbox.classList.toggle("danger", isServingEmpty);
		servingHint.style.display = isServingEmpty ? "block" : "none";
	});
	
	// 재료 실시간 검사
	document.addEventListener("input", function(event) {
        if (event.target.classList.contains("ingredient_input")) {
            const ingredientRow = event.target.closest(".ingredient_row");
            if (!ingredientRow) return;
            const ingredientInput = ingredientRow.querySelector(".ingredient_input");
            const ingredientHint = ingredientRow.querySelector(".ingredient_hint");
            const isIngredientEmpty = ingredientInput.value.trim() === "";
            ingredientInput.classList.toggle("danger", isIngredientEmpty);
            ingredientHint.style.display = isIngredientEmpty ? "block" : "none";
        }
		
        if (event.target.classList.contains("ingredient_amount_input")) {
            const ingredientRow = event.target.closest(".ingredient_row");
            if (!ingredientRow) return;
            const ingredientAmountInput = ingredientRow.querySelector(".ingredient_amount_input");
            const ingredientHint = ingredientRow.querySelector(".ingredient_hint");
            const isIngredientAmountEmpty = ingredientAmountInput.value.trim() === "";
            ingredientAmountInput.classList.toggle("danger", isIngredientAmountEmpty);
            ingredientHint.style.display = isIngredientAmountEmpty ? "block" : "none";
        }
    });
	
	// 조리 순서 실시간 검사
	document.addEventListener("input", function(event) {
        if (event.target.classList.contains("step_input")) {
			const stepRow = event.target.closest(".step_row");
			if (!stepRow) return;
            const stepInput = stepRow.querySelector(".step_wrap .step_input");
            const stepHint = stepRow.querySelector(".step_wrap .step_hint");
            const isStepEmpty = stepInput.value.trim() === "";
            stepInput.classList.toggle("danger", isStepEmpty);
            stepHint.style.display = isStepEmpty ? "block" : "none";
        }
    });
	
    // 폼 제출 시 최종 검사
    document.getElementById("writeRecipeForm").addEventListener("submit", function (event) {
		let isValid = true;
		
		const stepRow = document.querySelectorAll(".step_row");
		stepRow.forEach((row) => {
			const stepInput = row.querySelector(".step_input");
			const stepHint = row.querySelector(".step_hint");
			if (stepInput.value.trim() === "") {
				event.preventDefault();
				stepInput.classList.add("danger");
				stepHint.style.display = "block";
				isValid = false;
			}
		});
		const ingredientRow = document.querySelectorAll(".ingredient_row");
		ingredientRow.forEach((row) => {
			const ingredientInput = row.querySelector(".ingredient_input");
			const ingredientAmountInput = row.querySelector(".ingredient_amount_input");
			const ingredientHint = row.querySelector(".ingredient_hint");
			if (ingredientInput.value.trim() === "") {
				event.preventDefault();
				ingredientInput.classList.add("danger");
				ingredientHint.style.display = "block";
				isValid = false;
			}
			if (ingredientAmountInput.value.trim() === "") {
				event.preventDefault();
				ingredientAmountInput.classList.add("danger");
				ingredientHint.style.display = "block";
				isValid = false;
			}
		});

		if (servingInput.value.trim() === "") {
			event.preventDefault();
			servingSelectbox.classList.add("danger");
			servingHint.style.display = "block";
			servingInput.focus();
			isValid = false;
		}
		if (categoryWayInput.value.trim() === "") {
			event.preventDefault();
			categoryWaySelectbox.classList.add("danger");
			categoryHint.style.display = "block";
			categoryWayInput.focus();
			isValid = false;
		}
		if (categoryIngredientInput.value.trim() === "") {
			event.preventDefault();
			categoryIngredientSelectbox.classList.add("danger");
			categoryHint.style.display = "block";
			categoryIngredientInput.focus();
			isValid = false;
		}
		if (categoryCaseInput.value.trim() === "") {
			event.preventDefault();
			categoryCaseSelectbox.classList.add("danger");
			categoryHint.style.display = "block";
			categoryCaseInput.focus();
			isValid = false;
		}
		if (categoryTypeInput.value.trim() === "") {
			event.preventDefault();
			categoryTypeSelectbox.classList.add("danger");
			categoryHint.style.display = "block";
			categoryTypeInput.focus();
			isValid = false;
		}
		if (spicyInput.value.trim() === "") {
			event.preventDefault();
			spicyButtons.forEach((button) => {
				button.classList.add("danger");
			});
			spicyHint.style.display = "block";
			document.querySelector(".spicy_btn").focus();
			isValid = false;
		}
		if (timeInput.value.trim() === "") {
			event.preventDefault();
			timeSelectbox.classList.add("danger");
			timeHint.style.display = "block";
			timeInput.focus();
			isValid = false;
		}
		if (levelInput.value.trim() === "") {
			event.preventDefault();
			levelButtons.forEach((button) => {
				button.classList.add("danger");
			});
			levelHint.style.display = "block";
			document.querySelector(".level_btn").focus();
			isValid = false;
		}
		if (introduceInput.value.trim() === "") {
			event.preventDefault();
			introduceInput.classList.add("danger");
			introduceHint.style.display = "block";
			introduceInput.focus();
			isValid = false;
		}
		if (titleInput.value.trim() === "") {
			event.preventDefault();
			titleInput.classList.add("danger");
			titleHint.style.display = "block";
			titleInput.focus();
			isValid = false;
		}
		if (thumbnailInput.value.trim() === "") {
			event.preventDefault();
			thumbnailInput.classList.add("danger");
			thumbnailHint.style.display = "block";
			thumbnailInput.focus();
			isValid = false;
		}

		const acceptNotice = document.getElementById("acceptNotice");
		if (isValid == true && !acceptNotice.checked) {
			event.preventDefault(); // 폼 제출 막기
			alert("유의사항을 확인하고 동의해야 제출할 수 있습니다.");
		}
    });
});