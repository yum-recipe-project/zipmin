document.addEventListener("DOMContentLoaded", function () {
  // #header.on
  $('#header .gnb>li>a').on('mouseenter', function() {
    $('#header').addClass('on');
  });
  $('#header').on('mouseleave', function() {
    $('#header').removeClass('on');
  });
  
  // 메인 페이지의 스와이퍼
  if (document.querySelector('.tag .swiper')) {
	  var swiperTag = new Swiper('.tag .swiper', {
	    loop: true,
	    slidesPerView: 6,  
	    spaceBetween: 20,
	    centeredSlides: false,
	  });
  }
  
  // 병원 상세페이지의 스와이퍼
  if (document.querySelector('.doctor .swiper')) {	
	  var swiperDoctor = new Swiper('.doctor .swiper', {
	    loop: false,
	    slidesPerView: 3,  
	    spaceBetween: 20,
	    centeredSlides: false,
	  });
  }
  if (document.querySelector('.time .swiper')) {	
	  var swiperTime = new Swiper('.time .swiper', {
		loop: false,
		slidesPerView: 4,  
	    spaceBetween: 20,
	    centeredSlides: false,
	  });
  }
});