/**
 * 서버에서 레시피 데이터를 가져오는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	const modal = document.getElementById('viewRecipeModal');
	modal.addEventListener('show.bs.modal', async function(event) {
		
		// 폼
		const basicForm = document.getElementById('viewRecipeBasicForm');
		const stockForm = document.getElementById('viewRecipeStockForm');
		const stepForm = document.getElementById('viewRecipeStepForm');
		const tipForm = document.getElementById('viewRecipeTipForm');
		
		// 레시피 조회
		try {
			const id = event.relatedTarget?.dataset.id;
			
			const response = await instance.get(`/recipes/${id}`, {
				headers: getAuthHeaders()
			});
			
			console.log(response);
			
			if (response.data.code === 'RECIPE_READ_SUCCESS') {
				// 기본 정보
				basicForm.querySelector('.recipe_title').innerText = response.data.data.title;
				basicForm.querySelector('.recipe_cooklevel').innerText = response.data.data.cooklevel;
				basicForm.querySelector('.recipe_cooktime').innerText = response.data.data.cooktime;
				basicForm.querySelector('.recipe_spicy').innerText = response.data.data.spicy;
				basicForm.querySelector('.recipe_writer img').src = response.data.data.avatar;
				basicForm.querySelector('.recipe_writer span').innerText = response.data.data.nickname;
				basicForm.querySelector('.recipe_introduce').innerText = response.data.data.introduce;
				response.data.data.category_list.forEach(category => {
					const span = document.createElement('span');
					span.textContent = `# ${category.tag}`;
					basicForm.querySelector('.recipe_category').appendChild(span);
				});
				
				// 재료
				stockForm.querySelector('.recipe_portion').innerText = `( ${response.data.data.portion} 기준 )`;
				response.data.data.stock_list.forEach(stock => {
					const tr = document.createElement('tr');
					// 이름
					const tdName = document.createElement('td');
					tdName.textContent = stock.name;
					tr.appendChild(tdName);
					// 양
					const tdAmount = document.createElement('td');
					tdAmount.className = 'amount';
					tdAmount.dataset.amount = stock.amount;
					tdAmount.dataset.unit = stock.unit;
					tdAmount.textContent = `${stock.amount}${stock.unit}`;
					tr.appendChild(tdAmount);
					// 비고
					const tdNote = document.createElement('td');
					tdNote.textContent = stock.note;
					tr.appendChild(tdNote);
					stockForm.querySelector('.stock').appendChild(tr);
				});
				
				// 조리 과정
				response.data.data.step_list.forEach((step, index) => {
					const li = document.createElement('li');
					const contentDiv = document.createElement('div');
					contentDiv.className = 'step_content';
					// 제목
					const h5 = document.createElement('h5');
					h5.textContent = `STEP${index + 1}`;
					contentDiv.appendChild(h5);
					// 내용
					const p = document.createElement('p');
					p.textContent = step.content;
					contentDiv.appendChild(p);
					li.appendChild(contentDiv);
					// 이미지
					const imageDiv = document.createElement('div');
					imageDiv.className = 'step_image';
					const img = document.createElement('img');
					img.src = step.image;
					imageDiv.appendChild(img);
					if (step.image) {
						li.appendChild(imageDiv);
					}
					stepForm.querySelector('.step_list').appendChild(li);
				});
				
				// 팁
				tipForm.querySelector('.recipe_tip p').innerText = response.data.data.tip;
			}
		}
		catch(error) {
			const code = error?.response?.data?.code;
			
			// *** TODO : 에러 코드 추가 ***
			if (code === 'RECIPE_READ_FAIL') {
				alertDanger('레시피 조회에 실패했습니다.');
			}
			else if (code === 'INTERNAL_SERVER_ERROR') {
				alertDanger('서버 내부 오류가 발생했습니다.');
			}
			else {
				console.log(error);
			}
		}
		
	});
});
