/**
 * 메뉴 항목 클릭 시 활성화 상태를 토글하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
    const menuItems = document.querySelectorAll('.gnb ul li');
    const logo = document.querySelector('.logo');

    // 페이지 로드 시, 로컬 스토리지에서 마지막 활성화된 항목 가져오기
    const activeIndex = localStorage.getItem('activeMenuIndex');
    if (activeIndex !== null) {
        menuItems[activeIndex].classList.add('active');
    }
	
    // 메뉴 항목 클릭 시 이벤트 리스너 추가
    menuItems.forEach((item, index) => {
        item.addEventListener('click', function() {
            document.querySelectorAll('.gnb li').forEach(li => li.classList.remove('active'));
            this.classList.add('active');
            localStorage.setItem('activeMenuIndex', index);
        });
    });
	
    // 로고 클릭 시 'active' 클래스 제거 및 로컬 스토리지 초기화
    if (logo) {
        logo.addEventListener('click', function() {
            document.querySelectorAll('.gnb li').forEach(li => li.classList.remove('active'));
            localStorage.removeItem('activeMenuIndex');
        });
    }
});