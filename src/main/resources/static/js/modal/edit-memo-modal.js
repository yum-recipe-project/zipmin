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

        if (form.amount.value.trim() === '') {
            form.amount.classList.add('is-invalid');
            document.getElementById('sheetAmountHint1').style.display = 'block';
            form.amount.focus();
            isValid = false;
        } 


        if (!isValid) return false;
		
		
		// 재료 양/단위 분리
		const match = form.amount.value.trim().match(/^(\d+)([a-zA-Z가-힣]+)$/);
		if (form.amount.value.trim() === '') {
			form.amount.classList.add('is-invalid');
			form.amount.focus();
			isValid = false;
		}
		else if (!match) {
			form.amount.classList.add('is-invalid');
			form.amount.focus();
			isValid = false;
		}
		 
		if (isValid){
			try{
				const userId = parseJwt(localStorage.getItem('accessToken')).id;
				const data = {
					name: form.name.value.trim(),
				    amount: match[1],
				    unit: match[2],
				    note: form.note.value.trim(),
//					userId:userId
				}
				
				const response = await instance.patch(`/users/${userId}/memos/${memoId}`, data, {
					headers: getAuthHeaders()
				});
				
				if (response.data.code === 'MEMO_UPDATE_SUCCESS') {
					fetchUserMemoList();
					
					// 모달 닫기
	                const editMemoModalEl = document.getElementById('editMemoModal');
	                const modal = bootstrap.Modal.getInstance(editMemoModalEl);
	                if (modal) modal.hide();
				}
				
			}
			catch(error){
				console.log(error);
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

