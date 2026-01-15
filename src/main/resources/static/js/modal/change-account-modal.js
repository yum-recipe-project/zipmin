/**
 * 접근 권한을 설정하는 함수
 */
document.addEventListener('DOMContentLoaded', async function() {

	try {
		await instance.get('/dummy');
	}
	catch (error) {
		redirectToLogin('/');
	}
	
	fetchAccount();
});





/**
 * 계좌 정보를 조회하는 함수
 */
async function fetchAccount() {
	
	try {
		const payload = parseJwt(localStorage.getItem('accessToken'));

		const response = await instance.get(`/users/${payload.id}/account`, {
			headers: getAuthHeaders()
		});
		
		if (response.data.code === 'USER_ACCOUNT_READ_SUCCESS') {
			renderEditAccount(response.data.data);
		}
	}
	catch (error) {
		const code = error?.response?.data?.code;
				
		if (code === 'USER_ACCOUNT_INVALID_INPUT') {
			console.log(error);
		}
		else if (code === 'USER_INVALID_INPUT') {
			console.log(error);
		}
		else if (code === 'USER_ACCOUNT_UNAUTHORIZED_ACCESS') {
			console.log(error);
		}
		else if (code === 'USER_ACCOUNT_FORBIDDEN') {
			alertDanger('접근 권한이 없습니다.');
		}
		else if (code === 'USER_ACCOUNT_NOT_FOUND') {
			renderEditAccount(null);
		}
		else if (code === 'USER_NOT_FOUND') {
			console.log(error);
		}
		else if (code === 'INTERNAL_SERVER_ERROR') {
			console.log(error);
		}
		else {
			console.log(error);
		}
	}
	
}





/**
 * 계좌 정보를 바탕으로 모달을 렌더링하는 함수
 * @param {Object|null} account - 계좌 정보. 없으면 null
 */
function renderEditAccount(account) {
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

    // 계좌정보 섹션
    const accountSection = document.createElement('div');
    accountSection.className = 'account-section';

    const h5Account = document.createElement('h5');
    h5Account.textContent = '출금 계좌';
    accountSection.appendChild(h5Account);

    const formAccount = document.createElement('div');
    formAccount.className = 'form-account';

    if (account) {
	    const bankName = banks.find(b => b.code === account.bank)?.name || account.bank;

	    const maskedNumber = account.accountnum.replace(/^(\d{3})\d+(\d{1,2})$/, '$1*******$2');
	    formAccount.innerHTML = `<p>${bankName} ${maskedNumber}</p><p>예금주 ${account.name}</p>`;
    } else {
        formAccount.innerHTML = `<p>등록된 출금 계좌 정보가 없습니다</p>`;
    }
	
    accountSection.appendChild(formAccount);
    modalBody.appendChild(accountSection);

    // 계좌 등록/변경 폼
    const h5Form = document.createElement('h5');
    h5Form.textContent = account ? '출금 계좌 변경' : '출금 계좌 등록';
    modalBody.appendChild(h5Form);

    // 은행 선택
    const formGroupBank = document.createElement('div');
    formGroupBank.className = 'form-group';
    formGroupBank.innerHTML = '<label>은행</label>';
    const select = document.createElement('select');
    select.className = 'form-select';

    banks.forEach(bank => {
        const option = document.createElement('option');
        option.value = bank.code;
        option.textContent = bank.name;
        if (account && account.bank === bank.code) option.selected = true;
        select.appendChild(option);
    });

    formGroupBank.appendChild(select);
    modalBody.appendChild(formGroupBank);

    // 계좌번호
    const formGroupNumber = document.createElement('div');
    formGroupNumber.className = 'form-group';
    formGroupNumber.innerHTML = `
        <label>계좌번호</label>
        <input id="accountNumberInput" class="form-control" name="number" value="${account ? account.accountnum : ''}" type="number" step="1">
        <p>'-' 없이 숫자만 입력해 주세요. 선불전자지급수단은 사용할 수 없어요.</p>
    `;
    modalBody.appendChild(formGroupNumber);

    // 예금주명
    const formGroupName = document.createElement('div');
    formGroupName.className = 'form-group';
    formGroupName.innerHTML = `
        <label>예금주명</label>
        <input id="accountNameInput" class="form-control" name="name" value="${account ? account.name : ''}">
        <p>예금주명은 회원의 실명과 일치해야 해요.</p>
    `;
    modalBody.appendChild(formGroupName);

    // 모달 footer 버튼
    const modalFooter = document.querySelector('#changeAccountModal .modal-footer');
    modalFooter.innerHTML = ''; // 기존 버튼 제거

    const closeButton = document.createElement('button');
    closeButton.type = 'button';
    closeButton.className = 'btn btn-danger';
    closeButton.dataset.bsDismiss = 'modal';
    closeButton.textContent = '닫기';

    const submitButton = document.createElement('button');
    submitButton.type = 'submit';
    submitButton.className = 'btn btn-primary';
    submitButton.textContent = account ? '변경하기' : '등록하기';
	submitButton.className = 'btn btn-primary disabled'; 

    modalFooter.append(closeButton, submitButton);
	
	// 등록/변경 버튼 렌더링
	renderFooterButton(!!account);
}





/**
 * 모달 footer 버튼 렌더링 및 이벤트 처리
 * @param {boolean} hasAccount - 기존 계좌 정보가 있는지 여부
 */
function renderFooterButton(hasAccount) {
    const modalFooter = document.querySelector('#changeAccountModal .modal-footer');
    modalFooter.innerHTML = '';

    // 닫기 버튼
    const closeButton = document.createElement('button');
    closeButton.type = 'button';
    closeButton.className = 'btn btn-danger';
    closeButton.dataset.bsDismiss = 'modal';
    closeButton.textContent = '닫기';

    // 등록/변경 버튼
    const submitButton = document.createElement('button');
    submitButton.type = 'submit';
    submitButton.className = 'btn btn-primary disabled';
    submitButton.textContent = hasAccount ? '변경하기' : '등록하기';

    modalFooter.append(closeButton, submitButton);

    // 버튼 활성화/비활성화 및 클릭 이벤트
    const modalBody = document.querySelector('#changeAccountModal .modal-body');

    modalBody.addEventListener('input', function() {
        const numberVal = document.getElementById("accountNumberInput")?.value || '';
        const nameVal = document.getElementById("accountNameInput")?.value || '';
        submitButton.classList.toggle('disabled', !numberVal || !nameVal);
    });

	submitButton.addEventListener('click', function(e) {
	    e.preventDefault();

	    if (submitButton.textContent === '등록하기') {
	        writeAccount();
	    }
		else if (submitButton.textContent === '변경하기') {
	        editAccount();
	    }
	});

}





/**
 * 계좌를 작성하는 함수
 */
async function writeAccount() {

	try {
		const payload = parseJwt(localStorage.getItem('accessToken'));
		
		const data = {
			bank: document.querySelector('#changeAccountModal .form-select').value,
			accountnum: document.getElementById('accountNumberInput').value,
			name: document.getElementById('accountNameInput').value,
		};

		const response = await instance.post(`/users/${payload.id}/account`, data, {
			headers: getAuthHeaders()
		});
		
		console.log(response);

		if (response.data.code === 'USER_ACCOUNT_CREATE_SUCCESS') {
			alertPrimary('출금 계좌가 등록되었습니다.');
			bootstrap.Modal.getInstance(document.getElementById('changeAccountModal')).hide();
			// renderEditAccount(response.data.data);
			fetchAccount();
		}
	} catch (error) {
		const code = error?.response?.data?.code;

		if (code === 'USER_ACCOUNT_CREATE_FAIL') {
			console.log(error);
		}
		else if (code === 'USER_ACCOUNT_INVALID_INPUT') {
			console.log(error);
		}
		else if (code === 'USER_INVALID_INPUT') {
			console.log(error);
		}
		else if (code === 'USER_ACCOUNT_UNAUTHORIZED_ACCESS') {
			console.log(error);
		}
		else if (code === 'USER_NOT_FOUND') {
			console.log(error);
		}
		else if (code === 'INTERNAL_SERVER_ERROR') {
			console.log(error);
		}
		else {
			console.log(error);
		}
	}
}





/**
 * 계좌를 수정하는 함수
 */
async function editAccount() {
	
	try {
		const payload = parseJwt(localStorage.getItem('accessToken'));
		
		const data = {
			bank: document.querySelector('#changeAccountModal .form-select').value,
			accountnum: document.getElementById('accountNumberInput').value,
			name: document.getElementById('accountNameInput').value
		};
		
		const response = await instance.patch(`/users/${payload.id}/account`, data, {
			headers: getAuthHeaders()
		});

		if (response.data.code === 'USER_ACCOUNT_UPDATE_SUCCESS') {
			alertPrimary('출금 계좌가 변경되었습니다.');
			bootstrap.Modal.getInstance(document.getElementById('changeAccountModal')).hide();
			//renderEditAccount(response.data.data);
			fetchAccount();
		}
	} catch (error) {
		const code = error?.response?.data?.code;
		
		if (code === 'USER_ACCOUNT_UPDATE_FAIL') {
			console.log(error);
		}
		else if (code === 'USER_ACCOUNT_INVALID_INPUT') {
			console.log(error);
		}
		else if (code === 'USER_INVALID_INPUT') {
			console.log(error);
		}
		else if (code === 'USER_ACCOUNT_UNAUTHORIZED_ACCESS') {
			console.log(error);
		}
		else if (code === 'USER_ACCOUNT_FORBIDDEN') {
			alertDanger('접근 권한이 없습니다.');
		}
		else if (code === 'USER_ACCOUNT_NOT_FOUND') {
			console.log(error);
		}
		else if (code === 'USER_NOT_FOUND') {
			console.log(error);
		}
		else if (code === 'INTERNAL_SERVER_ERROR') {
			console.log(error);
		}
		else {
			console.log(error);
		}
	}
}

