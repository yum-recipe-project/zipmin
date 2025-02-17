/**
 * 
 */

document.addEventListener("DOMContentLoaded", function () {
	
	// 레시피 찜 
	document.querySelectorAll(".favorite_btn").forEach(button => {
	    button.addEventListener("click", function() {
	        this.classList.toggle("active");
	    });
	});

});