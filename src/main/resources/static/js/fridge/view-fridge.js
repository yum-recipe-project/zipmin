/**
 * 접근 권한을 설정하는 함수
 */
document.addEventListener('DOMContentLoaded', async function() {
	
	try {
		await instance.get('/dummy');
	}
	catch (error) {
		redirectToLogin('/', true);
	}
	
});





/**
 * 초기 실행하는 함수
 */
document.addEventListener('DOMContentLoaded', async function() {
	fetchUserFridgeList();
	fetchUserMemoList();
});






/**
 * 서버에서 나의 냉장고 목록을 가져오는 함수
 */
async function fetchUserFridgeList() {
	
	// 사용자 냉장고 목록 조회
	try {
		const token = localStorage.getItem('accessToken');
		const id = parseJwt(token).id;
		
		const response = await instance.get(`users/${id}/fridges`, {
			headers: getAuthHeaders()
		});
		
		if (response.data.code === 'USER_FRIDGE_READ_LIST_SUCCESS') {
			// 렌더링
			renderUserFridgeList(response.data.data);
			
			// 검색 결과 없음 표시
			if (response.data.data.length === 0) {
				document.querySelector('.fridge_list').style.display = 'none';
				document.querySelector('.fridge_zone').style.display = 'none';
				document.querySelector('.fridge_empty_wrap')?.remove();
				const content = document.querySelector('.fridge_list');
				content.insertAdjacentElement('afterend', renderUserFridgeListEmpty());
			}
			// 검색 결과 표시
			else {
				document.querySelector('.fridge_empty_wrap')?.remove();
				document.querySelector('.fridge_list').style.display = '';
				document.querySelector('.fridge_zone').style.display = '';
			}
		}
	}
	catch (error) {
		const code = error?.response?.data?.code;
		
		if (code === 'USER_FRIDGE_READ_LIST_FAIL') {
			alertDanger('냉장고 목록 조회에 실패했습니다.');
		}
		else if (code === 'USER_FRIDGE_INVALID_INPUT') {
			alertDanger('입력값이 유효하지 않습니다.');
		}
		else if (code === 'USER_INVALID_INPUT') {
			alertDanger('입력값이 유효하지 않습니다.');
		}
		else if (code === 'USER_FRIDGE_UNAUTHORIZED_ACCESS') {
			alertDanger('로그인되지 않은 사용자입니다.');
		}
		else if (code === 'USER_FRIDGE_FORBIDDEN') {
			alertDanger('접근 권한이 없습니다.');
		}
		else if (code === 'USER_NOT_FOUND') {
			alertDanger('해당 사용자를 찾을 수 없습니다.');
		}
		else if (code === 'INTERNAL_SERVER_ERROR') {
			alertDanger('서버 내부에서 오류가 발생했습니다.');
		}
		else {
			console.log(error);
		}
	}
	
}





/**
 *  나의 냉장고 목록을 화면에 렌더링하는 함수
 */
function renderUserFridgeList(userFridgeList) {
	const container = document.querySelector('.fridge_list');
	container.innerHTML = '';
	
	userFridgeList.forEach(userFridge => {
		const li = document.createElement('li');
		li.className = 'fridge';
		li.dataset.id = userFridge.id;
		
		const divInfo = document.createElement('div');
		divInfo.className = 'fridge_info';
		
		const spanImage = document.createElement('span');
		spanImage.className = 'image';
		spanImage.style.backgroundImage = `url("${userFridge.image}")`;
		
		const divDetail = document.createElement('div');
		divDetail.className = 'detail';
		
		const divTitle = document.createElement('div');
		divTitle.className = 'title';
		
		const h4Name = document.createElement('h4');
		h4Name.className = 'name';
		h4Name.textContent = `${userFridge.name} ${userFridge.amount}${userFridge.unit}`;
		switch (userFridge.zone) {
			case '냉장' :
				h4Name.style.setProperty('--dot-color', 'rgb(157, 233, 252)');
				break;
			case '냉동' :
				h4Name.style.setProperty('--dot-color', 'rgb(108, 169, 190)');
				break;
			case '상온' :
				h4Name.style.setProperty('--dot-color', 'rgb(250, 216, 166)');
				break;
		}
		divTitle.appendChild(h4Name);
		
		const tabWrap = document.createElement('div');
		tabWrap.className = 'tab_wrap';
		
		const btnEdit = document.createElement('button');
		btnEdit.className = 'btn_sort sort_sm';
		btnEdit.type = 'button';
		btnEdit.textContent = '수정';
		btnEdit.dataset.bsToggle = 'modal';
		btnEdit.dataset.bsTarget = '#editUserFridgeModal';
		btnEdit.addEventListener('click', function(event) {
			event.preventDefault();
			const form = document.getElementById('editUserFridgeForm');
			form.id.value = userFridge.id;
			form.amount.value = userFridge.amount + userFridge.unit;
			form.expdate.value = userFridge.expdate.slice(0, 10);
			form.querySelector('.fridge_image').style.backgroundImage = `url("${userFridge.image}")`;
			form.querySelector('.fridge_name').textContent = userFridge.name;
			form.querySelector('.fridge_category').textContent = userFridge.category;
		});
		
		const btnDelete = document.createElement('button');
		btnDelete.className = 'btn_sort sort_sm';
		btnDelete.type = 'button';
		btnDelete.textContent = '삭제';
		btnDelete.addEventListener('click', function(event) {
			event.preventDefault();
			deleteUserFridge(userFridge.id);
		});
		
		tabWrap.append(btnEdit, btnDelete);
		divTitle.appendChild(tabWrap);
		
		const pCategory = document.createElement('p');
		pCategory.className = 'info';
		const bCat = document.createElement('b');
		bCat.textContent = `카테고리`;
		const spanCat = document.createElement('span');
		spanCat.className = 'category';
		spanCat.textContent = userFridge.category;
		pCategory.append(bCat, document.createTextNode('\u00A0\u00A0'), spanCat);
		
		const pExpdate = document.createElement('p');
		pExpdate.className = 'info';
		const bExp = document.createElement('b');
		bExp.textContent = '유통기한';
		const spanExp = document.createElement('span');
		spanExp.className = 'expdate';
		spanExp.textContent = `${formatDate(userFridge.expdate)}`;
		pExpdate.append(bExp, document.createTextNode('\u00A0\u00A0'), spanExp);
		
		divDetail.append(divTitle, pCategory, pExpdate);
		divInfo.append(spanImage, divDetail);
		li.appendChild(divInfo);
		container.appendChild(li);
  });
}





/**
 * 나의 냉장고 목록 결과 없음 화면을 화면에 렌더링하는 함수
 */
function renderUserFridgeListEmpty() {
	const wrapper = document.createElement('div');
	wrapper.className = 'fridge_empty_wrap';
	
    const div = document.createElement('div');
    div.className = 'fridge_list_empty';
	
    const span = document.createElement('span');
    span.textContent = '냉장고에 재료를 추가해보세요';
    div.appendChild(span);
	wrapper.appendChild(div);

    return wrapper;
}





/**
 * 사용자 냉장고를 수정하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	const form = document.getElementById('editUserFridgeForm');
	form.addEventListener('submit', async function(event) {
		event.preventDefault();
		let isValid = true;
		
		// 폼값 검사
		if (form.expdate.value.trim() === '') {
			form.expdate.classList.add('is-invalid');
			form.querySelector('.expdate_field p').style.display = 'block';
			form.expdate.focus();
			isValid = false;
		}

		const sheetAmountInput = form.amount;
		const match = sheetAmountInput.value.trim().match(/^(\d+)([a-zA-Z가-힣]+)$/);
		if (sheetAmountInput.value.trim() === '') {
			sheetAmountInput.classList.add('is-invalid');
			document.getElementById('editUserFridgeAmountHint1').style.display = 'block';
			sheetAmountInput.focus();
			isValid = false;
		}
		else if (!match) {
			sheetAmountInput.classList.add('is-invalid');
			document.getElementById('editUserFridgeAmountHint2').style.display = 'block';
			sheetAmountInput.focus();
			isValid = false;
		}
		
		if (isValid) {
			try {
				const userId = parseJwt(localStorage.getItem('accessToken')).id;
				const fridgeId = form.id.value;
								
				const data = {
					id: fridgeId,
				    amount: match[1],
				    unit: match[2],
				    expdate: form.expdate.value.trim(),
					fridge_id: fridgeId
				}
				
				const response = await instance.patch(`/users/${userId}/fridges/${fridgeId}`, data, {
					headers: getAuthHeaders()
				});
				
				if (response.data.code === 'USER_FRIDGE_UPDATE_SUCCESS') {
					bootstrap.Modal.getInstance(document.getElementById('editUserFridgeModal'))?.hide();
					alertPrimary('냉장고 재료 수정에 성공했습니다')
					fetchUserFridgeList();
				}
								
			}
			catch(error) {
				const code = error?.response?.data?.code;

				if (code === 'USER_FRIDGE_UPDATE_FAIL') {
					alertDanger('냉장고 재료  삭제에 실패했습니다.')
				}
				else if (code === 'USER_FRIDGE_INVALID_INPUT') {
					alertDanger('입력값이 유효하지 않습니다.');
				}
				else if (code === 'USER_INVALID_INPUT') {
					alertDanger('입력값이 유효하지 않습니다.');
				}
				else if (code === 'USER_FRIDGE_UNAUTHORIZED_ACCESS') {
					alertDanger('로그인되지 않은 사용자입니다.');
				}
				else if (code === 'USER_FRIDGE_FORBIDDEN') {
					alertDanger('접근 권한이 없습니다.');
				}
				else if (code === 'USER_FRIDGE_NOT_FOUND') {
					alertDanger('해당 사용자를 찾을 수 없습니다.');
				}
				else if (code === 'USER_NOT_FOUND') {
					alertDanger('해당 사용자를 찾을 수 없습니다.');
				}
				else if (code === 'INTERNAL_SERVER_ERROR') {
					alertDanger('서버 내부에서 오류가 발생했습니다.');
				}
				else {
					console.log(error);
				}
			}
		}
	});
})





/**
 * 냉장고 재료를 삭제하는 함수
 */
async function deleteUserFridge(fridgeId) {
	
	// 사용자 냉장고 삭제
	try {
		const userId = parseJwt(localStorage.getItem('accessToken')).id;
		
		const response = await instance.delete(`/users/${userId}/fridges/${fridgeId}`, {
			headers: getAuthHeaders()
		});
		
		if (response.data.code === 'USER_FRIDGE_DELETE_SUCCESS') {
			alertPrimary('냉장고 재료 삭제에 성공했습니다.');
			fetchUserFridgeList();
		}
	}
	catch (error) {
		const code = error?.response?.data?.code;
		
		if (code === 'USER_FRIDGE_DELETE_FAIL') {
			alertDanger('냉장고 재료 삭제에 실패했습니다.')
		}
		else if (code === 'USER_FRIDGE_INVALID_INPUT') {
			alertDanger('입력값이 유효하지 않습니다.');
		}
		else if (code === 'USER_INVALID_INPUT') {
			alertDanger('입력값이 유효하지 않습니다.');
		}
		else if (code === 'USER_FRIDGE_UNAUTHORIZED_ACCESS') {
			alertDanger('로그인되지 않은 사용자입니다.');
		}
		else if (code === 'USER_FRIDGE_FORBIDDEN') {
			alertDanger('접근 권한이 없습니다.');
		}
		else if (code === 'USER_NOT_FOUND') {
			alertDanger('해당 사용자를 찾을 수 없습니다.');
		}
		else if (code === 'INTERNAL_SERVER_ERROR') {
			alertDanger('서버 내부에서 오류가 발생했습니다.');
		}
		else {
			console.log(error);
		}
	}
}





/**
 * 서버에서 냉장고 파먹기 목록을 가져오는 함수
 */
document.addEventListener('DOMContentLoaded', async function() {

	try {
		const id = parseJwt(localStorage.getItem('accessToken')).id;
		
		const response = await instance.get(`/users/${id}/picked-fridges`, {
			headers: getAuthHeaders()
		});
		
		if (response.data.code === 'FRIDGE_PICK_LIST_SUCCESS') {
			// 렌더링
			renderPickList(response.data.data);
			
			// 검색 결과 없음 표시
			if (response.data.data.length === 0) {
				document.querySelector('.pick_title').style.display = 'none';
				document.querySelector('.pick_list').style.display = 'none';
				document.querySelector('.pick_list_empty')?.remove();
				const content = document.querySelector('.pick_list');
				content.insertAdjacentElement('afterend', renderPickListEmpty());
			}
			// 검색 결과 표시
			else {
				document.querySelector('.pick_list_empty')?.remove();
				document.querySelector('.pick_title').style.display = '';
				document.querySelector('.pick_list').style.display = '';
			}
		}
	}
	catch (error) {
		const code = error?.response?.data?.code;

		if (code === 'FRIDGE_PICK_LIST_SUCCESS') {
			alertDanger('냉장고 파먹기 목록 조회에 실패했습니다.')
		}
		else if (code === 'USER_FRIDGE_READ_LIST_FAIL') {
			alertDanger('나의 냉장고 목록 조회에 실패했습니다.')
		}
		else if (code === 'RECIPE_READ_LIST_FAIL') {
			alertDanger('레시피 목록 조회에 실패했습니다.')
		}
		else if (code === 'RECIPE_STOCK_READ_LIST_FAIL') {
			alertDanger('레시피 재로 목록 조회에 실패했습니다.')
		}
		else if (code === 'USER_FRIDGE_INVALID_INPUT') {
			alertDanger('입력값이 유효하지 않습니다.');
		}
		else if (code === 'USER_INVALID_INPUT') {
			alertDanger('입력값이 유효하지 않습니다.');
		}
		else if (code === 'USER_FRIDGE_UNAUTHORIZED_ACCESS') {
			alertDanger('로그인되지 않은 사용자입니다.');
		}
		else if (code === 'USER_FRIDGE_FORBIDDEN') {
			alertDanger('접근 권한이 없습니다.');
		}
		else if (code === 'USER_NOT_FOUND') {
			alertDanger('해당 사용자를 찾을 수 없습니다.');
		}
		else if (code === 'INTERNAL_SERVER_ERROR') {
			alertDanger('서버 내부에서 오류가 발생했습니다.');
		}
		else {
			console.log(error);
		}
	}
	
});





/**
 * 냉장고 파먹기 목록을 화면에 렌더링하는 함수
 */
async function renderPickList(pickList) {
	const container = document.querySelector('.pick_list');
	container.innerHTML = '';
	
	if (pickList === null) return;
	
	pickList.forEach((recipe, index) => {
		const li = document.createElement('li');
		
		const recipeDiv = document.createElement('div');
		recipeDiv.className = 'recipe';

		const numberSpan = document.createElement('span');
		numberSpan.textContent = index + 1;

		const titleP = document.createElement('p');
		titleP.textContent = recipe.title;

		recipeDiv.appendChild(numberSpan);
		recipeDiv.appendChild(titleP);

		// 일치율 표시
		if (recipe.rate >= 90) {
			const flagP = document.createElement('p');
			flagP.className = 'flag';
			flagP.textContent = `${recipe.rate}% 일치`;
			recipeDiv.appendChild(flagP);
		}

		li.appendChild(recipeDiv);
		container.appendChild(li);
	});
}






/**
 * 냉장고 파먹기 목록 결과 없음 화면을 화면에 렌더링하는 함수
 */
function renderPickListEmpty() {
    const wrapper = document.createElement('div');
    wrapper.className = 'pick_list_empty';
	
    const span = document.createElement('span');
    span.textContent = '냉장고 속 재료로 만들 수 있는 요리가 없습니다';
    wrapper.appendChild(span);

    return wrapper;
}




let page = 0;
let size = 10;
let totalPages = 0;
let memoList = [];


/**
 * 서버에서 장보기 메모를 가져오는 함수
 */
async function fetchUserMemoList() {
	
	// 사용자 냉장고 목록 조회
	try {
		
		// 요청 URL
		const token = localStorage.getItem('accessToken');
		const id = parseJwt(token).id;
		
		const response = await instance.get(`users/${id}/memos`, {
			headers: getAuthHeaders(),
		});
		
		if (response.data.code === 'MEMO_READ_LIST_SUCCESS') {
			memoList = response.data.data;
			
			// 검색 결과 없음 표시
			if (memoList.length === 0) {
				document.querySelector('.ingredient_list').style.display = 'none';
				const content = document.querySelector('.ingredient_list');
				content.insertAdjacentElement('afterend', renderMemoListEmpty());
			}
			// 검색 결과 표시
			else {
				renderMemoList(memoList);
			}
			
		}
	}
	catch (error) {
		console.log(error);
	}
	
}



/**
 * 장보기 메모 목록을 화면에 렌더링하는 함수
 */
function renderMemoList(memoList) {
    const memoContent = document.querySelector('.memo_content');
    const table = memoContent.querySelector('.ingredient_list');
    const btnWrap = memoContent.querySelector('.btn_wrap');

    table.innerHTML = '';
    btnWrap.innerHTML = '';

    const thead = document.createElement('thead');
    thead.innerHTML = `
        <tr>
            <th width="20%">재료</th>
            <th width="20%">용량</th>
            <th width="30%">비고</th>
            <th width="20%"> </th> <!-- 버튼 칸 -->
            <th width="10%">선택</th>
        </tr>
    `;
    table.appendChild(thead);

    const tbody = document.createElement('tbody');

    memoList.forEach(memo => {
        const tr = document.createElement('tr');

        const tdName = document.createElement('td');
        tdName.textContent = memo.name;

        const tdAmount = document.createElement('td');
        tdAmount.textContent = `${memo.amount}${memo.unit || ''}`;

        const tdNote = document.createElement('td');
        tdNote.textContent = memo.note || '';

        const tdButtons = document.createElement('td');

        const editBtn = document.createElement('button');
        editBtn.className = 'btn btn_tab memo_edit';
        editBtn.type = 'button';
        editBtn.textContent = '수정';
        editBtn.setAttribute('data-bs-toggle', 'modal');
        editBtn.setAttribute('data-bs-target', '#editMemoModal');
        editBtn.setAttribute('data-id', memo.id);
        editBtn.setAttribute('data-name', memo.name);
        editBtn.setAttribute('data-amount', memo.amount);
        editBtn.setAttribute('data-unit', memo.unit || '');
        editBtn.setAttribute('data-note', memo.note || '');
        
        const deleteBtn = document.createElement('button');
        deleteBtn.className = 'btn btn_tab memo_delete';
        deleteBtn.type = 'button';
        deleteBtn.textContent = '삭제';
        deleteBtn.addEventListener('click', async function() {
            if (!confirm('이 메모를 삭제하시겠습니까?')) return;
            try {
                const token = localStorage.getItem('accessToken');
                const userId = parseJwt(token).id;
                await instance.delete(`/users/${userId}/memos/${memo.id}`, {
                    headers: getAuthHeaders()
                });
                alertPrimary('메모가 삭제되었습니다.');
                fetchUserMemoList();
            } catch (error) {
                console.error(error);
                alertDanger('메모 삭제 중 오류가 발생했습니다.');
            }
        });

        tdButtons.append(editBtn, deleteBtn);

        // 선택 칸
        const tdSelect = document.createElement('td');
        const checkbox = document.createElement('input');
        checkbox.type = 'checkbox';
        checkbox.id = `memo_${memo.id}`;
        checkbox.name = 'selectedMemo';

        const label = document.createElement('label');
        label.setAttribute('for', `memo_${memo.id}`);

        tdSelect.appendChild(checkbox);
        tdSelect.appendChild(label);

        tr.append(tdName, tdAmount, tdNote, tdButtons, tdSelect);
        tbody.appendChild(tr);
    });

    table.appendChild(tbody);

    // 버튼 영역
    const completeBtn = document.createElement('button');
    completeBtn.className = 'btn_outline delete_memo';
    completeBtn.textContent = '장보기 완료';

    const addBtn = document.createElement('button');
    addBtn.className = 'btn_primary';
    addBtn.type = 'button';
    addBtn.setAttribute('data-bs-toggle', 'modal');
    addBtn.setAttribute('data-bs-target', '#writeMemoModal');
    addBtn.textContent = '추가하기';

    btnWrap.append(completeBtn, addBtn);
}




/**
 * 장보기 메모 목록 결과 없음 화면을 화면에 렌더링하는 함수
 */
function renderMemoListEmpty() {

	const wrapper = document.createElement('div');
  	wrapper.className = 'memo_empty_wrap';

	const list = document.createElement('div');
	list.className = 'memo_list_empty';
	wrapper.appendChild(list);
	
    const span = document.createElement('span');
    span.textContent = '작성한 장보기 메모가 없습니다.';
    list.appendChild(span);

	const btnWrap = document.querySelector('.btn_wrap');
	const addBtn = document.createElement('button');
	addBtn.className = 'btn_primary';
	addBtn.type = 'button';
	addBtn.setAttribute('data-bs-toggle', 'modal');
	addBtn.setAttribute('data-bs-target', '#writeMemoModal');
	addBtn.textContent = '추가하기';

	btnWrap.append(addBtn);
	

    return wrapper;
}




/**
 * 장보기 메모 수정 버튼 동작 (수정 모달 오픈)
 */
document.addEventListener("DOMContentLoaded", function() {
    const editMemoModalEl = document.getElementById('editMemoModal');

    editMemoModalEl.addEventListener('show.bs.modal', function (event) {
        const button = event.relatedTarget; 
        const memoId = button.getAttribute('data-id');
        const name = button.getAttribute('data-name');
        const amount = button.getAttribute('data-amount');
        const unit = button.getAttribute('data-unit');
        const note = button.getAttribute('data-note');

        // form input 채우기
        const form = document.getElementById('editMemoForm');
        form.dataset.memoId = memoId; // 수정 요청시 사용
        form.name.value = name;
        form.amount.value = amount + unit; 
        form.note.value = note;
    });
});



/**
 * 장보기 메모 완료 기능
 */
/**
 * 장보기 메모 완료 기능 (체크한 메모 삭제)
 */
document.addEventListener('click', async function(event) {
    if (event.target.classList.contains('delete_memo')) {
		
        const selectedCheckboxes = document.querySelectorAll('input[name="selectedMemo"]:checked');
        const selectedMemoIds = Array.from(selectedCheckboxes).map(cb => cb.id.replace('memo_', ''));

		console.log(selectedMemoIds);
		
        if (selectedMemoIds.length === 0) {
            alertDanger('삭제할 메모를 선택해주세요.');
            return;
        }

        try {
            const token = localStorage.getItem('accessToken');
            const userId = parseJwt(token).id;

            for (const memoId of selectedMemoIds) {
                await instance.delete(`/users/${userId}/memos/${memoId}`, {
                    headers: getAuthHeaders()
                });
            }

            fetchUserMemoList();

            alertPrimary('선택한 메모가 삭제되었습니다.');

        } catch (error) {
            console.error(error);
            alertDanger('메모 삭제 중 오류가 발생했습니다.');
        }
    }
});





