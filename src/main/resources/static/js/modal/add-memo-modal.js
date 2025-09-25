/**
 * 장보기메모에 재로 담기 모달창의 재료를 선택했는지 검증하는 함수
 */
//document.addEventListener("DOMContentLoaded", function() {
//    const memoElement = document.getElementById("memo");
//    const submitButton = document.querySelector("#addMemoForm button[type='submit']");
//
//    memoElement.addEventListener("change", function(event) {
//		if (event.target.matches('input[name="ingredient"]')) {
//			const isChecked = document.querySelector('input[name=ingredient]:checked');
//			submitButton.classList.toggle("disabled", !isChecked);
//		}
//	});
//});




//

document.addEventListener("DOMContentLoaded", function() {

    const form = document.getElementById('addMemoForm');

    form.addEventListener('submit', async function(event) {
        event.preventDefault(); 

        let isValid = true;

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
		 

        console.log("재료명:", form.name.value.trim());
        console.log("amount:", match[1]);
        console.log("unit:", match[2]);
        console.log("비고:", form.note.value.trim());
		
		
		
		if (isValid){
			try{
				const id = parseJwt(localStorage.getItem('accessToken')).id;
				const data = {
					name: form.name.value.trim(),
				    amount: match[1],
				    unit: match[2],
				    note: form.note.value.trim(),
					id:id
				}
				
				const response = await instance.post(`/users/${id}/memos`, data, {
					headers: getAuthHeaders()
				});
				
				if (response.data.code === 'MEMO_CREATE_SUCCESS') {
					console.log("입력성공");
					fetchUserMemoList();
					
					// 모달 닫기
	                const addMemoModalEl = document.getElementById('addMemoModal');
	                const modal = bootstrap.Modal.getInstance(addMemoModalEl);
	                if (modal) modal.hide();
					// 모달 내용 초기화
					form.reset();
				}
				
			}
			catch(error){
				console.log(error);
			}
		}
		
		
		
    });
});




