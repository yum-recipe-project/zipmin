<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>

<head>
	<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>집밥의민족</title>
		<link rel="shortcut icon" type="image/png" href="../assets/images/logos/favicon.png" />
		<link rel="stylesheet" href="../assets/css/styles.min.css" />
		<link rel="stylesheet" href="/css/admin/login.css">
		<script src="/js/admin/login.js"></script>
	</head>

	<body>
		<!--  Body Wrapper -->
		<div class="page-wrapper" id="main-wrapper" data-layout="vertical" data-navbarbg="skin6" data-sidebartype="full" data-sidebar-position="fixed" data-header-position="fixed">
			<div class="position-relative overflow-hidden radial-gradient min-vh-100 d-flex align-items-center justify-content-center">
				<div class="d-flex align-items-center justify-content-center w-100">
					<div class="row justify-content-center w-100">
						<div class="col-md-8 col-lg-6 col-xxl-3">
							<div class="card mb-0">
								<div class="card-body">
									<div class="title">
										<h5>집밥의민족</h5>
										<h2>관리자로그인</h2>
									</div>
									
									<form id="loginForm">
										<div class="mb-3 username_field">
											<label for="usernameInput" class="form-label">아이디</label>
											<input type="text" class="form-control" id="usernameInput" name="username" aria-describedby="emailHelp">
											<p>아이디를 입력해주세요.</p>
										</div>
										<div class="mb-4 password_field">
											<label for="passwordInput" class="form-label">비밀번호</label>
											<input type="password" class="form-control" id="passwordInput" name="password">
											<p>비밀번호를 입력해주세요.</p>
										</div>
										
										<div class="d-flex align-items-center justify-content-between mb-4">
											<div class="form-check">
												<input class="form-check-input primary" type="checkbox" value="" id="save-id">
												<label class="form-check-label text-dark" for="save-id">아이디 저장</label>
											</div>
										</div>
										
										<button type="submit" class="btn btn-primary w-100 py-8 fs-4 mb-4 rounded-2">로그인</button>
										<div class="d-flex align-items-center justify-content-center">
											<a class="text-primary fw-bold ms-2" href="/">홈페이지 바로가기</a>
										</div>
									</form>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<script src="/assets/libs/jquery/dist/jquery.min.js"></script>
		<script src="/assets/libs/bootstrap/dist/js/bootstrap.bundle.min.js"></script>
	</body>

</html>