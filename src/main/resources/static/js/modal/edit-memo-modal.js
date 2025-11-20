/**
 * 장보기 메모를 수정하는 함수
 */
document.addEventListener("DOMContentLoaded", function() {

    const form = document.getElementById('editMemoForm');

    form.addEventListener('submit', async function(event) {
        event.preventDefault(); 

        let isValid = true;
        let memoId = form.dataset.memoId;

		// 폼값 검증
        if (form.name.value.trim() === '') {
            form.name.classList.add('is-invalid');
            form.name.focus();
            isValid = false;
        }

		let amount = null;
		let unit = null;
		
		console.warn('amount값: '+ (form.amount.value.trim() !== ''));
		
		if(form.amount.value.trim() === ''){
			amount = 0;
		}
		if (form.amount.value.trim() !== '') {
		    const match = form.amount.value.trim().match(/^(\d+)([a-zA-Z가-힣]+)$/);
		
		    if (match) {
		        amount = match[1];
		        unit = match[2];
		    } else {
				alertDanger('양/단위 형식으로 작성해주세요. (ex: 300g)');
				form.amount.focus();
				isValid = false;
		    }
		}
        if (!isValid) return false;
//		
//		// 재료 양/단위 분리
//		const match = form.amount.value.trim().match(/^(\d+)([a-zA-Z가-힣]+)$/);
//		if (form.amount.value.trim() === '') {
//			form.amount.classList.add('is-invalid');
//			form.amount.focus();
//			isValid = false;
//		}
//		else if (!match) {
//			form.amount.classList.add('is-invalid');
//			form.amount.focus();
//			isValid = false;
//		}
		 
		if (isValid){
			try{
				const userId = parseJwt(localStorage.getItem('accessToken')).id;
				const data = {
					name: form.name.value.trim(),
				    amount: amount,
				    unit: unit,
				    note: form.note.value.trim(),
				}
				
				const response = await instance.patch(`/users/${userId}/memos/${memoId}`, data, {
					headers: getAuthHeaders()
				});
				
				if (response.data.code === 'MEMO_UPDATE_SUCCESS') {
					fetchUserMemoList();
					alertPrimary('장보기 메모가 수정되었습니다.');
					
					// 모달 닫기
	                const editMemoModalEl = document.getElementById('editMemoModal');
	                const modal = bootstrap.Modal.getInstance(editMemoModalEl);
	                if (modal) modal.hide();
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
                    alertDanger('입력값이 유효하지 않습니다.');
                }
                else if (code === 'MEMO_NOT_FOUND') {
                    alertDanger('수정하려는 메모를 찾을 수 없습니다.');
                }
                else if (code === 'MEMO_UPDATE_FAIL') {
                    alertDanger('장보기 메모 수정에 실패했습니다.');
                }
                else {
                    console.error(error);
                    alertDanger('알 수 없는 오류가 발생했습니다.');
                }
            }
        }
    });
});





/**
 * 모달이 닫히면 폼을 초기화하는 함수
 */
document.addEventListener("DOMContentLoaded", function() {
    const editMemoModalEl = document.getElementById('editMemoModal');
    const editMemoForm = document.getElementById('editMemoForm');

    if (editMemoModalEl) {
        editMemoModalEl.addEventListener('hidden.bs.modal', function () {
            // 폼 리셋
            editMemoForm.reset();

            // 유효성 에러 표시 제거
            editMemoForm.querySelectorAll('.is-invalid').forEach(input => {
                input.classList.remove('is-invalid');
            });

            const hint = document.getElementById('sheetAmountHint1');
            if (hint) hint.style.display = 'none';
        });
    }
});