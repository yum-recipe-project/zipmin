/**
 * 포인트를 출금하는 모달창을 여는 함수
 */
function openWithdrawPointModal() {
	const ownedPoint = document.getElementById("ownedPoint");
	const remainPoint = document.getElementById("remainPoint");
	const pointInput = document.getElementById("pointInput");
	const submitButton = document.querySelector("#withdrawPointForm button[type='submit']");

	// 현재 보유 포인트 
	const userOwned = totalRevenue || 0;
	ownedPoint.innerText = userOwned.toLocaleString();

	pointInput.value = 1000; 

	// 남은 포인트 계산 
	const updateRemain = () => {
		const withdrawValue = parseInt(pointInput.value) || 0;
		const remain = userOwned - withdrawValue;

		if (withdrawValue < 1000 || withdrawValue > userOwned) {
			remainPoint.innerText = "-";
			submitButton.classList.add("disabled");
		} else {
			remainPoint.innerText = remain.toLocaleString();
			submitButton.classList.remove("disabled");
		}
	};

	// 모달 열 때 초기 계산
	updateRemain();
	pointInput.addEventListener("input", updateRemain);
	submitButton.disabled = submitButton.classList.contains("disabled");
}




/**
 * 출금 신청 함수
 */
async function submitWithdrawRequest() {
    const pointInput = document.getElementById("pointInput");
    const withdrawPoint = parseInt(pointInput.value);

    if (isNaN(withdrawPoint) || withdrawPoint < 1000) {
        alertDanger("출금 포인트는 1,000 이상이어야 합니다.");
        return;
    }

    try {
        const id = parseJwt(localStorage.getItem('accessToken')).id;

        const response = await fetch(`/users/${id}/withdraw`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                ...getAuthHeaders() 
            },
            body: JSON.stringify({
                point: withdrawPoint
            })
        });

        const result = await response.json();

        if (response.ok) {
            alertPrimary("출금 신청이 완료되었습니다.");
            // 모달 닫기
            const modal = bootstrap.Modal.getInstance(document.getElementById('withdrawPointModal'));
            modal.hide();

            // 보유 포인트 갱신
            totalRevenue -= withdrawPoint;
            document.getElementById("ownedPoint").innerText = totalRevenue.toLocaleString();
            document.getElementById("remainPoint").innerText = "-";
        } else {
            alertDanger(result.message || "출금 신청에 실패했습니다.");
        }

    } catch (error) {
        console.error(error);
        alertDanger("출금 요청 중 오류가 발생했습니다.");
    }
}

// 출금 버튼 함수
document.addEventListener("DOMContentLoaded", function() {
    const withdrawForm = document.getElementById("withdrawPointForm");
    if (withdrawForm) {
        withdrawForm.addEventListener("submit", function(event) {
            event.preventDefault();
            submitWithdrawRequest();
        });
    }
});
