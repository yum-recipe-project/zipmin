/**
 * 포인트를 충전하는 모달창에 데이터를 꽂는 함수
 */
document.addEventListener("DOMContentLoaded", function() {
	const ownedPoint = document.getElementById("ownedPoint");
	const totalPoint = document.getElementById("totalPoint");
	const pointRadio = document.getElementsByName("point");
	
	const topUpModal = document.getElementById("topUpPointModal");
	topUpModal.addEventListener("show.bs.modal", async function() {
		await fetchPoint();  
	});

	pointRadio.forEach(radio => {
		radio.addEventListener("change", function() {
			totalPoint.innerText = parseInt(ownedPoint.innerText) + parseInt(radio.value) + "P";
		});
	});
});



/**
 * 포인트를 충전하는 모달창의 충전할 포인트를 선택했는지 검증하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const pointRadio = document.querySelectorAll("input[name='point']");
	const submitButton = document.querySelector("#topUpPointForm button[type='submit']");
	
	pointRadio.forEach(function(radio) {
        radio.addEventListener("change", function() {
			const isChecked = Array.from(pointRadio).some(radio => radio.checked);
			submitButton.classList.toggle("disabled", !isChecked);
        });
    });
});






















/**
 * 사용자 포인트를 가져오는 함수
 */
async function fetchPoint() {
    
    const ownedPoint = document.getElementById('ownedPoint');
    const totalPoint = document.getElementById('totalPoint');
    
    try {
        const id = parseJwt(localStorage.getItem('accessToken')).id;
        
        const response = await fetch(`/users/${id}/point`, {
            method: 'GET',
            headers: getAuthHeaders()  
        });
        
        const result = await response.json();
        console.log("사용자 포인트:" + result.data.point);
        
        if (result.code === 'USER_READ_SUCCESS') {
            ownedPoint.innerText = result.data.point;
            totalPoint.innerText = result.data.point + "P";
        }
        else if (result.code === 'USER_INVALID_INPUT') {
            alertDanger('입력값이 유효하지 않습니다.');
        }
        else if (result.code === 'USER_NOT_FOUND') {
            alertDanger('해당 사용자를 찾을 수 없습니다.');
        }
        else if (result.code === 'INTERNAL_SERVER_ERROR') {
            alertDanger('서버 내부에서 오류가 발생했습니다.');
        }
        else {
            console.log('알 수 없는 에러:', result);
        }
    }
    catch(error) {
        console.log(error);
        alertDanger('알 수 없는 오류가 발생했습니다.');
    }
}


/**
 * 사용자 정보를 가져오는 함수
 */
async function fetchBuyer() {
    
    try {
        const id = parseJwt(localStorage.getItem('accessToken')).id;
        
        const response = await fetch(`/users/${id}`, {
            method: 'GET',
            headers: getAuthHeaders()  
        });
        
        const result = await response.json();
		
        if (result.code === 'USER_READ_SUCCESS') {
			console.log("사용자 조회 성공");
		    return result.data;
        }
        else if (result.code === 'USER_INVALID_INPUT') {
            alertDanger('입력값이 유효하지 않습니다.');
        }
        else if (result.code === 'USER_NOT_FOUND') {
            alertDanger('해당 사용자를 찾을 수 없습니다.');
        }
        else if (result.code === 'INTERNAL_SERVER_ERROR') {
            alertDanger('서버 내부에서 오류가 발생했습니다.');
        }
        else {
            console.log('알 수 없는 에러:', result);
        }
    }
    catch(error) {
        console.log(error);
        alertDanger('알 수 없는 오류가 발생했습니다.');
    }
}














/**
 * 포인트 결제 요청 함수
 */
document.addEventListener("DOMContentLoaded", function() {
    const topUpForm = document.getElementById("topUpPointForm");

	
    topUpForm.addEventListener("submit", async function(e) {
        e.preventDefault(); // 폼 제출 막기

        const ownedPoint = parseInt(document.getElementById("ownedPoint").innerText);
        const selectedRadio = document.querySelector("input[name='point']:checked");

        if (!selectedRadio) {
            alert("충전할 포인트를 선택해주세요.");
            return;
        }

        const amount = parseInt(selectedRadio.value);
        const merchantUid = `test_${Date.now()}_${Math.floor(Math.random() * 10000)}`
		
		const user = await fetchBuyer();


        // Iamport SDK 초기화
        var IMP = window.IMP;
        IMP.init("imp37643725"); 
		
		
        IMP.request_pay({
		 	channelKey:'channel-key-be3c520f-7105-474b-b9cd-8aa82a73f4a6',
            pg: 'kcp.INIpayTest',
            pay_method: 'card', 
			merchant_uid: merchantUid,
            name: '포인트 충전',
            amount: amount,
            buyer_email: user.email,
            buyer_name: user.username,           
            buyer_tel: user.tel
        }, async function(response) {
            if (response.success) {
				PostPoint(response);
				
            } else {
                console.log("결제 실패:", response.error_msg);
                alert("결제에 실패했습니다: " + response.error_msg);
            }
        });
    });
});





/**
 * 사용자 포인트를 충전하는 함수
 */
async function PostPoint(pointInfo) {
	
    try {
        const id = parseJwt(localStorage.getItem('accessToken')).id;
		
		const data = {
			user_id: id,
			imp_uid:pointInfo.imp_uid,
			amount:pointInfo.paid_amount,
			merchant_uid: pointInfo.merchant_uid,
			buyer_tel: pointInfo.buyer_tel,
			buyer_name: pointInfo.buyer_name
		};
        
		const response = await instance.post(`/users/${id}/point`, data, {
			headers: getAuthHeaders(),
		});
        
       const result = response.data;
	   
		if (result.code === 'PAYMENT_COMPLETE_SUCCESS') {
			alertPrimary('포인트가 충전되었습니다');
		    const modal = document.getElementById('topUpPointModal');
		    if (modal) {
		        const modalInstance = bootstrap.Modal.getInstance(modal);
		        if (modalInstance) {
		            modalInstance.hide();
		        }
		    }
		}
		
    }
    catch(error) {
        console.log(error);
        alertDanger('알 수 없는 오류가 발생했습니다.');
    }
}

