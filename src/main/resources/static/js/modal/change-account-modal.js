/**
 * 계좌를 변경하는 모달창을 여는 함수
 */
async function openChangeAccountModal() {
	
	// 사용자의 계좌정보 조회
	try {
        const id = parseJwt(localStorage.getItem('accessToken')).id;

        const response = await fetch(`/users/${id}/account`, {
            method: 'GET',
            headers: getAuthHeaders()
        });

        const result = await response.json();

        if (result.code === 'USER_READ_ACCOUNT_SUCCESS') {
			renderChangeAccount(result.data);

        } else if (result.code === 'USER_INVALID_INPUT') {
            alertDanger('입력값이 유효하지 않습니다.');
        } else if (result.code === 'USER_NOT_FOUND') {
            alertDanger('해당 사용자를 찾을 수 없습니다.');
        } else if (result.code === 'INTERNAL_SERVER_ERROR') {
            alertDanger('서버 내부에서 오류가 발생했습니다.');
        } else {
            console.log('알 수 없는 에러:', result);
        }
    } catch (error) {
        console.log(error);
        alertDanger('알 수 없는 오류가 발생했습니다.');
    }
	
}







/**
 * 계좌 정보를 바탕으로 모달을 렌더링하는 함수
 * @param {Object|null} account - 계좌 정보. 없으면 null
 */
function renderChangeAccount(account) {
    const modalBody = document.querySelector('#changeAccountModal .modal-body');
    modalBody.innerHTML = '';

    // 은행 리스트
	const banks = [
	    { code: 'ibk', name: '산업은행' },
	    { code: 'ibk_corp', name: '기업은행' },
	    { code: 'kb', name: '국민은행' },
	    { code: 'suhyup', name: '수협' },
	    { code: 'nh', name: '농협은행' },
	    { code: 'local_nhc', name: '지역농축협' },
	    { code: 'woori', name: '우리은행' },
	    { code: 'sc', name: 'SC은행' },
	    { code: 'citi', name: '시티은행' },
	    { code: 'daegu', name: '대구은행' },
	    { code: 'busan', name: '부산은행' },
	    { code: 'gwangju', name: '광주은행' },
	    { code: 'jeju', name: '제주은행' },
	    { code: 'jeonbuk', name: '전북은행' },
	    { code: 'gyeongnam', name: '경남은행' },
	    { code: 'saemaeul', name: '새마을금고' },
	    { code: 'shinhyup', name: '신협' },
	    { code: 'post', name: '우체국' },
	    { code: 'hana', name: '하나은행' },
	    { code: 'shinhan', name: '신한은행' },
	    { code: 'kbank', name: '케이뱅크' },
	    { code: 'kakao', name: '카카오뱅크' },
	    { code: 'toss', name: '토스뱅크' },
	    { code: 'kb_sec', name: 'KB증권' },
	    { code: 'mirae', name: '미래에셋증권' },
	    { code: 'samsung', name: '삼성증권' },
	    { code: 'korea', name: '한국투자' },
	    { code: 'nh_sec', name: 'NH투자증권' },
	    { code: 'hana_sec', name: '하나증권' },
	    { code: 'shinhan_sec', name: '신한투자증권' },
	    { code: 'meritz', name: '메리츠증권' }
	];


	// 계좌정보
    const accountSection = document.createElement('div');
    accountSection.className = 'account-section';

    const h5Account = document.createElement('h5');
    h5Account.textContent = '출금 계좌';
    accountSection.appendChild(h5Account);

    const formAccount = document.createElement('div');
    formAccount.className = 'form-account';

    if (account) {
        const maskedNumber = account.accountnum.replace(/^(\d{3})\d+(\d{1,2})$/, '$1*******$2');
        formAccount.innerHTML = `<p>${account.bank} ${maskedNumber}</p><p>예금주 ${account.name}</p>`;
    } else {
        formAccount.innerHTML = `<p>등록된 출금 계좌 정보가 없습니다</p>`;
    }

    accountSection.appendChild(formAccount);
    modalBody.appendChild(accountSection);

	// 계좌 등록/변경
    const h5Form = document.createElement('h5');
    h5Form.textContent = account ? '출금 계좌 변경' : '출금 계좌 등록';
    modalBody.appendChild(h5Form);

    const formGroupBank = document.createElement('div');
    formGroupBank.className = 'form-group';
    formGroupBank.innerHTML = '<label>은행</label>';
    const select = document.createElement('select');
    select.className = 'form-select';

    banks.forEach(bank => {
		const option = document.createElement('option');
	    option.value = bank.code;      
	    option.textContent = bank.name;
	    if (account && account.bank === bank.code) { 
	        option.selected = true; // 현재 계좌의 은행 선택
	    }
	    select.appendChild(option);
    });

    formGroupBank.appendChild(select);
    modalBody.appendChild(formGroupBank);

    const formGroupNumber = document.createElement('div');
    formGroupNumber.className = 'form-group';
    formGroupNumber.innerHTML = `
        <label>계좌번호</label>
        <input class="form-control" name="number" value="${account ? account.accountnum : ''}" type="number" step="1">
        <p>'-' 없이 숫자만 입력해 주세요. 선불전자지급수단은 사용할 수 없어요.</p>
    `;
    modalBody.appendChild(formGroupNumber);

    const formGroupName = document.createElement('div');
    formGroupName.className = 'form-group';
    formGroupName.innerHTML = `
        <label>예금주명</label>
        <input class="form-control" name="name" value="${account ? account.name : ''}">
        <p>예금주명은 회원의 실명과 일치해야 해요.</p>
    `;
    modalBody.appendChild(formGroupName);
}





















/**
 * 계좌를 변경하는 모달창에 데이터를 꽂는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
});



/**
 * 계좌를 변경하는 모달창의 폼값을 검증하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	const accountNumberInput = document.getElementById("accountNumberInput");
	const accountNameInput = document.getElementById("accountNameInput");
	const submitButton = document.querySelector("#changeAccountForm button[type='submit']");
	
	accountNumberInput.addEventListener("input", function() {
		submitButton.classList.toggle("disabled", !accountNumberInput.value || !accountNameInput.value);
	});
	accountNameInput.addEventListener("input", function() {
		submitButton.classList.toggle("disabled", !accountNumberInput.value || !accountNameInput.value);
	});
});