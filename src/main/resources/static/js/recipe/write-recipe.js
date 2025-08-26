/**
 * 썸네일 이미지를 업로드하면 미리보기로 배경에 표시하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	const form = document.getElementById('writeRecipeForm');
	form.thumbnail.addEventListener('change', function(event) {
		const file = event.target.files[0];
		if (file) {
			const reader = new FileReader();
			reader.onload = function(e) {
				form.thumbnail.style.background = `url(${e.target.result})`;
				form.thumbnail.style.backgroundSize = 'cover';
				form.thumbnail.style.backgroundPosition = 'center'
				form.thumbnail.style.border = '1px solid #EEE';
			};
			reader.readAsDataURL(file);
		}
		else {
			// 위에서 주었던 효과 없애기
			form.thumbnail.style.background = 'none';
			form.thumbnail.classList.add('danger');
			document.querySelector('.thumbnail_field p').style.display = 'block';
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
	const form = document.getElementById('writeRecipeForm');
	
	// 제목 실시간 검사
	form.title.addEventListener('blur', function() {
		const isTitleEmpty = this.value.trim() === '';
		this.classList.toggle('danger', isTitleEmpty);
		document.querySelector('.title_field p').style.display = isTitleEmpty ? 'block' : 'none';
	});
	
	// 소개 실시간 검사
	form.introduce.addEventListener('blur', function() {
		const isIntroduceEmpty = this.value.trim() === '';
		this.classList.toggle('danger', isIntroduceEmpty);
		document.querySelector('.introduce_field p').style.display = isIntroduceEmpty ? 'block' : 'none';
	});
	
	// 난이도 선택 버튼 활성화 및 실시간 검사
    const levelButtons = document.querySelectorAll(".level_btn_group .level_btn");
    levelButtons.forEach(button => {
        button.addEventListener('click', function () {
            levelButtons.forEach(btn => btn.classList.remove('active'));
            this.classList.add('active');
            form.cooklevel.value = this.getAttribute("data-value");
			const isLevelEmpty = form.cooklevel.value.trim() === '';
			levelButtons.forEach(btn => {
				btn.classList.toggle('danger', isLevelEmpty);
			});
			document.querySelector('.level_field p').style.display = isLevelEmpty ? 'block' : 'none';
        });
    });

	// 조리 시간 실시간 검사
	 document.querySelector('.time_field select').addEventListener('change', function() {
		form.cooktime.value = this.value;
		const isTimeEmpty = form.cooktime.value.trim() === '';
		this.classList.toggle("danger", isTimeEmpty);
		document.querySelector('.time_field p').style.display = isTimeEmpty ? "block" : "none";
	});
	
	// 맵기 정도 선택 버튼 활성화 및 실시간 검사
	const spicyButtons =  document.querySelectorAll('.spicy_field .spicy_btn');
	spicyButtons.forEach(button => {
	    button.addEventListener('click', function () {
	        spicyButtons.forEach(btn => btn.classList.remove('active'));
	        this.classList.add('active');
	        form.spicy.value = this.getAttribute('data-value');
			const isSpicyEmpty = form.spicy.value.trim() === '';
			spicyButtons.forEach(btn => {
				btn.classList.toggle('danger', isSpicyEmpty);
			});
			document.querySelector('.spicy_field p').style.display = isSpicyEmpty ? "block" : "none";
	    });
	});

	// 종류별 카테고리 실시간 검사
	document.querySelector('.category_field select:nth-of-type(1)').addEventListener('change', function() {
		form.categoryType.value = this.value;
		const isCategoryTypeEmpty = form.categoryType.value.trim() === '';
		this.classList.toggle('danger', isCategoryTypeEmpty);
		document.querySelector('.category_field p').style.display = isCategoryTypeEmpty ? "block" : "none";
	});

	// 상황별 카테고리 실시간 검사
	document.querySelector('.category_field select:nth-of-type(2)').addEventListener('change', function() {
		form.categoryCase.value = this.value;
		const isCategoryCaseEmpty = form.categoryCase.value.trim() === '';
		this.classList.toggle('danger', isCategoryCaseEmpty);
		document.querySelector('.category_field p').style.display = isCategoryCaseEmpty ? 'block' : 'none';
	});

	// 재료별 카테고리 실시간 검사
	document.querySelector('.category_field select:nth-of-type(3)').addEventListener('change', function() {
		form.categoryIngredient.value = this.value;
		const isCategoryIngredientEmpty = form.categoryIngredient.value.trim() === '';
		this.classList.toggle('danger', isCategoryIngredientEmpty);
		document.querySelector('.category_field p').style.display = isCategoryIngredientEmpty ? 'block' : 'none';
	});

	// 방법별 카테고리 실시간 검사
	document.querySelector('.category_field select:nth-of-type(4)').addEventListener('change', function() {
		form.categoryWay.value = this.value;
		const isCategoryWayEmpty = form.categoryWay.value.trim() === '';
		this.classList.toggle('danger', isCategoryWayEmpty);
		document.querySelector('.category_field p').style.display = isCategoryWayEmpty ? 'block' : 'none';
	});
	
	// 조리 양 실시간 검사
	document.querySelector('.serving_field select').addEventListener('change', function() {
		form.serving.value = this.value;
		const isServingEmpty = form.serving.value.trim() === '';
		this.classList.toggle('danger', isServingEmpty);
		document.querySelector('.serving_field p').style.display = isServingEmpty ? 'block' : 'none';
	});
	
	// 재료 실시간 검사
	document.addEventListener('blur', function (event) {
		const target = event.target;

		if (target.classList.contains('ingredient_input') || target.classList.contains('ingredient_amount_input')) {
			const ingredientRow = target.closest('.ingredient_row');
			if (!ingredientRow) return;
			const isEmpty = target.value.trim() === '';
			target.classList.toggle('danger', isEmpty);
			ingredientRow.querySelector('.ingredient_hint').style.display = isEmpty ? 'block' : 'none';
		}
	}, true);

	
	// 조리 순서 실시간 검사
	document.addEventListener('blur', function(event) {
		if (event.target.classList.contains('step_input')) {
			const stepRow = event.target.closest('.step_row');
			if (!stepRow) return;
			const isEmpty = stepRow.querySelector('.step_input').value.trim() === '';
			stepRow.querySelector('.step_input').classList.toggle('danger', isEmpty);
			stepRow.querySelector('.step_hint').style.display = isEmpty ? 'block' : 'none';
		}
	}, true);

	
});



/**
 * 
 */
document.addEventListener('DOMContentLoaded', function() {
	
	const form = document.getElementById('writeRecipeForm');
	form.addEventListener('submit', async function(event) {
		event.preventDefault();
		let isValid = true;
		
		document.querySelectorAll('.step_row').forEach((row) => {
			if (row.querySelector('.step_input').value.trim() === '') {
				row.querySelector('.step_input').classList.add('danger');
				row.querySelector('.step_hint').style.display = 'block';
				isValid = false;
			}
		});
		
		document.querySelectorAll('.ingredient_row').forEach((row) => {
			if (row.querySelector('.ingredient_input').value.trim() === "") {
				event.preventDefault();
				row.querySelector('.ingredient_input').classList.add("danger");
				row.querySelector('.ingredient_hint').style.display = "block";
				isValid = false;
			}
			if (row.querySelector('.ingredient_amount_input').value.trim() === "") {
				event.preventDefault();
				row.querySelector('.ingredient_amount_input').classList.add("danger");
				row.querySelector('.ingredient_hint').style.display = "block";
				isValid = false;
			}
		});

		if (form.serving.value.trim() === '') {
			document.querySelector('.serving_field select').classList.add('danger');
			document.querySelector('.serving_field p').style.display = 'block';
			form.serving.focus();
			isValid = false;
		}
		
		if (form.categoryWay.value.trim() === '') {
			document.querySelector('.category_field select:nth-of-type(4)').classList.add('danger');
			document.querySelector('.category_field p').style.display = "block";
			form.categoryWay.focus();
			isValid = false;
		}
		
		if (form.categoryIngredient.value.trim() === '') {
			document.querySelector('.category_field select:nth-of-type(3)').classList.add('danger');
			document.querySelector('.category_field p').style.display = 'block';
			form.categoryIngredient.focus();
			isValid = false;
		}
		
		if (form.categoryCase.value.trim() === '') {
			document.querySelector('.category_field select:nth-of-type(2)').classList.add('danger');
			document.querySelector('.category_field p').style.display = 'block';
			form.categoryCase.focus();
			isValid = false;
		}
		
		if (form.categoryType.value.trim() === '') {
			document.querySelector('.category_field select:nth-of-type(1)').classList.add('danger');
			document.querySelector('.category_field p').style.display = 'block';
			form.categoryType.focus();
			isValid = false;
		}
		
		if (form.spicy.value.trim() === '') {
			document.querySelectorAll('.spicy_field .spicy_btn').forEach((button) => {
				button.classList.add('danger');
			});
			document.querySelector('.spicy_field p').style.display = 'block';
			document.querySelector('.spicy_btn').focus();
			isValid = false;
		}
		
		if (form.cooktime.value.trim() === '') {
			document.querySelector('.time_field select').classList.add('danger');
			document.querySelector('.time_field p').style.display ='block';
			form.cooktime.focus();
			isValid = false;
		}
		
		if (form.cooklevel.value.trim() === '') {
			document.querySelectorAll('.level_field .level_btn').forEach((button) => {
				button.classList.add('danger');
			});
			document.querySelector('.level_field p').style.display = 'block';
			document.querySelector('.level_btn').focus();
			isValid = false;
		}
		
		if (form.introduce.value.trim() === '') {
			form.introduce.classList.add('danger');
			document.querySelector('.introduce_field p').style.display = 'block';
			form.introduce.focus();
			isValid = false;
		}
		
		if (form.title.value.trim() === '') {
			form.title.classList.add('danger');
			document.querySelector('.title_field p').style.display = 'block';
			form.title.focus();
			isValid = false;
		}
		
		if (form.thumbnail.value.trim() === '') {
			form.thumbnail.classList.add('danger');
			document.querySelector('.thumbnail_field p').style.display = 'block';
			form.thumbnail.focus();
			isValid = false;
		}

		if (isValid == true && !form.notice.checked) {
			alert('유의사항을 확인하고 동의해야 제출할 수 있습니다.');
			isValid = false;
		}
		
		// 폼 제출
		if (isValid) {
			if (!isLoggedIn()) {
				redirectToLogin();
			}
			
			try {
				const payload = parseJwt(localStorage.getItem('accessToken'));
				
				const formdata = new FormData();
				formdata.append('recipeRequestDto', new Blob([JSON.stringify({
					title: form.title.value.trim(),
					image: form.image.value.trim(),
					introduce: form.introduce.value,
					cooklevel: form.cooklevel.value,
					cooktime: form.cooktime.value,
					spicy: form.spicy.value,
					portion: form.serving.value,
					tip: form.tip.value,
					youtube_url: "https://www.youtube.com/watch?v=dQw4w9WgXcQ",
					user_id: payload.id,
					category_dto_list: [
						{ type: '종류별', tag: form.categoryType.value },
						{ type: '상황별', tag: form.categoryCase.value },
						{ type: '재료별', tag: form.categoryIngredient.value },
						{ type: '방법별', tag: form.categoryWay.value }
					],
					
					stock_dto_list: getStockList(),
					step_dto_list: getStepList(),
										
				})], { type: 'application/json' }));
				
				
				const recipeImage = document.getElementById('writeRecipeImageInput'); 
			    if (recipeImage.files.length > 0) {
			        formdata.append('recipeImage', recipeImage.files[0]);
			    }
				
				console.log(formdata);
				
				const response = await instance.post('/recipes', formdata, {
					headers: {
						...getAuthHeaders(),
						'Content-Type': undefined
					}
				});
						
				if (response.data.code === 'RECIPE_CREATE_SUCCESS') {
					alert('맛있는 레시피가 등록되었어요!');
					location.href = '/recipe/viewRecipe.do';
				}
			}
			catch (error) {
				
			}
			
		}
		
	});
});



/**
 * 
 */
function getStockList() {
	const stockList = [];
	document.querySelectorAll('.ingredient_row').forEach(row => {
		const rawAmount = row.querySelector('.ingredient_amount_input').value;
		const parsed = rawAmount.match(/^(\d+)(.*)$/);
		const amount = parsed ? parsed[1].trim() : '';
		const unit = parsed ? parsed[2].trim() : '';

		stockList.push({
			name: row.querySelector('.ingredient_input').value,
			amount: amount,
			unit: unit,
			note: row.querySelector('.ingredient_note_input').value,
		});
	});
	return stockList;
}



/**
 * 
 */
function getStepList() {
	const stepList = [];
	document.querySelectorAll('.step_row').forEach(row => {
		stepList.push({
			content: row.querySelector('.step_input').value,
			image_url: "https://example.com/step.jpg",
		});
	});
	return stepList;
}




