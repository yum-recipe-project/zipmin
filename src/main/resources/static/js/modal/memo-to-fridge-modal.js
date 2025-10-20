
/**
 * 장보기 완료 모달 폼 검증
 */
function validateCompleteMemoForm() {
    const modal = document.getElementById('memoToFridgeModal');
    const rows = modal.querySelectorAll('.complete_memo_list tr');
    let isValid = true;

    rows.forEach(row => {
        // 카테고리
        const categorySelect = row.querySelector('.memoInfo-category');
        if (!categorySelect.value || categorySelect.value.trim() === '') {
            categorySelect.classList.add('is-invalid');
            isValid = false;
        } else {
            categorySelect.classList.remove('is-invalid');
        }

        // 보관방법
        const storageSelect = row.querySelector('.memoInfo-storage');
        if (!storageSelect.value || storageSelect.value.trim() === '') {
            storageSelect.classList.add('is-invalid');
            isValid = false;
        } else {
            storageSelect.classList.remove('is-invalid');
        }

        // 소비기한
        const expInput = row.querySelector('.memoInfo-expdate');
        if (!expInput.value || expInput.value.trim() === '') {
            expInput.classList.add('is-invalid');
            isValid = false;
        } else {
            expInput.classList.remove('is-invalid');
        }
    });

    if (!isValid) {
        alertDanger('모든 재료의 카테고리, 보관방법, 소비기한을 선택해주세요.');
    }

    return isValid;
}





/**
 * 메모 -> 냉장고
 */
document.addEventListener('DOMContentLoaded', function() {
	
	const form = document.getElementById('memoToFridgeForm');

	form.addEventListener('submit', async function(event) {
		event.preventDefault();
		// 폼값 검증
		if (!validateCompleteMemoForm()) return;
		
		try {
		    const rows = document.querySelectorAll('.complete_memo_list tr');
		    const fridgeItems = Array.from(rows).map(tr => ({
				memoId: tr.dataset.memoId, 
		        name: tr.querySelector('.memoInfo-name').textContent.trim(),
		        amount: tr.querySelector('.memoInfo-amount').textContent.trim(),
		        category: tr.querySelector('.memoInfo-category').value.trim(),
		        zone: tr.querySelector('.memoInfo-storage').value.trim(),
		        expdate: tr.querySelector('.memoInfo-expdate').value.trim()
		    }));
			
		    if (fridgeItems.length === 0) {
		        alertDanger('냉장고에 추가할 재료가 없습니다.');
		        return;
		    }

		    const userId = parseJwt(localStorage.getItem('accessToken')).id;
			
			for (const item of fridgeItems) {
				// 냉장고 작성
			    const fridgeId = await createFridge(item);
			    if (fridgeId) {
					// 사용자 냉장고 입력
			        await createUserFridge(userId, item, fridgeId);

			        if (item.memoId) {
						// 메모에서 삭제
			            await deleteMemo(item.memoId);
			        }
			    }
			}
		    alertPrimary('장보기 완료된 재료들이 냉장고에 입력되었습니다.');
		    bootstrap.Modal.getInstance(document.getElementById('memoToFridgeModal'))?.hide();
		    fetchUserFridgeList();
			fetchUserMemoList();
		} catch (error) {
		    console.error(error);
		    alertDanger('재료를 냉장고에 담는 중 오류가 발생했습니다.');
		}
	});
	
});





/**
 * 재료별 냉장고 생성
 */
async function createFridge(item) {
    try {
		// 카테고리별 이미지 매핑
		const categoryImages = {
		    '육류': '/images/fridge/pig.png',
		    '어패류': '/images/fridge/fish.png',
		    '채소류': '/images/fridge/grass.png',   
		    '과일류': '/images/fridge/apple.png',
		    '견과류': '/images/fridge/almond.png',
		    '유제품': '/images/fridge/milk.png',
		    '완제품': '/images/fridge/dinner.png',
		    '소스류': '/images/fridge/ketchup.png',   
		    '기타': '/images/fridge/cutlery.png'  
		};

		const imagePath = categoryImages[item.category] || '/images/fridge/cutlery.png';
		
        const data = {
            image: imagePath,
            name: item.name,
            category: item.category,
            zone: item.zone
        };

        const response = await instance.post(`/fridges`, data, {
            headers: getAuthHeaders()
        });

        if (response.data.code === 'FRIDGE_CREATE_SUCCESS') {
            const fridgeId = response.data.data.id;
            return fridgeId;
        } else {
            return null;
        }
    } 
	catch (error) {
		const code = error?.response?.data?.code;

		if (code === 'FRIDGE_CREATE_FAIL') {
			alertDanger('냉장고 작성에 실패했습니다.');
		}
		else if (code === 'FRIDGE_INVALID_INPUT') {
			alertDanger('입력값이 유효하지 않습니다.');
		}
		else if (code === 'USER_INVALID_INPUT') {
			alertDanger('입력값이 유효하지 않습니다.');
		}
		else if (code === 'FRIDGE_UNAUTHORIZED_ACCESS') {
			alertDanger('로그인되지 않은 사용자입니다.');
		}
		else if (code === 'USER_NOT_FOUND') {
			alertDanger('해당 사용자를 찾을 수 없습니다.');
		}
		else if (code === 'INTERNAL_SERVER_ERROR') {
			alertDanger('서버 내부 오류가 발생했습니다.');
		}
		else {
			console.log(error);
		}
	}
}





/**
 * 사용자 냉장고 등록
 */
async function createUserFridge(userId, item, fridgeId) {
    try {
        const match = item.amount.match(/^(\d+)([a-zA-Z가-힣]*)$/);
        const amount = match ? match[1] : null;
        const unit = match ? match[2] : '';

        const userFridgeData = {
            amount: amount,
            unit: unit,
            expdate: item.expdate,
            fridge_id: fridgeId
        };
		

        const response = await instance.post(`/users/${userId}/fridges`, userFridgeData, {
            headers: getAuthHeaders()
        });
    } 
	catch(error) {
		const code = error?.response?.data?.code;
		
		if (code === 'USER_FRIDGE_CREATE_FAIL') {
			alertDanger('사용자 냉장고 작성에 실패했습니다.');
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
 * 특정 메모 삭제
 */
async function deleteMemo(memoId) {
    try {
        const token = localStorage.getItem('accessToken');
        const userId = parseJwt(token).id;

        const response = await instance.delete(`/users/${userId}/memos/${memoId}`, {
            headers: getAuthHeaders()
        });
		
		if (response.data.code === 'MEMO_DELETE_SUCCESS') {
		    return true;
		} else {
		    return false;
		}
    } catch (error) {
        const code = error?.response?.data?.code;

        if (code === 'MEMO_UNAUTHORIZED_ACCESS') {
            alertDanger('로그인되지 않은 사용자입니다.');
        }
        else if (code === 'MEMO_FORBIDDEN') {
            alertDanger('접근 권한이 없습니다.');
        }
        else if (code === 'MEMO_INVALID_INPUT') {
            alertDanger('잘못된 입력값입니다.');
        }
        else if (code === 'MEMO_NOT_FOUND') {
            alertDanger('삭제하려는 메모를 찾을 수 없습니다.');
        }
        else if (code === 'MEMO_DELETE_FAIL') {
            alertDanger('메모 삭제에 실패했습니다.');
        }
        else {
            console.error(error);
            alertDanger('알 수 없는 오류가 발생했습니다.');
        }
        return false;
    }
}
