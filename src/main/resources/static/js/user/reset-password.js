/**
 * 
 */
/*
function getQueryParam(name){
  const url = new URL(location.href);
  return url.searchParams.get(name);
}
document.getElementById('resetPasswordForm').addEventListener('submit', async (e) => {
  e.preventDefault();
  const token = getQueryParam('token');
  const newPassword = e.target.newPassword.value.trim();
  try {
    const res = await fetch('/auth/password-reset', {
      method: 'POST',
      headers: {'Content-Type':'application/json'},
      body: JSON.stringify({ token, newPassword })
    });
    const json = await res.json();
    if (json.code === 'PASSWORD_RESET_SUCCESS') {
      alert('비밀번호가 변경되었습니다. 다시 로그인해 주세요.');
      location.href = '/user/login.do';
    } else {
      document.getElementById('resetHint').style.display = 'block';
    }
  } catch (err) {
    document.getElementById('resetHint').style.display = 'block';
  }
});
*/




document.addEventListener('DOMContentLoaded', function() {
	
	const form = document.getElementById('resetPasswordForm');
	form.addEventListener('submit', async function(event) {
		event.preventDefault();
		
		try {
			const token = new URLSearchParams(window.location.search).get('key');
			
			const data = {
				password: '12345'
			}
			
			const response = await fetch(`/users/password`, {
				method: 'PATCH',
				headers: {
					'Content-Type': 'application/json',
					'Authorization': `Bearer ${token}`
				},
				body: JSON.stringify(data)
			});
			
			console.log(response);
		}
		catch (error) {
			console.log(error);
		}
		
	});
	
});