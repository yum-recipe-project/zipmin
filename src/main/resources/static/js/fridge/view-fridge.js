/**
 * 
 */
document.addEventListener('DOMContentLoaded', function() {
    const swiper = new Swiper('.swiper', {
        direction: 'horizontal',
		
		slidesPerView: 'auto',
        spaceBetween: 12,
		
        scrollbar: {
            el: '.swiper-scrollbar',
            draggable: true, // 스크롤바 드래그 가능
        },
    });
});
