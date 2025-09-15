/**
 * 키친가이드 입력값 검증 함수
 * @returns {boolean} - 유효하면 true, 아니면 false
 */
function validateEditGuideForm() {
    let isValid = true;

    const title = document.getElementById('editGuideTitleInput');
    const subtitle = document.getElementById('editGuideSubtitleInput');
    const category = document.getElementById('editGuideCategorySelect');
    const content = document.getElementById('editGuideContentInput');

    if (title.value.trim() === '') {
        title.classList.add('is-invalid');
        document.getElementById('editGuideTitleHint').style.display = 'block';
        title.focus();
        isValid = false;
    }

    if (subtitle.value.trim() === '') {
        subtitle.classList.add('is-invalid');
        document.getElementById('editGuideSubtitleHint').style.display = 'block';
        subtitle.focus();
        isValid = false;
    }

    if (category.value.trim() === '') {
        category.classList.add('is-invalid');
        document.getElementById('editGuideCategoryHint').style.display = 'block';
        category.focus();
        isValid = false;
    }

    if (content.value.trim() === '') {
        content.classList.add('is-invalid');
        document.getElementById('editGuideContentHint').style.display = 'block';
        content.focus();
        isValid = false;
    }

    return isValid;
}



/**
 * 가이드 수정 함수
 */
document.addEventListener('DOMContentLoaded', function() {
	
	const form = document.getElementById('editGuideForm');
	form.addEventListener('submit', async function(event) {
		event.preventDefault();
		
		// 입력값 검증
		if (!validateEditGuideForm()) return; 
		
		try {
	          const id = document.getElementById('editGuideId').value;
			  
			  const payload = {
			      id: parseInt(id),
			      title: document.getElementById('editGuideTitleInput').value.trim(),
			      subtitle: document.getElementById('editGuideSubtitleInput').value.trim(),
			      category: document.getElementById('editGuideCategorySelect').value.trim(),
			      content: document.getElementById('editGuideContentInput').value.trim()
			  };

			  const response = await instance.patch(`/guides/${id}`, payload, {
			      headers: {
			          ...getAuthHeaders(),
			          'Content-Type': 'application/json'
			      }
			  });

	          if (response.data.code === 'KITCHEN_UPDATE_SUCCESS') {
	              alertPrimary('키친가이드 수정에 성공했습니다.');
	              bootstrap.Modal.getInstance(document.getElementById('editGuideModal'))?.hide();
	              fetchGuideList();
	          }
	      } catch (error) {
	          const code = error?.response?.data?.code;

	          switch (code) {
	              case 'KITCHEN_UPDATE_FAIL':
	                  alertDanger('키친가이드 수정에 실패했습니다.');
	                  break;
	              case 'KITCHEN_INVALID_INPUT':
	                  alertDanger('입력값이 유효하지 않습니다.');
	                  break;
	              case 'KITCHEN_UNAUTHORIZED_ACCESS':
	                  alertDanger('로그인되지 않은 사용자입니다.');
	                  break;
	              case 'KITCHEN_FORBIDDEN':
	                  alertDanger('접근 권한이 없습니다.');
	                  break;
	              case 'KITCHEN_NOT_FOUND':
	                  alertDanger('해당 키친가이드를 찾을 수 없습니다.');
	                  break;
	              case 'USER_NOT_FOUND':
	                  alertDanger('해당 사용자를 찾을 수 없습니다.');
	                  break;
	              case 'INTERNAL_SERVER_ERROR':
	                  alertDanger('서버 내부 오류가 발생했습니다.');
	                  break;
	              default:
	                  console.log(error);
	          }
	      }
	  });
		
});