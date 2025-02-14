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
 * 다음 단계 작성하기 버튼 클릭 시 조리 순서 입력폼을 추가하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	document.getElementById("addStepBtn").addEventListener('click', function(event) {
        event.preventDefault();
		
		const ul = document.querySelector('.step_wrap ul');
		const li = document.createElement('li');
		const stepCount = ul.children.length + 1;
		
		li.innerHTML = `
			<div class="image_field">
				<label>STEP ${ stepCount }</label>
				<input type="file" id="imageInput" name="image" accept="image/*">
			</div>
			<div class="description_field">
				<input type="text" id="descriptionInput" name="" value="" placeholder="조리 방법을 자세히 작성해주세요">
				<div class="remove_btn">
					<a class="removeIngredientBtn">
						<img src="/images/recipe/close.png">지우기
					</a>
				</div>
			</div>
		`;
		ul.append(li);
		
		// 삭제 기능
		li.querySelector(".removeIngredientBtn").addEventListener('click', function(event) {
			event.preventDefault();
			li.remove();
			// 삭제 시 번호 재정렬
	        for (let i = 0; i < ul.children.length; i++) {
	            ul.children[i].querySelector("label").textContent = `STEP ${i + 1}`;
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
		if (thumbnailInput.value.trim() === "") {
			thumbnailInput.classList.add("danger");
			thumbnailHint.style.display = "block";
		}
		else {
			thumbnailInput.classList.remove("danger");
			thumbnailHint.style.display = "none";
		}
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
	const categoryTypeSelectBox = document.getElementById("categoryTypeSelectbox");
	const categoryTypeInput = document.getElementById("categoryTypeInput");
	categoryTypeSelectBox.addEventListener("change", function() {
		categoryTypeInput.value = this.value;
		const isCategoryTypeEmpty = categoryTypeInput.value.trim() === "";
		categoryTypeSelectBox.classList.toggle("danger", isCategoryTypeEmpty);
		categoryTypeInput.style.display = isCategoryTypeEmpty ? "block" : "none";
	});

	// 상황별 카테고리 실시간 검사
	const categoryCaseSelectbox = document.getElementById("categoryCaseSelectbox");
	const categoryCaseInput = document.getElementById("categoryCaseInput");
	categoryCaseSelectbox.addEventListener("change", function() {
		categoryCaseInput.value = this.value;
		const isCategoryCaseEmpty = categoryCaseInput.value.trim() === "";
		categoryCaseSelectbox.classList.toggle("danger", isCategoryCaseEmpty);
		categoryCaseInput.style.display = isCategoryCaseEmpty ? "block" : "none";
	});

	// 재료별 카테고리 실시간 검사
	const categoryIngredientSelectbox = document.getElementById("categoryIngredientSelectbox");
	const categoryIngredientInput = document.getElementById("categoryIngredientInput");
	categoryIngredientSelectbox.addEventListener("change", function() {
		categoryIngredientInput.value = this.value;
		const isCategoryIngredientEmpty = categoryIngredientInput.value.trim() === "";
		categoryIngredientSelectbox.classList.toggle("danger", isCategoryIngredientEmpty);
		categoryIngredientInput.style.display = isCategoryIngredientEmpty ? "block" : "none";
	});

	// 방법별 카테고리 실시간 검사
	const categoryWaySelectbox = document.getElementById("categoryWaySelectbox");
	const categorWayInput = document.getElementById("categorWayInput");
	categoryWaySelectbox.addEventListener("change", function() {
		categorWayInput.value = this.value;
		const isCategoryWayEmpty = categorWayInput.value.trim() === "";
		categoryWaySelectbox.classList.toggle("danger", isCategoryWayEmpty);
		categorWayInput.style.display = isCategoryWayEmpty ? "block" : "none";
	});

	// 조리양 실시간 검사
	servingInput.addEventListener("input", function() {
		if (servingInput.value.trim() === "") {
			servingInput.classList.add("danger");
			servingHint.style.display = "block";
		}
		else {
			servingInput.classList.remove("danger");
			servingHint.style.display = "none";
		}
	});
	
	// 재료 실시간 검사
	ingredientInput.addEventListener("input", function() {
		if (ingredientInput.value.trim() === "") {
			ingredientInput.classList.add("danger");
			ingredientHint.style.display = "block";
		}
		else {
			ingredientInput.classList.remove("danger");
			ingredientHint.style.display = "none";
		}
	});
	
	// 재료 양 실시간 검사
	ingredientAmountInput.addEventListener("input", function() {
		if (ingredientAmountInput.value.trim() === "") {
			ingredientAmountInput.classList.add("danger");
			ingredientHint.style.display = "block";
		}
		else {
			ingredientAmountInput.classList.remove("danger");
			ingredientHint.style.display = "none";
		}
	});
	
	
	
	
	// 여기 재료랑 순서 추가해야 되는데 고유한 Id가 필요할듯
	// acceptNotice 이건 체크했는지 확인
    // 폼 제출 시 최종 검사
	const form = document.getElementById("writeRecipeForm");
    form.addEventListener("submit", function (event) {
		
		if (spicyInput.value.trim() === "") {
			event.preventDefault();
			spicyButtons.forEach((button) => {
				button.classList.add("danger");
			});
			spicyHint.style.display = "block";
			document.querySelector(".spicy_btn").focus();
		}
		if (timeInput.value.trim() === "") {
			event.preventDefault();
			timeSelectbox.classList.add("danger");
			timeHint.style.display = "block";
			timeInput.focus();
		}
		if (levelInput.value.trim() === "") {
			event.preventDefault();
			levelButtons.forEach((button) => {
				button.classList.add("danger");
			});
			levelHint.style.display = "block";
			document.querySelector(".level_btn").focus();
		}
		if (introduceInput.value.trim() === "") {
			event.preventDefault();
			introduceInput.classList.add("danger");
			introduceHint.style.display = "block";
			introduceInput.focus();
		}
		if (titleInput.value.trim() === "") {
			event.preventDefault();
			titleInput.classList.add("danger");
			titleHint.style.display = "block";
			titleInput.focus();
		}
    });
});