/**
 * 키친가이드 글쓰기
 */
document.addEventListener('DOMContentLoaded', function() {

    const form = document.getElementById('writeGuideForm'); // form id
    form.addEventListener('submit', async function(event) {
        event.preventDefault();
        let isValid = true;

        // 입력값 검증
        const title = document.getElementById('title');
        const subtitle = document.getElementById('subtitle');
        const category = document.getElementById('category');
        const content = document.getElementById('content');

        if (title.value.trim() === '') {
            title.classList.add('is-invalid');
            document.getElementById('titleHint').style.display = 'block';
            title.focus();
            isValid = false;
        }

        if (subtitle.value.trim() === '') {
            subtitle.classList.add('is-invalid');
            document.getElementById('subtitleHint').style.display = 'block';
            subtitle.focus();
            isValid = false;
        }

        if (category.value.trim() === '') {
            category.classList.add('is-invalid');
            document.getElementById('categoryHint').style.display = 'block';
            category.focus();
            isValid = false;
        }

        if (content.value.trim() === '') {
            content.classList.add('is-invalid');
            document.getElementById('contentHint').style.display = 'block';
            content.focus();
            isValid = false;
        }

        if (!isValid) return;

        // 로그인 여부 확인
        if (!isLoggedIn()) {
            redirectToLogin();
            return;
        }

        try {
            // 요청 DTO 구성
            const guideRequestDto = {
                title: title.value.trim(),
                subtitle: subtitle.value.trim(),
                category: category.value.trim(),
                content: content.value.trim()
            };

            // API 요청
            const response = await instance.post('/guides', guideRequestDto, {
                headers: getAuthHeaders()
            });

            if (response.data.code === 'KITCHEN_CREATE_SUCCESS') {
                alert('키친가이드 작성이 완료되었습니다.');
                location.href = '/kitchen/listGuide.do';
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
