document.addEventListener("DOMContentLoaded", function() {
    const ownedPoint = document.getElementById("ownedPoint");
    const remainPoint = document.getElementById("remainPoint");
    const pointInput = document.getElementById("pointInput");
    const supportButton = document.querySelector("#supportRecipeForm button[type='submit']");

    // 초기에는 후원 버튼 비활성화
    supportButton.disabled = true;

    
	/**
	 * 사용자 보유 포인트 조회
	 */
    async function loadUserPoint() {
        try {
            const id = parseJwt(localStorage.getItem('accessToken')).id;

            const response = await fetch(`/users/${id}/point`, {
                method: 'GET',
                headers: getAuthHeaders()
            });

            const result = await response.json();

            if (result.code === 'USER_READ_SUCCESS') {
                ownedPoint.innerText = result.data.point;
                validatePointInput(); // 포인트 가져온 후 바로 입력값 체크
            } else {
                console.error('포인트 조회 오류:', result);
            }
        } catch (error) {
            console.error(error);
        }
    }


	/**
	 * 후원 포인트 입력 제한 
	 */
    function validatePointInput() {
        let value = pointInput.value.trim();

		// 정수 체크
        if (!/^\d+$/.test(value)) {
            supportButton.disabled = true;
            remainPoint.innerText = "-";
            return;
        }

        value = parseInt(value);
        const maxPoint = parseInt(ownedPoint.innerText);

		// 최소 100포인트, 보유포인트 초과 여부
        if (value < 100 || value > maxPoint) {
            supportButton.disabled = true;
        } else {
            supportButton.disabled = false;
        }

        // 남은 포인트 계산
        remainPoint.innerText = maxPoint - value;
    }

    pointInput.addEventListener("input", validatePointInput);

    // 모달 열릴 때 사용자 포인트 가져오기
    const supportModal = document.getElementById("supportRecipeModal");
    supportModal.addEventListener("show.bs.modal", loadUserPoint);
});



/**
 * 후원하기 버튼 동작 함수
 */
document.addEventListener("DOMContentLoaded", function() {
    const supportButton = document.querySelector("#supportRecipeForm button[type='submit']");
    const pointInput = document.getElementById("pointInput");
	
    supportButton.addEventListener("click", async function(event) {
        event.preventDefault(); 

        const point = parseInt(pointInput.value);
		const funderId = parseJwt(localStorage.getItem('accessToken')).id;
		const recipeId = parseInt(new URLSearchParams(window.location.search).get('id'));
		const fundeeId = parseInt(document.getElementById('fundeeIdInput').value);
		
		// 후원하기 
		try {
            const data = {
                recipe_id: recipeId,
                point: point,
            };

            const response = await instance.post(`/funds/${funderId}/supports/${fundeeId}`, data, {
                headers: getAuthHeaders(),
				'Content-Type': 'application/json'
            });

            if (response.data.code === 'FUND_COMPLETE_SUCCESS') {
				alertPrimary('후원이 완료되었습니다');
                const modalInstance = bootstrap.Modal.getInstance(supportRecipeModal);
                if (modalInstance) {
                    modalInstance.hide();
                }
            }
        } catch (error) {
            console.error(error);
        }


		
		
    });
});
