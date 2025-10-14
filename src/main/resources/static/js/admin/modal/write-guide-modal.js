/**
 * 키친가이드 글쓰기
 */
document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('writeGuideForm');
	
    form.addEventListener('submit', async function(event) {
        event.preventDefault();
		
		// 폼값 검증
        if (!validateGuideForm()) {
            return;
        }
				
        // 로그인 여부 확인
        if (!isLoggedIn()) {
            redirectToLogin();
            return;
        }

        try {
            // 요청 DTO
            const guideRequestDto = {
				title: document.getElementById('title').value.trim(),
                subtitle: document.getElementById('subtitle').value.trim(),
                category: document.getElementById('category').value.trim(),
                content: document.getElementById('content').value.trim()
            };

            // API 요청
            const response = await instance.post('/guides', guideRequestDto, {
                headers: getAuthHeaders()
            });

            if (response.data.code === 'KITCHEN_CREATE_SUCCESS') {
				alertPrimary('키친가이드를 성공적으로 작성했습니다.');
				bootstrap.Modal.getInstance(document.getElementById('writeGuideModal'))?.hide();
                fetchGuideList(false); 
            }
        } catch (error) {
            const code = error?.response?.data?.code;

            if (code === 'KITCHEN_CREATE_FAIL') {
                alertDanger('가이드 작성에 실패했습니다.');
            } else if (code === 'KITCHEN_INVALID_INPUT') {
                alertDanger('입력값이 유효하지 않습니다.');
            } else if (code === 'KITCHEN_UNAUTHORIZED_ACCESS') {
                alertDanger('로그인되지 않은 사용자입니다.');
            } else if (code === 'KITCHEN_FORBIDDEN') {
                alertDanger('작성 권한이 없습니다.');
            } else if (code === 'INTERNAL_SERVER_ERROR') {
                alertDanger('서버 내부 오류가 발생했습니다.');
            } else {
                console.log(error);
            }
        }
    });
});


/**
 * 키친가이드 작성 폼값 검증 함수
 */
function validateGuideForm() {
    const title = document.getElementById('title');
    const subtitle = document.getElementById('subtitle');
    const category = document.getElementById('category');
    const content = document.getElementById('content');

    if (subtitle.value.trim() === '') {
        alertDanger("소제목을 입력해주세요.");
        subtitle.focus();
        return false;
    }

    if (title.value.trim() === '') {
        alertDanger("제목을 입력해주세요.");
        title.focus();
        return false;
    }

    if (category.value.trim() === '') {
        alertDanger("카테고리를 선택해주세요.");
        category.focus();
        return false;
    }

    if (content.value.trim() === '') {
        alertDanger("본문 내용을 입력해주세요.");
        content.focus();
        return false;
    }

    return true;
}



/**
 * 모달이 닫히면 폼을 초기화 하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	const modal = document.getElementById('writeGuideModal');
	modal.addEventListener('hidden.bs.modal', function() {
		
		const form = document.getElementById('writeGuideForm');

		if (form) {
			form.reset();
		}
		
		modal.querySelectorAll('.is-invalid').forEach(el => 
			{el.classList.remove('is-invalid')
		});
		modal.querySelectorAll('p[id*="Hint"]').forEach(p => {
			p.style.display = 'none'
		});

	});
	
});



