




/**
 * 레시피 작성 폼을 실시간으로 검증하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	// 레시피 작성 폼
	const form = document.getElementById('writeRecipeForm');
	
	// 이미지 선택시 적용
	form.image.addEventListener('change', function(event) {
		const file = event.target.files[0];
		if (file) {
			const reader = new FileReader();
			reader.onload = function(e) {
				form.image.style.background = `url(${e.target.result})`;
				form.image.style.backgroundSize = 'cover';
				form.image.style.backgroundPosition = 'center'
				form.image.style.border = '1px solid #EEE';
			};
			reader.readAsDataURL(file);
		}
		else {
			form.image.classList.add('danger');
			form.querySelector('.image_field p').style.display = 'block';
		}
	});
	
	// 제목 실시간 검사
	form.title.addEventListener('blur', function() {
		const isTitleEmpty = this.value.trim() === '';
		this.classList.toggle('danger', isTitleEmpty);
		form.querySelector('.title_field p').style.display = isTitleEmpty ? 'block' : 'none';
	});
	
	// 소개 실시간 검사
	form.introduce.addEventListener('blur', function() {
		const isIntroduceEmpty = this.value.trim() === '';
		this.classList.toggle('danger', isIntroduceEmpty);
		form.querySelector('.introduce_field p').style.display = isIntroduceEmpty ? 'block' : 'none';
	});
	
	// 난이도 실시간 검사
	form.querySelectorAll('input[name="cooklevelRadio"]').forEach(radio => {
		radio.addEventListener('change', function() {
			form.cooklevel.value = this.value;
			const isCooklevelEmpty = form.cooklevel.value.trim() === '';
			form.querySelector('.cooklevel_field p').style.display = isCooklevelEmpty ? 'block' : 'none';
		});
	});
	
	// 조리 시간 실시간 검사
	form.querySelectorAll('input[name="cooktimeRadio"]').forEach(radio => {
		radio.addEventListener('change', function() {
			form.cooktime.value = this.value;
			const isCooktimeEmpty = form.cooktime.value.trim() === '';
			form.querySelector('.cooktime_field p').style.display = isCooktimeEmpty ? 'block' : 'none';
		});
	});
	
	// 맵기 정도 실시간 검사
	form.querySelectorAll('input[name="spicyRadio"]').forEach(radio => {
		radio.addEventListener('change', function() {
			form.spicy.value = this.value;
			const isSpicyEmpty = form.spicy.value.trim() === '';
			form.querySelector('.spicy_field p').style.display = isSpicyEmpty ? 'block' : 'none';
		});
	});
	
	// *** TODO : 모든 카테고리에 danger 걸렸다가 해제되는 동작 수정 ***
	// 종류별 카테고리 실시간 검사
	form.querySelector('.category_field select:nth-of-type(1)').addEventListener('change', function() {
		form.categoryType.value = this.value;
		const isCategoryTypeEmpty = form.categoryType.value.trim() === '';
		this.classList.toggle('danger', isCategoryTypeEmpty);
		form.querySelector('.category_field p').style.display = isCategoryTypeEmpty ? "block" : "none";
	});

	// 상황별 카테고리 실시간 검사
	form.querySelector('.category_field select:nth-of-type(2)').addEventListener('change', function() {
		form.categoryCase.value = this.value;
		const isCategoryCaseEmpty = form.categoryCase.value.trim() === '';
		this.classList.toggle('danger', isCategoryCaseEmpty);
		form.querySelector('.category_field p').style.display = isCategoryCaseEmpty ? 'block' : 'none';
	});

	// 재료별 카테고리 실시간 검사
	form.querySelector('.category_field select:nth-of-type(3)').addEventListener('change', function() {
		form.categoryIngredient.value = this.value;
		const isCategoryIngredientEmpty = form.categoryIngredient.value.trim() === '';
		this.classList.toggle('danger', isCategoryIngredientEmpty);
		form.querySelector('.category_field p').style.display = isCategoryIngredientEmpty ? 'block' : 'none';
	});

	// 방법별 카테고리 실시간 검사
	form.querySelector('.category_field select:nth-of-type(4)').addEventListener('change', function() {
		form.categoryWay.value = this.value;
		const isCategoryWayEmpty = form.categoryWay.value.trim() === '';
		this.classList.toggle('danger', isCategoryWayEmpty);
		form.querySelector('.category_field p').style.display = isCategoryWayEmpty ? 'block' : 'none';
	});
	
});





/**
 * 레시피 재료 작성 폼을 실시간으로 검증하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	// 레시피 재료 작성 폼
	const form = document.getElementById('writeRecipeStockForm');
	
	// 조리 양 실시간 검사
	form.querySelector('.portion_field select').addEventListener('change', function() {
		form.portion.value = this.value;
		const isPortionEmpty = form.portion.value.trim() === '';
		this.classList.toggle('danger', isPortionEmpty);
		form.querySelector('.portion_field p').style.display = isPortionEmpty ? 'block' : 'none';
	});
	
	// 재료 실시간 검사
	// *** TODO : 재료 실시간 검사 수정 ***
	form.querySelectorAll('.stock_field .input_group').forEach(stock => {
		stock.addEventListener('blur', function() {
			const nameInput = stock.querySelector('input[name="name"]');
			const amountInput = stock.querySelector('input[name="amount"]');
			const isAmountNotMatch = !form.amount.value.trim().match(/^(\d+)([a-zA-Z가-힣]+)$/);
			const isNameEmpty = nameInput.value.trim() === '';
			const isAmountEmpty = amountInput.value.trim() === '';
			nameInput.classList.toggle('danger', (isNameEmpty && isAmountEmpty) || (!isAmountEmpty && isAmountNotMatch));
			amountInput.classList.toggle('danger', (isNameEmpty && isAmountEmpty) || (!isAmountEmpty && isAmountNotMatch));
			form.querySelector('.stock_field p:nth-of-type(1)').style.display = isNameEmpty && isAmountEmpty ? 'block' : 'none';
			form.querySelector('.stock_field p:nth-of-type(2)').style.display = !isNameEmpty && !isAmountEmpty && isAmountNotMatch ? 'block' : 'none';
		}, true);
	});
	
});





/**
 * 레시피 조리 과정 작성 폼을 실시간으로 검증하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {

	// 레시피 조리과정 작성 폼
	const form = document.getElementById('writeRecipeStepForm');
	
	// 조리과정 실시간 검사
	form.querySelectorAll('.step_field .form-textarea').forEach(step => {
		step.addEventListener('blur', function() {
			const isStepEmpty = step.querySelector('textarea[name="content"]').value.trim() === '';
			this.classList.toggle('danger', isStepEmpty);
			form.querySelector('.step_field p').style.display = isStepEmpty ? 'block' : 'none';
		}, true);
	});
	
});





/**
 * 재료 추가하기 버튼 클릭 시 재료 입력폼을 추가하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	document.getElementById("writeRecipeAddStockButton").addEventListener('click', function(event) {
		event.preventDefault()
		
		const container = document.querySelector('.stock_field ul');
		const li = document.createElement('li');
		li.classList.add('input_group');
		
		const nameInput = document.createElement('input');
		nameInput.type = 'text';
		nameInput.name = 'name';
		nameInput.placeholder = '재료';
		
		const amountInput = document.createElement('input');
		amountInput.type = 'text';
		amountInput.name = 'amount';
		amountInput.placeholder = '단위가 포함된 양';
		
		const noteInput = document.createElement('input');
		noteInput.type = 'text';
		noteInput.name = 'note';
		noteInput.placeholder = '비고 (선택사항)';
		
		const removeBtn = document.createElement('button');
		removeBtn.className = 'remove_stock_btn';
		const removeIcon = document.createElement('img');
		removeIcon.src = '/images/recipe/close.png';
		removeBtn.appendChild(removeIcon);
		removeBtn.appendChild(document.createTextNode('지우기'));
		
		li.append(nameInput, amountInput, noteInput, removeBtn);
		container.appendChild(li);
		
		// 삭제 이벤트
		removeBtn.addEventListener('click', function(event) {
			event.preventDefault();
			li.remove();
		});
		
		// 실시간 검사
		li.addEventListener('blur', function() {
			const isStockEmpty = nameInput.value.trim() === '' || amountInput.value.trim() === '';
			nameInput.classList.toggle('danger', isStockEmpty);
			amountInput.classList.toggle('danger', isStockEmpty);
			document.querySelector('.stock_field p').style.display = isStockEmpty ? 'block' : 'none';
		}, true);
	});
	
});





/**
 * 다음 단계 작성하기 버튼 클릭 시 조리 순서 입력폼을 추가하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	document.getElementById('writeRecipeAddStepButton').addEventListener('click', function(event) {
		event.preventDefault();
		
		const container = document.querySelector('.step_field ul');
		const li = document.createElement('li');
		
		const stepNumber = document.createElement('h5');
		stepNumber.textContent = `STEP ${container.children.length + 1}`;
		
		const formTextarea = document.createElement('div');
		formTextarea.className = 'form-textarea';
		
		const textarea = document.createElement('textarea');
		textarea.name = 'content';
		textarea.rows = 2;
		textarea.cols = 30;
		textarea.placeholder = '조리 방법을 자세히 작성해주세요 (사진 첨부는 선택사항입니다)';
		
		const fileInput = document.createElement('input');
		fileInput.type = 'file';
		fileInput.name = 'image';
		fileInput.accept = 'image/*';
		
		const removeBtn = document.createElement('button');
		removeBtn.type = 'button';
		removeBtn.className = 'remove_step_btn';
		const removeIcon = document.createElement('img');
		removeIcon.src = '/images/recipe/close.png';
		removeBtn.appendChild(removeIcon);
		removeBtn.appendChild(document.createTextNode('지우기'));

		formTextarea.append(textarea, fileInput, removeBtn);

		li.append(stepNumber, formTextarea);
		container.appendChild(li);

		// 삭제 기능
		li.querySelector('.remove_step_btn').addEventListener('click', function(event) {
			event.preventDefault();
			li.remove();
			const stepList = container.children;
			for (let i = 0; i < stepList.length; i++) {
				stepList[i].querySelector('h5').textContent = `STEP ${ i + 1 }`;
			}
		});
		
		// 실시간 검사
		textarea.addEventListener('blur', function() {
			const isStepEmpty = textarea.value.trim() === '';
			formTextarea.classList.toggle('danger', isStepEmpty);
			document.querySelector('.step_field p').style.display = isStepEmpty ? 'block' : 'none';
		});
	});
});





/**
 * 레시피를 작성하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	document.getElementById('submitWriteRecipeFormButton').addEventListener('click', async function(event) {
		event.preventDefault();
		
		const recipeForm = document.getElementById('writeRecipeForm');
		const recipeStockForm = document.getElementById('writeRecipeStockForm');
		const recipeStepForm = document.getElementById('writeRecipeStepForm');
		const recipeNoticeForm = document.getElementById('writeRecipeNoticeForm');
		let isValid = true;
			
		recipeStepForm.querySelectorAll('.step_field .form-textarea').forEach(step => {
			if(step.querySelector('textarea[name="content"]').value.trim() === '') {
				step.classList.add('danger');
				recipeStepForm.querySelector('.step_field p').style.display = 'block';
				isValid = false;
			}
		});
		
		recipeStockForm.querySelectorAll('.stock_field .input_group').forEach(stock => {
			const nameInput = stock.querySelector('input[name="name"]');
			const amountInput = stock.querySelector('input[name="amount"]');
			if(nameInput.value.trim() === '' || amountInput.value.trim() === '') {
				nameInput.classList.add('danger');
				amountInput.classList.add('danger');
				recipeStockForm.querySelector('.stock_field p').style.display = 'block';
				isValid = false;
			}
		});
	
		if (recipeStockForm.portion.value.trim() === '') {
			recipeStockForm.querySelector('.portion_field .form-select').classList.add('danger');
			recipeStockForm.querySelector('.portion_field p').style.display = 'block';
			isValid = false;
		}
		
		if (recipeForm.categoryWay.value.trim() === '') {
			recipeForm.querySelector('.category_field select:nth-of-type(4)').classList.add('danger');
			recipeForm.querySelector('.category_field p').style.display = "block";
			recipeForm.categoryWay.focus();
			isValid = false;
		}
		
		if (recipeForm.categoryIngredient.value.trim() === '') {
			recipeForm.querySelector('.category_field select:nth-of-type(3)').classList.add('danger');
			recipeForm.querySelector('.category_field p').style.display = 'block';
			recipeForm.categoryIngredient.focus();
			isValid = false;
		}
		
		if (recipeForm.categoryCase.value.trim() === '') {
			recipeForm.querySelector('.category_field select:nth-of-type(2)').classList.add('danger');
			recipeForm.querySelector('.category_field p').style.display = 'block';
			recipeForm.categoryCase.focus();
			isValid = false;
		}
		
		if (recipeForm.categoryType.value.trim() === '') {
			recipeForm.querySelector('.category_field select:nth-of-type(1)').classList.add('danger');
			recipeForm.querySelector('.category_field p').style.display = 'block';
			recipeForm.categoryType.focus();
			isValid = false;
		}
		
		if (recipeForm.spicy.value.trim() === '') {
			recipeForm.querySelector('.spicy_field p').style.display = 'block';
			recipeForm.spicy.focus();
			isValid = false;
		}
		
		if (recipeForm.cooktime.value.trim() === '') {
			recipeForm.querySelector('.cooktime_field p').style.display ='block';
			recipeForm.cooktime.focus();
			isValid = false;
		}
		
		if (recipeForm.cooklevel.value.trim() === '') {
			recipeForm.querySelector('.cooklevel_field p').style.display = 'block';
			recipeForm.cooklevel.focus();
			isValid = false;
		}
		
		if (recipeForm.introduce.value.trim() === '') {
			recipeForm.introduce.classList.add('danger');
			recipeForm.querySelector('.introduce_field p').style.display = 'block';
			recipeForm.introduce.focus();
			isValid = false;
		}
		
		if (recipeForm.title.value.trim() === '') {
			recipeForm.title.classList.add('danger');
			recipeForm.querySelector('.title_field p').style.display = 'block';
			recipeForm.title.focus();
			isValid = false;
		}
		
		if (recipeForm.image.value.trim() === '') {
			recipeForm.image.classList.add('danger');
			recipeForm.querySelector('.image_field p').style.display = 'block';
			recipeForm.image.focus();
			isValid = false;
		}
		
		if (isValid == true && !recipeNoticeForm.notice.checked) {
			alert('유의사항을 확인하고 동의해야 제출할 수 있습니다.');
			isValid = false;
		}
		
		// 레시피 작성
		if (isValid) {
			try {
				const id = parseJwt(localStorage.getItem('accessToken')).id;
				
				const formdata = new FormData();
				formdata.append('recipeRequestDto', new Blob([JSON.stringify({
					title: recipeForm.title.value.trim(),
					introduce: recipeForm.introduce.value.trim(),
					cooklevel: recipeForm.cooklevel.value.trim(),
					cooktime: recipeForm.cooktime.value.trim(),
					spicy: recipeForm.spicy.value.trim(),
					tip: recipeForm.tip.value.trim(),
					user_id: id,
					category_dto_list: [
						{ type: '종류별', tag: recipeForm.categoryType.value.trim() },
						{ type: '상황별', tag: recipeForm.categoryCase.value.trim() },
						{ type: '재료별', tag: recipeForm.categoryIngredient.value.trim() },
						{ type: '방법별', tag: recipeForm.categoryWay.value.trim() }
					],
					
					portion: recipeStockForm.portion.value.trim(),
					stock_dto_list: Array.from(recipeStockForm.querySelectorAll('.input_group')).map(stock => {
					    return {
					        name: stock.querySelector('input[name="name"]').value.trim(),
					        amount: stock.querySelector('input[name="amount"]').value.match(/^(\d+)(.*)$/)[1].trim(),
					        unit: stock.querySelector('input[name="amount"]').value.match(/^(\d+)(.*)$/)[2].trim(),
					        note: stock.querySelector('input[name="note"]').value.trim(),
					    };
					}),
					
					step_dto_list: Array.from(recipeStepForm.querySelectorAll('.form-textarea')).map(step => {
						return {
							content: step.querySelector('textarea[name="content"]').value.trim()
						}
					})
				})], { type: 'application/json' }));
			
				const recipeImage = recipeForm.image; 
				if (recipeImage.files.length > 0) {
			        formdata.append('recipeImage', recipeImage.files[0]);
			    }
				
				recipeStepForm.querySelectorAll('.step_field input[name="image"]').forEach((recipeImage, idx) => {
				    if (recipeImage.files.length > 0) {
				        formdata.append(`stepImageMap[${idx}]`, recipeImage.files[0]);
				    }
				});

				const response = await instance.post('/recipes', formdata, {
					headers: {
						...getAuthHeaders(),
						'Content-Type': undefined
					}
				});
				
				if (response.data.code === 'RECIPE_CREATE_SUCCESS') {
					location.href = `/recipe/viewRecipe.do?id=${response.data.data.id}`;
				}
				
			}
			catch (error) {
				console.log(error);
				
				// *** TODO : 에러 코드 추가 ***
			}
		}

	});
});



