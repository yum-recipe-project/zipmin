/**
 * 장보기 메모를 작성하는 함수
 */
document.addEventListener("DOMContentLoaded", function() {

    const form = document.getElementById('writeMemoForm');

    form.addEventListener('submit', async function(event) {
        event.preventDefault(); 

        let isValid = true;

		// 폼값 검증
        if (form.name.value.trim() === '') {
            form.name.classList.add('is-invalid');
            form.name.focus();
            isValid = false;
        }

		let amount = null;
		let unit = null;
		
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
		
		 
		if (isValid){
			try{
				const userId = parseJwt(localStorage.getItem('accessToken')).id;
				const data = {
					name: form.name.value.trim(),
				    amount: amount,
				    unit: unit,
				    note: form.note.value.trim(),
					userId: userId
				}
				
				const response = await instance.post(`/users/${userId}/memos`, data, {
					headers: getAuthHeaders()
				});
				
				if (response.data.code === 'MEMO_CREATE_SUCCESS') {
					fetchUserMemoList();
					
					// 모달 닫기
	                const writeMemoModalEl = document.getElementById('writeMemoModal');
	                const modal = bootstrap.Modal.getInstance(writeMemoModalEl);
	                if (modal) modal.hide();
				}
				
			} catch (error) {
                const code = error?.response?.data?.code;

                if (code === 'MEMO_UNAUTHORIZED_ACCESS') {
                    alertDanger('로그인되지 않은 사용자입니다.');
                }
                else if (code === 'MEMO_INVALID_INPUT') {
                    alertDanger('입력값이 유효하지 않습니다.');
                }
                else if (code === 'MEMO_CREATE_FAIL') {
                    alertDanger('장보기 메모 등록에 실패했습니다.');
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
    const writeMemoModalEl = document.getElementById('writeMemoModal');
    const writeMemoForm = document.getElementById('writeMemoForm');

    if (writeMemoModalEl) {
        writeMemoModalEl.addEventListener('hidden.bs.modal', function () {
            // 폼 리셋
            writeMemoForm.reset();

            // 유효성 에러 표시 제거
            writeMemoForm.querySelectorAll('.is-invalid').forEach(input => {
                input.classList.remove('is-invalid');
            });

            const hint = document.getElementById('sheetAmountHint1');
            if (hint) hint.style.display = 'none';
        });
    }
});

