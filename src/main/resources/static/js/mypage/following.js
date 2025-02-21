/**
 * 
 */

document.addEventListener("DOMContentLoaded", function () {
	
	// 팔로우 버튼 
	document.querySelectorAll('.followButton').forEach(function(button) {
	    button.addEventListener('click', function() {
	        const buttonText = this.querySelector('span');
	        
	        if (buttonText.textContent === '팔로잉') {
	            buttonText.textContent = '팔로우';
	            this.classList.remove('btn_outline_small');
	            this.classList.add('btn_primary');
	        } else {
	            buttonText.textContent = '팔로잉';
	            this.classList.remove('btn_primary');
	            this.classList.add('btn_outline_small');
	        }
	    });
	});


	
	
});