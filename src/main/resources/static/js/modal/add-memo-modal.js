/**
 * 장보기메모에 재로 담기 모달창의 재료를 선택했는지 검증하는 함수
 */
document.addEventListener("DOMContentLoaded", function() {
    const memoElement = document.getElementById('addMemoModal');
    const submitButton = document.querySelector("#addMemoForm button[type='submit']");

    memoElement.addEventListener("change", function(event) {
		if (event.target.matches('input[name="ingredient"]')) {
			const isChecked = document.querySelector('input[name=ingredient]:checked');
			submitButton.classList.toggle("disabled", !isChecked);
		}
	});
});










/**
 * 장보기메모에 재료 담기
 */
document.addEventListener("DOMContentLoaded", function() {
    const addMemoForm = document.getElementById("addMemoForm");

    addMemoForm.addEventListener("submit", async function(event) {
        event.preventDefault();

        // 체크된 재료만 가져오기
        const selectedCheckboxes = document.querySelectorAll('input[name="ingredient"]:checked');

        if (selectedCheckboxes.length === 0) {
            alertPrimary("하나 이상의 재료를 선택해주세요.");
            return;
        }

        const memoList = Array.from(selectedCheckboxes).map(cb => {
            const tr = cb.closest("tr");
            return {
                name: tr.children[0].textContent,
                amount: tr.children[1].dataset.amount || tr.children[1].textContent.replace(/[^\d]/g, ''),
                unit: tr.children[1].dataset.unit || '',
                note: tr.children[2].textContent || ''
            };
        });

        const data = { memoList };
		
        try {
            const userId = parseJwt(localStorage.getItem('accessToken')).id;

            const response = await instance.post(`/users/${userId}/memos/recipe-stock`, data, {
                headers: getAuthHeaders(),
            });

            if (response.data.code === 'MEMO_CREATE_SUCCESS') {
                alertPrimary("선택한 재료가 장보기 메모에 담겼습니다");

                // 모달 닫기
                const modalEl = document.getElementById('addMemoModal');
                const modal = bootstrap.Modal.getInstance(modalEl);
                if (modal) modal.hide();

            } else {
                alertDanger(response.data.message || "재료 담기에 실패했습니다.");
            }

        } catch (error) {
            console.error(error);
            alertDanger("서버 오류로 재료 담기에 실패했습니다.");
        }
    });
});




